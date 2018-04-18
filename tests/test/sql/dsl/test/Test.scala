package sql.dsl.test

import java.sql.DriverManager

import org.scalatest.FunSuite

import sql.dsl.test.TestDsl._
import sql.dsl.test.TestSchema._
import sql.dsl.jdbc.{execute => exec}

class Test extends FunSuite {
  test("test") {
    val conn = DriverManager.getConnection("jdbc:h2:mem:")
    var stmt = conn.createStatement()
    stmt.execute("create table Users(id int, name varchar);")
    stmt.close()

    exec(conn, insertInto(USERS).values(Default, "John"))

    stmt = conn.createStatement()
    val i = stmt.executeUpdate("delete from Users;")
    stmt.close()
    println(i)

    conn.close()
  }
}
