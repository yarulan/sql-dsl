package sql.dsl.test.dsl

import sql.dsl.{InsertValueArg, InsertStatement}

object Dsl extends sql.dsl.Dsl {
  val Users = new Users
  def insertInto(users: Users) = new {
    def values(id: InsertValueArg[Int], name: InsertValueArg[String]): InsertStatement[Users] = {
      InsertStatement(Users, Seq(id,name))
    }
  }
}
