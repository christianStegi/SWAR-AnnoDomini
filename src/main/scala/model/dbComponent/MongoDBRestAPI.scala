package model.dbComponent

import akka.http.scaladsl.server.Route

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.io.StdIn

import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.model.StatusCodes

import scala.io.StdIn
import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success, Try}
import model.fileIOComponent.Impl.FileIOAsJSON
import model.dbComponent.impl.DAOMongoDBImpl


object MongoDBRestAPI {

  val host = "localhost"
  val port = 8082

  val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "my-system")
  given ActorSystem[Any] = system
  val executionContext: ExecutionContextExecutor = system.executionContext
  given ExecutionContextExecutor = executionContext

  val jsonHelper = FileIOAsJSON()
//   val xmlHelper = FileIORestXml_SMALL()
    val mongoDbImpl = DAOMongoDBImpl()

    @main def run(): Unit = {

    println("\n============== MongoDBRestAPI server running now...  ==============\n")

    val route = concat(
      path("mongodb" / "load") {

        get {
          Try(mongoDbImpl.read) match {
            case Success(table) => {
            //   complete(HttpEntity(ContentTypes.`text/xml(UTF-8)`, table))
              complete(HttpEntity(ContentTypes.`application/json`, table))
            }
            case Failure(exception) => complete(StatusCodes.BadRequest, "table could not be loaded")
          }
        }
      },
      path("mongodb" / "save") {
        put {

          entity(as[String]) { table =>
            // xmlHelper.saveFromString(table)
            
            mongoDbImpl.show(table)
            // mongoDbImpl.save(table)

            complete(StatusCodes.OK, "table was saved")
            // Try() match {
            // // Try(xmlHelper.load) match {
            //   case Success(table) => complete(StatusCodes.OK, "table was saved")
            //   case Failure(exception) => complete(StatusCodes.BadRequest, "table could not be saved")
            // }
          }
        }
      }
    )

    val bindingFuture = Http().newServerAt(host, port).bind(route)


    println(s"Server now online. You can also visit the corresponding URL in a browser. Press RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done

  }

}
