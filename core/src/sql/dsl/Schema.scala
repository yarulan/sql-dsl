package sql.dsl

trait Schema {
  private var _tables = Vector[Table]()

  def tables: Seq[Table] = _tables

  abstract class Table(_name: String) {
    _tables = _tables.:+(this)

    private var _columns = Vector[Column[_]]()

    class Column[T](val name: String) {
      _columns = _columns.:+(this)
    }

    def column[T](implicit name: sourcecode.Name): Column[T] = {
      new Column(name.value)
    }

    def getName: String = _name

    def columns: Seq[Column[_]] = _columns
  }
}