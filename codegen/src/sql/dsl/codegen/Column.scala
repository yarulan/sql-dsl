package sql.dsl.codegen

import scala.reflect.runtime.universe.TypeTag

class Column[T](
  val table: Table,
  val name: Name
)(
  implicit val tag: TypeTag[T]
) {
  table.addColumn(this)

  val `type` = tag.tpe
}
