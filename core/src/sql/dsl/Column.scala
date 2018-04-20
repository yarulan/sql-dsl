package sql.dsl

case class Column[Table <: sql.dsl.Table, R](table: Table, name: String) {
  type Type


}
