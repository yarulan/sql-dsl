package sql.dsl.codegen

import sql.dsl.NotUnique

import scala.reflect.runtime.universe.TypeTag

abstract class Table(val tableName: String, val recordName: String) {
  def column[T](implicit name: sourcecode.Name, tag: TypeTag[T]): Column[T, NotUnique] = {
    new Column[T, NotUnique](this, Name.fromCamelCase(name.value), NotUnique)
  }

  lazy val columns: Seq[Column.Any] = {
    val columnFields = getClass.getDeclaredFields.filter(field => classOf[Column.Any].isAssignableFrom(field.getType))
    columnFields.map { field =>
      field.setAccessible(true)
      field.get(this).asInstanceOf[Column.Any]
    }
  }
}
