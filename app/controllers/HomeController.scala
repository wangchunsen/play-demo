package controllers

import db.DataBase
import javax.inject._
import model.{Account, User}
import model.enum.AccountStatus
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._
import viewmodel.LoginForm

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(val messagesApi: MessagesApi, database: DataBase) extends Controller with I18nSupport {

  import database.Implicits._

  val loginForm = Form(mapping(
    "passport" -> nonEmptyText,
    "password" -> nonEmptyText(maxLength = 30, minLength = 5),
    "remember" -> boolean,
    "rdUrl" -> text(maxLength = 300))(LoginForm.apply)(LoginForm.unapply))

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Action { implicit request =>
    Ok(views.html.index())
  }

  def login(to: Option[String]) = Action { implicit request =>
    val map = Map("rdUrl" -> to.getOrElse(""))
    val errors = loginForm.bind(map).discardingErrors
    Ok(views.html.login(errors))
  }

  def postLogin = Action { implicit request =>
    val formData = loginForm.bindFromRequest
    formData.fold(
      error => BadRequest(views.html.login(error)),
      login => mapToUserOrErrorMsg(db.Account.selectByNameAndPassword(login.passport, login.password).queryNow) fold(
        user => Security.doLogin(user)(Found(login.rdUrl)),
        errorMsg => Ok(views.html.login(formData.withGlobalError(errorMsg)))
      )
    )
  }

  private def mapToUserOrErrorMsg(accountOpt: Option[Account]): Either[User, String] = {
    accountOpt map { account =>
      if (account.status == AccountStatus.Normal) Left(User(0, "", 0))
      else Right("The account is disabled, please contact admin")
    } getOrElse Right("Invalid user name or password")
  }
}
