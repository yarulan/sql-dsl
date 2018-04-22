package sql.dsl.test

import java.sql.{Connection, DriverManager}
import java.util.UUID

import org.scalatest.{FunSuite, Matchers}
import sql.dsl.test.dsl._
import sql.dsl.jdbc._

class Db(conn: Connection) {
  def createUser(user: User.Name): Unit = {
//    insertInto(Users).values(Default, user.name).execute(conn)
  }
}

class Test extends FunSuite with Matchers {
  test("test") {
    val conn = DriverManager.getConnection("jdbc:h2:mem:")

    var stmt = conn.createStatement()
    stmt.execute("create table Users(uuid uuid, id int, name varchar);")
    stmt.close()

    val db = new Db(conn)

    val uuid1 = UUID.fromString("00000000-0000-0000-0000-000000000001")
    val uuid2 = UUID.fromString("00000000-0000-0000-0000-000000000002")

    insertInto(Users).values(uuid1, 1, "John").execute(conn)
    insertInto(Users).values(uuid2, 2, "John").execute(conn)

    from(Users).where(User.id === 1).select(User.uuid, User.id, User.name).execute(conn).shouldBe(
      Some(User(uuid = uuid1, id = 1, name = "John"))
    )

    from(Users).where(User.name === "John").select(User.uuid, User.id, User.name).execute(conn).shouldBe(
      Seq(User(uuid = uuid1, id = 1, name = "John"), User(uuid = uuid2, id = 2, name = "John"))
    )

    conn.close()
  }
}
