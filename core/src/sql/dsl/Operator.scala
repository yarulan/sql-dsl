package sql.dsl

sealed trait Operator

object Operator {
  case object Eq extends Operator
}
