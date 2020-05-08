package controllers
import db.{Account, DataBase}
import javax.inject.Inject
import play.api.mvc._

class AdminController @Inject()(database:DataBase)  extends Controller{
  import  database.Implicits._

  def accounts =Authentication{implicit request=>
    val accounts = Account.selectAll().queryNow
    Ok(views.html.accounts(accounts))
  }
}
