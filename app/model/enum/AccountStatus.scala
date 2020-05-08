package model.enum

object AccountStatus extends Enumeration {
  type AccountStatus = Value
  val Normal = Value(0)
  val Disabled = Value(1)
}
