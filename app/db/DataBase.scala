package db

import doobie._
import cats.effect.IO
import com.zaxxer.hikari.HikariDataSource
import doobie.hikari.HikariTransactor
import javax.inject.{Inject, Singleton}
import play.api.db.DBApi
import play.api.db.evolutions.ApplicationEvolutionsProvider

@Singleton
class DataBase @Inject()(provider: ApplicationEvolutionsProvider, dBApi: DBApi){
  provider.get.start()
  val transactor:Transactor[IO] =
    HikariTransactor[IO](dBApi.database("default").dataSource.asInstanceOf[HikariDataSource])

  object Implicits{
    implicit class QueryIo[A](query: ConnectionIO[A]){
      def queryNow :A = transactor.trans.apply(query).unsafeRunSync()
    }
  }
}
