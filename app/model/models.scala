package model

import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import model.enum.AccountStatus.AccountStatus

import scala.language.implicitConversions


case class Account(id:Long, passport:String, password:String, status: AccountStatus)

object User{
  def formJson(json:String):Option[User] = decode[User](json).right.toOption
}
case class User(id:Long, name:String, role:Int){
  def toJsonStr:String = this.asJson.noSpaces
}