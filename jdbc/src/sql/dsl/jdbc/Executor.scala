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

//  def execute(stmt: InsertStatement[_]): Unit = {
//    val conn = getConnection
//    val table = stmt.table
//    val values = stmt.values
//    val columns = table.columns
//    val tableName = stmt.table.name
//    val args = (values map {
//      case InsertValueArg.Default => "default"
//      case _ => "?"
//    }).mkString(", ")
//    val jdbcStmt = conn.prepareStatement(s"insert into $tableName values ($args)")
//
//    values.collect {
//      case x: InsertValueArg.Plain[_] => x
//    }.zipWithIndex.foreach { case (arg, i) =>
//      jdbcStmt.setObject(i + 1, arg.value)
//    }
//
//    jdbcStmt.executeUpdate()
//  }

  def execute[T <: SelectStatement[_]](stmt: T): T#Result = {
//    val conn = getConnection
//    val jdbcStmt = conn.createStatement()
//    val sColumns = stmt.columns.map(_.name).mkString(", ")
//    val tableName = stmt.table.name
//    val resultSet = jdbcStmt.executeQuery(s"select $sColumns from $tableName")
    ???
  }
}
