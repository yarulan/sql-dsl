package sql.dsl

case class Column[Table <: sql.dsl.Table, ColumnType](table: Table, name: String) {
  type Slice

  trait BuilderArg {
    type Slice
    def toValue: Option[ColumnType]
  }

  object BuilderArg {
    case object NoArg extends BuilderArg {
      override type Slice = {}
      override def toValue: Option[ColumnType] = None
    }

    implicit def wedewc(value: ColumnType) = SetValue(value)
  }

  case class SetValue(value: ColumnType) extends BuilderArg {
    override type Slice = Column.this.Slice
    override def toValue: Option[ColumnType] = Some(value)
  }
}
