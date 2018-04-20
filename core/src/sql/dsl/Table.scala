package sql.dsl

trait Table[Table <: sql.dsl.Table[Table]] {
  type Record <: sql.dsl.Record[Table]
  type Column[Type] <: sql.dsl.Column[Table, Type]


  val columns: Seq[sql.dsl.Column[Table, _]]

  def newRecord: Record

  val name: String
}
