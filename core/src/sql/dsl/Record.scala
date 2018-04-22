package sql.dsl

import scala.collection.mutable

trait Record[Table <: sql.dsl.Table] {

  private val values = mutable.Map[Column[Table, _, _], Any]()

  def set[Type](column: Column[Table, Type, _], value: Type): Unit = {
    values(column) = value
  }

  def get[Type](column: Column[Table, Type, _]): Type = {
    values(column).asInstanceOf[Type]
  }

  override def equals(that: Any): Boolean = {
    that match {
      case that: Record[_] =>
        values == that.values
      case _ => false
    }
  }
}
