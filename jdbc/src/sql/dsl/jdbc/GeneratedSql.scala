package sql.dsl.jdbc

case class GeneratedSql(template: String, parameters: Seq[Any])
