package sql.dsl.macros

import sql.dsl.SelectStatement
import sql.dsl.Column

import scala.reflect.macros.blackbox

class Macros(val c: blackbox.Context) {
  import c.Expr
  import c.universe._

//  def foo(c1: Expr[Column[_, _]]): Expr[SelectStatement { type Result = c1.value.Type }]  = {
//
//    val zzzType = c1.tree.tpe.members.find(_.name.toString == "ZZZ").get
//    val field = zzzType.asType.toType.dealias.decls.head.asTerm
//
//    val fieldName = field.name.toString
//
//    val defn = ValDef(Modifiers(), fieldName, tq"${field.asMethod.returnType}", q"null.asInstanceOf")
//
//    val result = Expr(q""" new { $defn } """)
//
//    println(showCode(result.tree))
//
//    result
//  }
}
