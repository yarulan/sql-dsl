package sql.dsl.test

import java.nio.file.Paths
import java.util.UUID

import sql.dsl.codegen.{ConsoleWriter, FileSystemWriter, Generator, Table}

object TestGenerator {
  def main(args: Array[String]): Unit = {
    val Users = new Table("Users", "User") {
      val uuid = column[UUID].unique
      val id = column[Int].unique
      val name = column[String]
    }
    val pkg = "sql.dsl.test.dsl"
    val src = Paths.get("./example/src").resolve(pkg.replace('.', '/'))
//    val generator = new Generator(ConsoleWriter, pkg)
    val generator = new Generator(new FileSystemWriter(src), pkg)

    generator.generate(Seq(Users))
  }
}
