package sql.dsl.codegen

import sql.dsl.{NotUnique, Unique, UniqueOrNot}

import scala.reflect.runtime.universe.TypeTag

case class Column[T, TUnique <: UniqueOrNot](
  table: Table,
  name: Name,
  isUnique: TUnique
)(
  implicit val tag: TypeTag[T]
) {
  val `type` = tag.tpe
}

object Column {
  type Any = Column[_, _ <: UniqueOrNot]

  implicit class NotUniqueColumnOps[T : TypeTag](column: Column[T, NotUnique]) {
    def unique: Column[T, Unique] = column.copy(isUnique = Unique)
  }
}
