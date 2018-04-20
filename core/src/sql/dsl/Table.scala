package sql.dsl

trait Table {
  type Table <: sql.dsl.Table
  type Record <: sql.dsl.Record[Table]
  type Column[Type] = sql.dsl.Column[Table, Type]

  val columns: Seq[Column[_]]

  def newRecord: Record

  val name: String
}
