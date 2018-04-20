package sql.dsl.test

import sql.dsl.{InsertStatement, InsertValueArg}

object TestDsl extends sql.dsl.Dsl {
  def insertInto(users: TestSchema.Users) = new {
    def values(id: InsertValueArg[Int], name: InsertValueArg[String]): InsertStatement[TestSchema.Users] = {
      InsertStatement(TestSchema.Users, Seq(id, name))
    }
  }
}
