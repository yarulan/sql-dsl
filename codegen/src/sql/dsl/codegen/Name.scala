package sql.dsl.codegen

case class Name(parts: Seq[String]) {
  def camelCased: String = (parts.head +: parts.tail.map(_.capitalize)).mkString("")
  def pascalCased: String = parts.map(_.capitalize).mkString("")
}

object Name {
  def fromCamelCase(s: String): Name = {
    val parts = s.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")
    Name(parts.map(_.toLowerCase))
  }
}