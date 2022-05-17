package fileIORest

import akka.http.scaladsl.server.Route

// import scala.language.postfixOps

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

//import model.fileIOComponent.Impl.FileIORestXml
//import model.fileIOComponent.FileIOInterface

import fileIORest.impl.LittleTestFile
import fileIORest.impl.FileIORestXml_SMALL



object FileIORestAPI {

  val host = "localhost"
  val port = 8081

  val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "my-system")
  given ActorSystem[Any] = system
  val executionContext: ExecutionContextExecutor = system.executionContext
  given ExecutionContextExecutor = executionContext

  val xmlHelper = FileIORestXml_SMALL()

    @main def run(): Unit = {

    println("\n============== FileIORestAPI server running now...  ==============\n")

    val route = concat(
      path("fileIO" / "xml" / "load") {
        println("\n============== in load before stuff starts ==============\n")

        get {
          Try(xmlHelper.loadAsStringForSending) match {
            case Success(table) => {
              println("\n============== in load case Success ==============\n")
              complete(HttpEntity(ContentTypes.`text/xml(UTF-8)`, table))
            }
            case Failure(exception) => complete(StatusCodes.BadRequest, "table could not be loaded")
          }
        }
      },
      path("fileIO" / "xml" / "save") {
        put {

          println("\n============== starting save procedure... ==============\n")
          entity(as[String]) { table =>
            xmlHelper.saveFromString(table)
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


    println(s"Server now online. You can also visit the corresponding URL. Press RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done

  }

}