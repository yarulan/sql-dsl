package sql.dsl

sealed trait UniqueOrNot {
  type Type <: UniqueOrNot
  type Container[T]
  val isTrue: Boolean
}

sealed trait Unique extends UniqueOrNot {
  override type Type = Unique
  override type Container[T] = Option[T]
  override val isTrue: Boolean = true
}

object Unique extends Unique

sealed trait NotUnique extends UniqueOrNot {
  override type Type = NotUnique
  override type Container[T] = Seq[T]
  override val isTrue: Boolean = false
}

object NotUnique extends NotUnique