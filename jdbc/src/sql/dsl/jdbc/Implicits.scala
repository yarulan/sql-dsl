package sql.dsl.jdbc

import java.sql.Connection

import sql.dsl.{Column, SelectStatement, Table}

object Implicits extends Implicits

trait Implicits {

  implicit class SelectOps[Table <: sql.dsl.Table[Table], R](
    val select: SelectStatement[Table] {
      type Result = R
    }
  ) {
    def execute(conn: Connection): R = {
      val jdbcStmt = conn.createStatement()
      val table = select.table
      val sColumns = select.columns.map(_.name).mkString(", ")
      val tableName = table.name
      val query = s"select $sColumns from $tableName"
      val resultSet = jdbcStmt.executeQuery(query)
      resultSet.next()
      val record = table.newRecord
      select.columns.zipWithIndex.foreach { case (column, i) =>
        val value = resultSet.getObject(i + 1)
        record.set(column.asInstanceOf[Column[Table, Any]], value)
      }
      record.asInstanceOf[R]
    }
  }

}
