package sql.dsl.jdbc

import java.sql.{Connection, ResultSet}

import sql.dsl._

import scala.collection.mutable.ArrayBuffer

object Implicits extends Implicits

trait Implicits {

  implicit class SelectOps[TTable <: Table, R, TUnique <: UniqueOrNot](
    val select: SelectStatement[TTable, TUnique] {
      type Record = R
    }
  ) {
    private def resultSetToRecord(resultSet: ResultSet): R = {
      val table = select.from.table
      val record = table.newRecord
      select.columns.zipWithIndex.foreach { case (column, i) =>
        val value = resultSet.getObject(i + 1)
        record.set(column.asInstanceOf[Column[table.Table, Any, _]], value)
      }
      record.asInstanceOf[R]
    }

    private def conditionToSql(condition: Condition[_, _, _]): GeneratedSql = {
      val op = condition.operator match {
        case Operator.Eq => "="
      }
      val column = condition.column.name
      GeneratedSql(s"$column $op ?", Seq(condition.value))
    }

    def execute(conn: Connection): TUnique#Container[R] = {

      val table = select.from.table
      val sColumns = select.columns.map(_.name).mkString(", ")
      val tableName = table.name
      val where = select.where.map(conditionToSql)
      val sWhere = where.map(" where " + _.template).getOrElse("")
      val query = s"select $sColumns from $tableName$sWhere;"
      println("QQQQQQQQ: " + query)
      val stmt = conn.prepareStatement(query)
      where.map(_.parameters).getOrElse(Seq()).zipWithIndex.foreach { case (param, i) =>
        stmt.setObject(i + 1, param)
      }
      val resultSet = stmt.executeQuery()

      val result = if (select.unique) {
        if (resultSet.next()) {
          val result = Some(resultSetToRecord(resultSet))
          if (resultSet.next()) {
            throw new Exception(s"Expected one record, but got more, query: $select")
          }
          result
        } else {
          None
        }
      } else {
        val records = ArrayBuffer[R]()
        while(resultSet.next()) {
          val record = resultSetToRecord(resultSet)
          records += record
        }
        records
      }

      resultSet.close()
      stmt.close()

      result.asInstanceOf[TUnique#Container[R]]
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
