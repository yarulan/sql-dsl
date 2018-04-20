package sql.dsl

sealed trait Statement

case class InsertStatement[Table <: sql.dsl.Table[Table]](
  table: Table,
  values: Seq[InsertValueArg[_]]
) extends Statement {
}

case class SelectStatement[Table <: sql.dsl.Table[Table]](
  table: Table,
  columns: Seq[Column[Table, _]]
) extends Statement {
  type Result
}
