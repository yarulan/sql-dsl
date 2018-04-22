package sql.dsl

import scala.language.dynamics


trait Dsl {
  val Default = InsertValueArg.Default

//  def foo(c1: Column[_, _]): c1.ZZZ = macro Macros.foo

//  def selectFrom[Table <: sql.dsl.Table[Table], C1](table: Table) = SelectFrom(table)

  def from[Table <: sql.dsl.Table](table: Table) = From(table)

}

case class From[Table <: sql.dsl.Table](table: Table) extends SelectWords[Table] {
  override val from: From[Table] = this

  override type X = NotUnique

  def where[ColumnType, Unique <: UniqueOrNot](
    condition: Condition[Table, ColumnType, Unique]
  ) = FromWhere(this, condition)

  override val whereOpt: Option[Condition[Table, _, X]] = None
}

case class FromWhere[Table <: CTable, Unique <: UniqueOrNot](
  from: From[Table],
  where: Condition[Table, _, Unique]
) extends SelectWords[Table] {
  override type X = Unique
  override val whereOpt: Option[Condition[Table, _, X]] = Some(where)
}

trait SelectWords[Table <: sql.dsl.Table] {
  val from: From[Table]

  type Column[U] = sql.dsl.Column[Table, U, _ <: UniqueOrNot]

  type X <: UniqueOrNot

  val whereOpt: Option[Condition[Table, _, X]]

  private def unsafeCast[U](x: Any): U = x.asInstanceOf[U]

  def select[C1](c1: Column[C1]): SelectStatement[Table, X] {
    type Record = c1.Slice
  } = {
    unsafeCast(SelectStatement(from, Seq(c1), whereOpt))
  }

  def select[C1, C2](c1: Column[C1], c2: Column[C2]): SelectStatement[Table, X] {
    type Record = c1.Slice with c2.Slice
  } = {
    unsafeCast(SelectStatement(from, Seq(c1, c2), whereOpt))
  }
}

//sealed trait Unique
//case object Unique extends Unique
//trait NotUnique[T] extends Unique


//case class SelectFrom[T <: Table](table: T) {
//  import scala.language.experimental.macros
//
//  def apply[C1](c1: Column[T, C1]): SelectStatement { type Result = c1.Type} = macro Macros.foo
//  def apply[C1, C2](c1: Column[T, C1], c2: Column[T, C2]): c1.Type with c2.Type = ??? // macro Macros.foo
//}

//sealed trait SelectArg[Table, Value]
//
//object SelectArg {
//  case class Column[Table, Value]() extends SelectArg[Table, Value]
//
//  implicit def fromColumn[Table, Value](column: sql.dsl.impl.Column[Table, Value]) =
//}