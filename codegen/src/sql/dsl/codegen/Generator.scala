package sql.dsl.codegen

import java.io.OutputStream
import java.nio.file.Path

class Generator(
  writer: Writer = ConsoleWriter,
  `package`: String
) {
  private var indent = 0

  private var stream: OutputStream = _

  private val InsertValueArg = "InsertValueArg"
  private val InsertStatement = "InsertStatement"

  def emit(line: String): Unit = {
    stream.write(line.getBytes)
  }

  def emitln(line: String): Unit = {
    emit("  " * indent)
    emit(line)
    emitln()
  }

  def emitln(): Unit = {
    emit("\n")
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

  def emitBlock(start: String, end: String)(block: => Unit): Unit = {
    emitln(start, 1)
    block
    emitln(-1, end)
  }

  def generate(tables: Seq[Table]): Unit = {
    writer.init()
    genDsl(tables)
    tables.foreach(generate)
  }

  private def genDsl(tables: Seq[Table]): Unit = {
    stream = writer.getStream(s"Dsl.scala")

    emit(s"package ${`package`}\n\n")

    emitln(s"import sql.dsl.{$InsertValueArg, $InsertStatement}")
    emitln()

    emitBlock("object Dsl extends sql.dsl.Dsl {", "}") {
      tables.foreach { table =>
        val tableName = table.tableName
        val recordName = table.recordName
        val columns = table.columns
        val User = recordName
        val Users = tableName
        emitln(s"val $Users = new $Users")
      }

      tables.foreach { table =>
        val tableName = table.tableName
        val recordName = table.recordName
        val columns = table.columns
        val User = recordName
        val Users = tableName
        val sColumns = columns.map { column =>
          s"${column.name.camelCased}: $InsertValueArg[${column.`type`.toString}]"
        }.mkString(", ")
        emitln(s"def insertInto(users: $Users) = new {")
        emitln(s"  def values($sColumns): $InsertStatement[$Users] = {")
        emitln(s"    $InsertStatement($Users, Seq(${columns.map(_.name.camelCased).mkString(",")}))")
        emitln(s"  }")
        emitln(s"}")
      }
    }

    stream.close()

  }

  private def generate(table: Table): Unit = {
    val tableName = table.tableName
    val recordName = table.recordName
    val columns = table.columns
    val User = recordName
    val Users = tableName

    stream = writer.getStream(s"$User.scala")

    emit(s"package ${`package`}\n\n")

    emitln("import sql.dsl.{Table, Record, Column}")
    emitln(s"import ${`package`}.Dsl._")

    emitln()

    emitBlock(s"class $User extends Record[$Users] {", "}") {
      columns.foreach { column =>
        val id = column.name.camelCased
        val Int = column.`type`.toString
        emitln(s"def $id: $Int = get($User.$id)")
      }
    }

    emitln()

    emitBlock(s"object $User {", "}") {
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
    }

    emitBlock(s"class $Users extends Table {", "}") {
      emitln(s"override type Table = $Users")
      emitln(s"""override val name: String = "$Users"""")
      emitln(s"override type Record = $User")
      val sColumns = columns.map(column => s"$User.${column.name.camelCased}").mkString(", ")
      emitln(s"override val columns = Seq($sColumns)")
      emitln(s"override def newRecord: $User = new $User")
    }

    stream.close()
  }
}