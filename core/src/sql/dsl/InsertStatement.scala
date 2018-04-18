package sql.dsl

sealed trait Statement

case class InsertStatement(table: Schema#Table, values: Seq[InsertValueArg[_]]) extends Statement {

}
