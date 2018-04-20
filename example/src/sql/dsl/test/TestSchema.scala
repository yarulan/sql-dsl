package sql.dsl.test

import sql.dsl
import sql.dsl.{Column, Record, Table}

object TestSchema {
  class User extends Record[Users] {
    def id: Int = get(User.id)
    def name: String = get(User.name)
  }

  object User {
    val id = new Column[Users, Int](Users, "ID") {
      override type Type = {
        val id: Int
      }
    }
    val name = new Column[Users, String](Users, "NAME") {
      override type Type = {
        val name: String
      }
    }
  }

  val Users = new Users

  class Users extends Table {
    override type Table = Users
    override val name: String = "USERS"
    override type Record = User
    override val columns = Seq(User.id, User.name)
    override def newRecord: User = new User
  }


}