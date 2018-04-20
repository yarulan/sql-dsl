package sql.dsl.codegen

import java.nio.file.Path

import sql.dsl.Schema

class Generator(dir: Path) {
  private var indent = 0

  def emitln(line: String): Unit = {
    print("  " * indent)
    println(line)
  }

  def emitln(): Unit = {
    println()
  }

  def emitln(line: String, indent: Int): Unit = {
    emitln(line)
    this.indent += indent
  }

  def emitln(indent: Int, line: String): Unit = {
    this.indent += indent
    emitln(line)
  }

  def emitln(indent1: Int, line: String, indent2: Int): Unit = {
    this.indent += indent1
    emitln(line)
    this.indent += indent2
  }

  def generate(table: Table): Unit = {
    val tableName = table.tableName
    val recordName = table.recordName
    val columns = table.columns
    val User = recordName
    val Users = tableName

    emitln(s"class $recordName extends Record[$tableName] {", 1)
    columns.foreach { column =>
      val id = column.name.camelCased
      val Int = column.`type`.toString
      emitln(s"def $id: $Int = get($User.$id)")
    }
    emitln(-1, "}")

    emitln()

    emitln(s"object $recordName {", 1)

    columns.foreach { column =>
      val columnName = column.name.camelCased
      val columnType = column.`type`.toString
      emitln(s"""val $columnName = new Column[$tableName, $columnType]($tableName, "$columnName") {""")
      emitln(s"""  override type Slice = {""")
      emitln(s"""    val $columnName: $columnType""")
      emitln(s"""  }""")
      emitln(s"""}""")
    }

    columns.foreach { column =>
      val columnName = column.name
      val columnType = column.`type`.toString
      val Id = column.name.pascalCased
      emitln(s"""type $Id = {""")
      emitln(s"""  val ${column.name.camelCased}: $columnType""")
      emitln(s"""}""")
    }

    emitln()

    emitln("def apply(", 1)
    columns.foreach { column =>
      val id = column.name.camelCased
      val comma = if (column != columns.last) "," else ""
      emitln(s"$id: $User.$id.BuilderArg = $User.$id.BuilderArg.NoArg$comma")
    }
    val applyType = s"${columns.map(column => s"${column.name.camelCased}.Slice").mkString(" with ")}"
    emitln(-1, s"): $applyType = {", 1)
    emitln(s"val record = $tableName.newRecord")
    columns.foreach { column =>
      val id = column.name.camelCased
      val User = recordName
      emitln(s"$id.toValue.foreach(value => record.set($User.$id, value))")
    }
    emitln(s"record.asInstanceOf[$applyType]")
    emitln(-1, "}")

    emitln(-1, "}")

    emitln("class Users extends Table {", 1)
    emitln(s"override type Table = $Users")
    emitln(s"""override val name: String = "$Users"""")
    emitln(s"override type Record = $User")
    emitln(s"override val columns = Seq(${columns.map(column => s"$User.${column.name.camelCased}").mkString(", ")})")
    emitln(s"override def newRecord: $User = new $User")
    emitln(-1, "}")
  }
}

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
