package sql.dsl.test

import sql.dsl.{InsertStatement, InsertValueArg}

object TestDsl extends sql.dsl.Dsl {
  def insertInto(users: TestSchema.USERS.type) = new {
    def values(id: InsertValueArg[Int], name: InsertValueArg[String]): InsertStatement = {
      InsertStatement(TestSchema.USERS, Seq(id, name))
    }
  }
}
