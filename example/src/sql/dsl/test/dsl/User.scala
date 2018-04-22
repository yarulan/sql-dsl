package sql.dsl.test.dsl

import sql.dsl._

class User extends Record[Users] {
  def uuid: java.util.UUID = get(User.uuid)
  def id: Int = get(User.id)
  def name: String = get(User.name)
}

object User {
  val uuid = new Column[Users, java.util.UUID, Unique](Users, "uuid", Unique) {
    override type Slice = {
      val uuid: java.util.UUID
    }
  }
  val id = new Column[Users, Int, Unique](Users, "id", Unique) {
    override type Slice = {
      val id: Int
    }
  }
  val name = new Column[Users, String, NotUnique](Users, "name", NotUnique) {
    override type Slice = {
      val name: String
    }
  }
  type Uuid = {
    val uuid: java.util.UUID
  }
  type Id = {
    val id: Int
  }
  type Name = {
    val name: String
  }

  def apply(
    uuid: User.uuid.BuilderArg = User.uuid.BuilderArg.NoArg,
    id: User.id.BuilderArg = User.id.BuilderArg.NoArg,
    name: User.name.BuilderArg = User.name.BuilderArg.NoArg
  ): uuid.Slice with id.Slice with name.Slice = {
    val record = Users.newRecord
    uuid.toValue.foreach(value => record.set(User.uuid, value))
    id.toValue.foreach(value => record.set(User.id, value))
    name.toValue.foreach(value => record.set(User.name, value))
    record.asInstanceOf[uuid.Slice with id.Slice with name.Slice]
  }
}
class Users extends Table {
  override type Table = Users
  override val name: String = "Users"
  override type Record = User
  override val columns = Seq(User.uuid, User.id, User.name)
  override def newRecord: User = new User
}
