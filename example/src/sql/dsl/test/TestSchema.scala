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
      override type Slice = {
        val id: Int
      }
    }
    val name = new Column[Users, String](Users, "NAME") {
      override type Slice = {
        val name: String
      }
    }

    type Id = {
      val id: Int
    }

    type Name = {
      val name: String
    }

    def apply(
      id: User.id.BuilderArg = User.id.BuilderArg.NoArg,
      name: User.name.BuilderArg = User.name.BuilderArg.NoArg
    ): id.Slice with name.Slice = {
      val record = Users.newRecord
      id.toValue.foreach(value => record.set(User.id, value))
      name.toValue.foreach(value => record.set(User.name, value))
      record.asInstanceOf[id.Slice with name.Slice]
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