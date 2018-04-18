package sql.dsl.codegen

import java.nio.file.Path

import sql.dsl.Schema

object Generator {
  def emitln(line: String) = ???

  def generate(schema: Schema): Unit = {
    schema.tables.foreach { table =>
      val tableName = table.getName
      emitln(s"def insertInto($tableName: )")
    }

  }

  def generate(table: Schema#Table): Unit = {

  }
}
