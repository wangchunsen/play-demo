package db

import doobie.implicits._
import doobie.util.fragment.Fragment
import doobie.util.meta.Meta
import model.enum.AccountStatus
import model.enum.AccountStatus.AccountStatus

object Account {
  implicit private val statusMapper: Meta[AccountStatus] =
    Meta[Int].xmap(AccountStatus.apply, _.id)

  private val selectAccount = Fragment.const("select id, passport, password, status from account")

  def selectByNameAndPassword(name: String, password: String) = {
    (selectAccount ++
      sql"""where
          passport = $name and
          password = $password"""
      ).query[model.Account].option
  }

  def selectAll() = selectAccount.query[model.Account].to[List]
}
