package sql.dsl.test

import java.sql.{Connection, DriverManager}

import org.scalatest.{FunSuite, Matchers}
import sql.dsl.test.dsl._
import sql.dsl.test.dsl.Dsl._
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

//    User(name = "John")
//    User(id = 1)
    db.createUser(User(name = "John"))

    from(Users).select(User.id).execute(conn).id shouldBe 0
    from(Users).select(User.name).execute(conn).name shouldBe "John"

    conn.close()
  }


}
