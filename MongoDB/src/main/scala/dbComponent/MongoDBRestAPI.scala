package dbComponent

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.model.StatusCodes

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.io.StdIn
import scala.util.{Failure, Success, Try}

import dbComponent.impl.DAOMongoDBImpl


object MongoDBRestAPI {

    val host = "localhost"
    val port = 8082

    val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "my-system")
    given ActorSystem[Any] = system
    val executionContext: ExecutionContextExecutor = system.executionContext
    given ExecutionContextExecutor = executionContext

    val mongoDbImpl = DAOMongoDBImpl()

    @main def run(): Unit = {

        println("\n============== MongoDBRestAPI server running now...  ==============\n")

        val route = concat(

            path("mongodb" / "create") {
                put {
                    mongoDbImpl.create
                    complete(StatusCodes.OK, "document was created")
                }
            },
            path("mongodb" / "load") {
                get {
                    Try(mongoDbImpl.read) match {
                        case Success(table) => {
                            // complete(HttpEntity(ContentTypes.`application/json`, table))
                            complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, table))       
                        }
                        case Failure(exception) => complete(StatusCodes.BadRequest, "table could not be loaded")
                    }
                }
            },
            path("mongodb" / "save") {
                put {
                    entity(as[String]) { table =>

                        mongoDbImpl.update(table)
                        complete(StatusCodes.OK, "table was saved")
                        // Try() match {
                        // // Try(xmlHelper.load) match {
                        //   case Success(table) => complete(StatusCodes.OK, "table was saved")
                        //   case Failure(exception) => complete(StatusCodes.BadRequest, "table could not be saved")
                        // }
                    }
                }
            },
            path("mongodb" / "deleteAllDocuments") {
                put {
                    entity(as[String]) { message =>
                        mongoDbImpl.delete
                        complete(StatusCodes.OK, "document was deleted")
                    }
                }
            })


        val bindingFuture = Http().newServerAt(host, port).bind(route)

        // delete now gets called in .create method for now
        // mongoDbImpl.delete
        mongoDbImpl.create        

        println(s"Server now online. You can also visit the corresponding URL in a browser. Press RETURN to stop...")
        StdIn.readLine() // let it run until user presses return
        bindingFuture
        .flatMap(_.unbind()) // trigger unbinding from the port
        .onComplete(_ => system.terminate()) // and shutdown when done
    }

}
