package sql.dsl.codegen

import scala.reflect.runtime.universe.TypeTag

abstract class Table(val tableName: String, val recordName: String) {
  private var _columns = Vector[Column[_]]()

  def column[T](implicit name: sourcecode.Name, tag: TypeTag[T]): Column[T] = {
    new Column[T](this, Name.fromCamelCase(name.value))
  }
  def columns: Seq[Column[_]] = _columns

  private[codegen] def addColumn(column: Column[_]): Unit = {
    _columns = _columns.:+(column)
  }
}
