package sql.dsl

sealed trait PhantomBool

object PhantomBool {
  sealed trait True extends PhantomBool
  sealed trait False extends PhantomBool
}
