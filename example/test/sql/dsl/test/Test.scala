package sql.dsl.test

import java.sql.{Connection, DriverManager}

import org.scalatest.{FunSuite, Matchers}
import sql.dsl.test.dsl._
import sql.dsl.jdbc._

class Db(conn: Connection) {
  def createUser(user: User.Name): Unit = {
    insertInto(Users).values(Default, user.name).execute(conn)
  }
}

class Test extends FunSuite with Matchers {
  test("test") {
    val conn = DriverManager.getConnection("jdbc:h2:mem:")

    var stmt = conn.createStatement()
    stmt.execute("create table Users(id int, name varchar);")
    stmt.close()

    val db = new Db(conn)

    insertInto(Users).values(1, "John").execute(conn)
    insertInto(Users).values(2, "John").execute(conn)
    insertInto(Users).values(3, "Jane").execute(conn)

    from(Users).where(User.id === 1).select(User.id, User.name).execute(conn).shouldBe(
      Some(User(id = 1, name = "John"))
    )

    from(Users).where(User.name === "John").select(User.id, User.name).execute(conn).shouldBe(
      Seq(User(id = 1, name = "John"), User(id = 2, name = "John"))
    )

    conn.close()
  }
}
