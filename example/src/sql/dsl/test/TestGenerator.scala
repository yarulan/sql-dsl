package sql.dsl.test

import java.nio.file.Paths

import sql.dsl.codegen.{FileSystemWriter, Generator, Table}

object TestGenerator {
  def main(args: Array[String]): Unit = {
    val Users = new Table("Users", "User") {
      val id = column[Int]
      val name = column[String]
    }
    val pkg = "sql.dsl.test.dsl"
    val src = Paths.get("./example/src").resolve(pkg.replace('.', '/'))
    val generator = new Generator(new FileSystemWriter(src), pkg)

    generator.generate(Seq(Users))
  }
}
