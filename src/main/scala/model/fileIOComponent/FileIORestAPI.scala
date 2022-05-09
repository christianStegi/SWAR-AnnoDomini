package model.fileIOComponent.XMLImpl

import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.model.StatusCodes

import scala.io.StdIn
import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success, Try}

import model.fileIOComponent.XMLImpl.FileIO
import model.fileIOComponent.FileIOInterface


object FileIORestAPI extends FileIOInterface{

  val host = "localhost"
  val port = 8081

  val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "my-system-FileIORestApi")
  given ActorSystem[Any] = system
  val executionContext: ExecutionContextExecutor = system.executionContext
  given ExecutionContextExecutor = executionContext

  val xmlHelper = FileIO()

  @main def run(): Unit = {

    val route = concat(
      path("fileIO" / "xml" / "load") {
        get {
          Try(xmlHelper.load) match {
            case Success(table) => {
              //val tableXml = xmlFileIO.tableFromXML(table).toString
              //complete(HttpEntity(ContentTypes.`text/xml(UTF-8)`, table))
              complete(HttpEntity(ContentTypes.`text/xml(UTF-8)`, "Hier muss der Inhalt von table rein als String oder XML"))
            }
            case Failure(exception) => complete(StatusCodes.BadRequest, "table could not be loaded")
          }
        }
      },
      path("fileIO" / "xml" / "save") {
        post {
          entity(as[String]) { table =>
            //FileIO.saveFromString(table)
            //xmlHelper.tableFromXML(table)
            
            //application/xml

            Try(xmlFileIO.load) match {
              case Success(table) => complete(StatusCodes.OK, "table was saved")
              case Failure(exception) => complete(StatusCodes.BadRequest, "table could not be saved")
            }
          }
        }
      }
    )

    val bindingFuture = Http().newServerAt(host, port).bind(route)
  }

}