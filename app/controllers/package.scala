import java.net.URLEncoder

import model.User
import play.api.mvc.{RequestHeader, Result}
import play.api.mvc.Results._
import play.api.mvc.Security.AuthenticatedBuilder

package object controllers {
  object Security{
    private[this] val userSessionKey = "current_user"

    def getUserFromRequest(request:RequestHeader) =
      request.session.get(userSessionKey).flatMap(User.formJson)

    def doLogin(user: User)(result:Result) =result.withSession(userSessionKey->user.toJsonStr)

  }

  val Authentication = AuthenticatedBuilder[User](
    userinfo = Security.getUserFromRequest,
    onUnauthorized = { request =>
      Unauthorized(views.html.unauthorized(URLEncoder.encode(request.uri, "utf-8")))
    })
}
