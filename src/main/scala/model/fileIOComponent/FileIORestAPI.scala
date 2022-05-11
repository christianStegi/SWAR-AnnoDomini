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

import model.fileIOComponent.Impl.FileIORestXml
import model.fileIOComponent.FileIOInterface



object FileIORestAPI {

  val host = "localhost"
  val port = 8081

  val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "my-system-fileIO")
  given ActorSystem[Any] = system
  val executionContext: ExecutionContextExecutor = system.executionContext
  given ExecutionContextExecutor = executionContext

  val xmlHelper = FileIORestXml()

    @main def run(): Unit = {

    println("============== FileIORestAPI server running now...  ==============")

    val route = concat(
      path("fileIO" / "xml" / "load") {
        println("\n\n ============== in load before stuff starts ==============\n\n")

        get {
          Try(xmlHelper.loadAsStringForSending) match {
            case Success(table) => {
              println("\n\n ============== in load case Success ==============\n\n")
              //val tableXml = xmlFileIO.tableFromXML(table).toString
              complete(HttpEntity(ContentTypes.`text/xml(UTF-8)`, table))
              // complete(HttpEntity(ContentTypes.`text/xml(UTF-8)`, "Hier muss der Inhalt von table rein als String oder XML"))
            }
            case Failure(exception) => complete(StatusCodes.BadRequest, "table could not be loaded")
          }
        }
      },
      path("fileIO" / "xml" / "save") {
        put {

          println("\n\n============== starting save procedure... ==============\n\n")
          entity(as[String]) { table =>
            xmlHelper.saveFromString(table)
            //xmlHelper.tableFromXML(table)

            Try(xmlHelper.load) match {
              case Success(table) => complete(StatusCodes.OK, "table was saved")
              case Failure(exception) => complete(StatusCodes.BadRequest, "table could not be saved")
            }
          }
        }
      }
    )

    val bindingFuture = Http().newServerAt(host, port).bind(route)


    println(s"Server now online. You can also visit the corresponding URL. Press RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done

  }

}