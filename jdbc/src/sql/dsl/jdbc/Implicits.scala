package sql.dsl.jdbc

import java.sql.Connection

import sql.dsl._

object Implicits extends Implicits

trait Implicits {

  implicit class SelectOps[Table <: sql.dsl.Table, R](
    val select: SelectStatement[Table] {
      type Result = R
    }
  ) {
    def execute(conn: Connection): R = {
      val jdbcStmt = conn.createStatement()
      val table = select.from.table
      val sColumns = select.columns.map(_.name).mkString(", ")
      val tableName = table.name
      val query = s"select $sColumns from $tableName"
      val resultSet = jdbcStmt.executeQuery(query)
      resultSet.next()
      val record = table.newRecord
      select.columns.zipWithIndex.foreach { case (column, i) =>
        val value = resultSet.getObject(i + 1)
        record.set(column.asInstanceOf[Column[table.Table, Any]], value)
      }
      record.asInstanceOf[R]
    }
  }

  implicit class InsertOps[Table <: sql.dsl.Table](insert: InsertStatement[Table]) {
    def execute(conn: Connection): Unit = {
      val table = insert.table
      val values = insert.values
      val columns = table.columns
      val tableName = table.name
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

}
