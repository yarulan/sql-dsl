package sql.dsl.test

import sql.dsl.Schema

object TestSchema extends Schema {
  val USERS = new Table("USERS") {
    val ID = column[Int]
    val NAME = column[String]
  }
}