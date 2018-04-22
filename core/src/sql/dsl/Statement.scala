package sql.dsl

sealed trait Statement

case class InsertStatement[Table <: sql.dsl.Table](
  table: Table,
  values: Seq[InsertValueArg[_]]
) extends Statement {
}

case class SelectStatement[Table <: sql.dsl.Table, Unique <: UniqueOrNot](
  from: From[Table],
  columns: Seq[Column[Table, _, _]],
  where: Option[Condition[Table, _, Unique]]
) extends Statement {
  type Record

  def unique: Boolean = where.exists(_.unique.isTrue)
}
