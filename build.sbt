import BuildUtil._
import Deps._

val commonSettings = Seq(
  organization := "pro.ulan.flashcards",
  scalaVersion := "2.12.4",
  scalacOptions ++= Seq("-feature", "-language:implicitConversions")
)

lazy val sqlDsl = project.in(file("."))
  .settings(commonSettings: _*)
  .aggregate(core, codegen, jdbc, tests)

lazy val core = project
  .settings(commonSettings: _*)
  .configure(
    useFlatDirs,
    addDeps(sourcecode)
  )

lazy val codegen = project
  .settings(commonSettings: _*)
  .dependsOn(core)
  .configure(
    useFlatDirs,
    addDeps(scalaMeta)
  )

lazy val jdbc = project
  .settings(commonSettings: _*)
  .dependsOn(core)
  .configure(
    useFlatDirs,
    addDeps(scalaTest, h2) % Test,
  )

lazy val tests = project
  .settings(commonSettings: _*)
  .dependsOn(codegen, jdbc)
  .configure(
    useFlatDirs,
    addDeps(scalaTest, h2) % Test,
  )
