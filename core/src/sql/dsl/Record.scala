package sql.dsl

import scala.collection.mutable

trait Record[Table <: sql.dsl.Table] {

  private val values = mutable.Map[Column[Table, _], Any]()

  def set[Type](column: Column[Table, Type], value: Type): Unit = {
    values(column) = value
  }

  def get[Type](column: Column[Table, Type]): Type = {
    values(column).asInstanceOf[Type]
  }
}
