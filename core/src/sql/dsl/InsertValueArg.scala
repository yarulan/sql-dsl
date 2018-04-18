package sql.dsl

sealed trait InsertValueArg[+T]

object InsertValueArg {
  implicit def fromT[T](value: T): InsertValueArg[T] = Plain(value)

  case object Default extends InsertValueArg[Nothing]

  case class Plain[T](value: T) extends InsertValueArg[T]
}