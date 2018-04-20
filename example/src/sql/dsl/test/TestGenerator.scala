package sql.dsl.test

import java.nio.file.Paths

import sql.dsl.codegen.{Generator, Table}

object TestGenerator {
  def main(args: Array[String]): Unit = {
    val Users = new Table("Users", "User") {
      val id = column[Int]
      val name = column[String]
    }
    val generator = new Generator(Paths.get("./example/src"))

    generator.generate(Users)
  }
}
