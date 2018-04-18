package sql.dsl.test

import java.nio.file.Paths

object TestGenerator {
  def main(args: Array[String]): Unit = {
    sql.dsl.codegen.Generator.generate(TestSchema)
  }
}
