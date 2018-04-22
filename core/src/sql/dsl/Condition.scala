package sql.dsl

case class Condition[Table <: CTable, ColumnType, Unique <: UniqueOrNot](
  column: Column[Table, ColumnType, _ <: UniqueOrNot],
  operator: Operator,
  value: ColumnType,
  unique: Unique
)