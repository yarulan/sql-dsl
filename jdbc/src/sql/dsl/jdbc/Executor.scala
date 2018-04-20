package sql.dsl.jdbc

import java.sql.Connection

import sql.dsl.{InsertStatement, InsertValueArg, SelectStatement, Statement}

trait Executor {
  def getConnection: Connection

//  def execute(stmt: Statement): Unit = {
//    stmt match {
//      case x: InsertStatement => execute(x)
//    }
//  }

  def execute(stmt: InsertStatement[_]): Unit = {
  }
//
  def execute[T <: SelectStatement[_]](stmt: T): T#Result = {
//    val conn = getConnection
//    val jdbcStmt = conn.createStatement()
//    val sColumns = stmt.columns.map(_.name).mkString(", ")
//    val tableName = stmt.table.name
//    val resultSet = jdbcStmt.executeQuery(s"select $sColumns from $tableName")
    ???
  }
}
