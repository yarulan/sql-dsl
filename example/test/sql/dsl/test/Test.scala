package sql.dsl.test

import java.sql.{Connection, DriverManager}

import org.scalatest.{FunSuite, Matchers}
import sql.dsl.test.TestDsl._
import sql.dsl.test.TestSchema._
import sql.dsl.jdbc._

class Test extends FunSuite with Matchers {
  test("test") {
    val conn = DriverManager.getConnection("jdbc:h2:mem:")

    var stmt = conn.createStatement()
    stmt.execute("create table Users(id int, name varchar);")
    stmt.close()

    insertInto(Users).values(Default, "John").execute(conn)

    from(Users).select(User.id).execute(conn).id shouldBe 0
    from(Users).select(User.name).execute(conn).name shouldBe "John"

    conn.close()
  }
}
