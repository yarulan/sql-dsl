package sql.dsl

import java.sql.{Connection, DriverManager}

package object jdbc {
  def execute(conn: Connection, stmt: InsertStatement): Unit = {
    val table = stmt.table
    val values = stmt.values
    val columns = table.columns
    val tableName = stmt.table.getName
    val args = (values map {
      case InsertValueArg.Default => "default"
      case _ => "?"
    }).mkString(", ")
    val jdbcStmt = conn.prepareStatement(s"insert into $tableName values ($args)")

    values.collect {
      case x: InsertValueArg.Plain[_] => x
    }.zipWithIndex.foreach { case (arg, i) =>
      jdbcStmt.setObject(i + 1, arg.value)
    }

    jdbcStmt.executeUpdate()
  }
}
