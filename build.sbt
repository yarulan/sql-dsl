import BuildUtil._
import Deps._

val commonSettings = Seq(
  organization := "pro.ulan.sql.dsl",
  scalaVersion := "2.12.4",
  scalacOptions ++= Seq("-feature", "-language:implicitConversions", "-language:reflectiveCalls")
)

lazy val sqlDsl = project.in(file("."))
  .settings(commonSettings: _*)
  .aggregate(core, dsl, codegen, jdbc, example, macros)

lazy val core = project
  .settings(commonSettings: _*)
  .configure(
    useFlatDirs,
    addDeps(sourcecode)
  )

lazy val dsl = project
  .settings(commonSettings: _*)
  .dependsOn(core, macros)
  .configure(
    useFlatDirs,
    addDeps(sourcecode)
  )

lazy val codegen = project
  .settings(commonSettings: _*)
  .dependsOn(core)
  .configure(
    useFlatDirs,
    addDeps(scalaMeta, sourcecode, scalaReflect)
  )

lazy val jdbc = project
  .settings(commonSettings: _*)
  .dependsOn(core)
  .configure(
    useFlatDirs,
    addDeps(scalaTest, h2) % Test,
  )

lazy val example = project
  .settings(commonSettings: _*)
  .dependsOn(dsl, codegen, jdbc)
  .configure(
    useFlatDirs,
    addDeps(scalaTest, h2) % Test,
  )

lazy val macros = project
  .settings(commonSettings: _*)
  .dependsOn(core)
  .configure(
    useFlatDirs,
    addDeps(scalaReflect),
  )
