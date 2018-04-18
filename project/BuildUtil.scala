import sbt.Keys._
import sbt._

object BuildUtil {
  def addDeps(deps: AddDepsArg*) = AddDeps(Compile, deps)

  val useFlatDirs: Project => Project = _.settings(
    scalaSource in Compile := baseDirectory.value / "src",
    scalaSource in Test := baseDirectory.value / "test",
    resourceDirectory in Compile := baseDirectory.value / "res"
  )

  val ideSkipProject: Project => Project = {
    _.settings(SettingKey[Boolean]("ide-skip-project") := true)
  }

  sealed trait AddDepsArg

  object AddDepsArg {
    case class Initialize(x: Def.Initialize[ModuleID]) extends AddDepsArg
    case class ModuleId(x: ModuleID) extends AddDepsArg

    implicit def fromDefInitialize(x: Def.Initialize[ModuleID]): Initialize = Initialize(x)
    implicit def fromModuleId(x: ModuleID): ModuleId = ModuleId(x)
  }

  case class AddDeps(conf: Configuration, deps: Seq[AddDepsArg]) extends (Project => Project) {
    override def apply(project: Project): Project = {
      val settings = deps.map {
        case AddDepsArg.Initialize(x) => libraryDependencies += x.value
        case AddDepsArg.ModuleId(x) => libraryDependencies += x
      }
      project.settings(settings: _*)
    }

    def % (conf: Configuration): AddDeps = copy(conf = conf)
  }
}
