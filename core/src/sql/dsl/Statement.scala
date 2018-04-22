package sql.dsl

sealed trait Statement

case class InsertStatement[Table <: sql.dsl.Table](
  table: Table,
  values: Seq[InsertValueArg[_]]
) extends Statement {
}

case class SelectStatement[Table <: sql.dsl.Table, RecordContainer[_]](
  from: From[Table],
  columns: Seq[Column[Table, _]],
  where: Where[RecordContainer]
) extends Statement {
  type Record
}
