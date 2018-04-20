import sbt._
import sbt.Keys.scalaVersion

object Deps {
  val scalaMeta = "org.scalameta" %% "scalameta" % "3.7.3"
  val h2 = "com.h2database" % "h2" % "1.3.170"
  val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4"
  val sourcecode = "com.lihaoyi" %% "sourcecode" % "0.1.4"

  val scalaReflect: Def.Initialize[ModuleID] = Def.setting {
    "org.scala-lang" % "scala-reflect" % scalaVersion.value
  }

  val scalaCompiler: Def.Initialize[ModuleID] = Def.setting {
    "org.scala-lang" % "scala-compiler" % scalaVersion.value
  }
}