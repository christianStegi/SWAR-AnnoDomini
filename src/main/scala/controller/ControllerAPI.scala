package controller

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Route


import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.io.StdIn

object ControllerAPI {

  val host = "localhost"
  val port = 8080

  val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "my-system-ControllerAPI")
  given ActorSystem[Any] = system
  val executionContext: ExecutionContextExecutor = system.executionContext
  given ExecutionContextExecutor = executionContext

  //val controller = Controller()
  
  def run(): Future[Http.ServerBinding] = {
    val route: Route =
      concat(
        path("save") {
          get {
            controller.save()
            //complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>saved current table</h1>"))
            complete(HttpEntity(ContentTypes.`application/xml`, "<h1>saved current table</h1>"))
          }
        },
        path("load") {
          get {
            controller.load()
            //complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>load saved table</h1>"))
            complete(HttpEntity(ContentTypes.`application/xml`, "<h1>load saved table</h1>"))
          }
        }
      )

    Http().newServerAt(host, port).bind(route)
  }
}