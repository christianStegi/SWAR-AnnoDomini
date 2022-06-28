package controller

import model.gameComponent.{Card, Table, TableGenerator}
import model.fileIOComponent.Impl.FileIOAsXML
import util.{Observable, UndoManager}
import controller.commands.{DoubtCommand, PlaceCardCommand}

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Route

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.io.StdIn
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.HttpMethods
import akka.protobufv3.internal.compiler.PluginProtos.CodeGeneratorResponse.File
import akka.http.scaladsl.model.MediaTypes
import scala.language.postfixOps
import scala.util.{Failure, Success, Try}
import akka.http.scaladsl.unmarshalling.Unmarshal
import model.fileIOComponent.Impl.FileIOAsJSON
import model.dbComponent.impl.DAOMongoDBImpl
import play.api.libs.json.Json


class Controller(var table: Table) extends Observable{

  //AKKA STUFF (for REST)
  val fileIoHost: String = "localhost"
  val fileIoPort: Int = 8081
  val mongoDBHost: String = "localhost"
  val mongoDBPort: Int = 8082
  val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "my-system")
  given ActorSystem[Any] = system
  val executionContext: ExecutionContextExecutor = system.executionContext
  given ExecutionContextExecutor = executionContext

  val undoManager = UndoManager()
  val fileIOAsXML = FileIOAsXML()
  val fileIOAsJSON = FileIOAsJSON()
  val mongoImpl = DAOMongoDBImpl()


  def createTestTable(noOfPlayers:Int): Unit = {
    val tb = TableGenerator(noOfPlayers, 40)
    table = tb.createTable
    notifyObservers()
  }

  def tableToString: String = table.showTable + table.showCurrentPlayer

  def placeCard(card:Int, place:Int): Unit ={
    undoManager.doStep(PlaceCardCommand(card, place, this, table.copy()))
    notifyObservers()
  }

  def doubt():Unit = {
    undoManager.doStep(DoubtCommand(this, table.copy()))
    notifyObservers()
  }

  def showAllPlayers(): String = table.showAllPlayers
  def confirmWinner: Any = if table.playerWon then "congratulations, player: " + table.previousPlayer + " has won!"
  def getCard(index:Int): Card = table.takePlayerCard(index)._1

  def undo(): Unit ={
    undoManager.undoStep
    notifyObservers()
  }

  def redo(): Unit ={
    undoManager.redoStep
    notifyObservers()
  }

  def saveGameAsXML(): Unit = fileIOAsXML.save(table)

  def loadGameFromXML(): Unit= {
    table = fileIOAsXML.load
    notifyObservers()
  }

  def saveGameAsJSON(): Unit = fileIOAsJSON.save(table)

  def loadGameFromJSON(): Unit= {
    table = fileIOAsJSON.load
    notifyObservers()
  }


  def saveGameViaRestAsXML(): Unit = {
    val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "SingleRequest")
    given ActorSystem[Any] = system
    val executionContext: ExecutionContextExecutor = system.executionContext
    given ExecutionContextExecutor = executionContext

    val tableAsXml = fileIOAsXML.tableToXML(table)
    val xmlAsString = tableAsXml.toString

    println("xmlAsString:\n")
    println(xmlAsString)
    
    val responseFuture: Future[HttpResponse] = Http().singleRequest(
      HttpRequest(
        method =  HttpMethods.PUT,
        uri = s"http://$fileIoHost:$fileIoPort/fileIO/xml/save",
        entity = HttpEntity(ContentTypes.`text/xml(UTF-8)`, xmlAsString)
      ))
  }

  
  def loadGameViaRestAsXML(): Future[Boolean] = {
    val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "SingleRequest")
    given ActorSystem[Any] = system
    val executionContext: ExecutionContextExecutor = system.executionContext
    given ExecutionContextExecutor = executionContext

    val responseFuture: Future[HttpResponse] = Http().singleRequest(
      HttpRequest(
        uri = s"http://$fileIoHost:$fileIoPort/fileIO/xml/load"
      )
    )

    responseFuture
      .onComplete {

        case Success(value) =>

          println("%%%%%%%% loadGameViaRestAsXML: in load on complete Success(value) %%%%%%%%")
          
          val tableAsString = Unmarshal(value.entity).to[String]

          tableAsString.onComplete {
            case Success(value) =>
              val nowAsXml = scala.xml.XML.loadString(value.toString)
              table = fileIOAsXML.tableFromXML(nowAsXml)
              println("table:")
              println(table)
              notifyObservers()
              return Future(true)
            case Failure(exception) =>
              return Future(false)
          }

        case Failure(exception) =>
          println("%%%%%%%% Failure in Controller.loadGameViaRestAsXML -  REST-loading did not work. %%%%%%%%")
          return Future(false)
      }
    return Future(false)
  }

  
  def createEmptyMongoDocument(): Unit = 
    val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "SingleRequest")
    given ActorSystem[Any] = system
    val executionContext: ExecutionContextExecutor = system.executionContext
    given ExecutionContextExecutor = executionContext

    val responseFuture: Future[HttpResponse] = Http().singleRequest(
      HttpRequest(
        method =  HttpMethods.PUT,
        uri = s"http://$mongoDBHost:$mongoDBPort/mongodb/create",
        entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, "creating empty game document...")
      )
    )


  def deleteMongoDocuments(): Unit = 
    val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "SingleRequest")
    given ActorSystem[Any] = system
    val executionContext: ExecutionContextExecutor = system.executionContext
    given ExecutionContextExecutor = executionContext

    val responseFuture: Future[HttpResponse] = Http().singleRequest(
      HttpRequest(
        method =  HttpMethods.PUT,
        uri = s"http://$mongoDBHost:$mongoDBPort/mongodb/deleteAllDocuments",
        entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, "deleting all documents...")
      )
    )
  

  def saveGameWithMongoDB(): Unit = {
    val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "SingleRequest")
    given ActorSystem[Any] = system
    val executionContext: ExecutionContextExecutor = system.executionContext
    given ExecutionContextExecutor = executionContext
    val tableAsJSON = fileIOAsJSON.tableToJson(table)
    val tableAsJSONString = fileIOAsJSON.tableToJsonString(table)
    // println("tableAsJSONString:\n")
    // println(tableAsJSONString)
    
    val responseFuture: Future[HttpResponse] = Http().singleRequest(
      HttpRequest(
        method =  HttpMethods.PUT,
        uri = s"http://$mongoDBHost:$mongoDBPort/mongodb/save",
        // entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, tableAsJSONString)
        entity = HttpEntity(ContentTypes.`application/json`, tableAsJSONString)
      ))

  }


  def loadGameWithMongoDB(): Future[Boolean] = {
    val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "SingleRequest")
    given ActorSystem[Any] = system
    val executionContext: ExecutionContextExecutor = system.executionContext
    given ExecutionContextExecutor = executionContext

    val responseFuture: Future[HttpResponse] = Http().singleRequest(
      HttpRequest(
        uri = s"http://$mongoDBHost:$mongoDBPort/mongodb/load"
      ))

    responseFuture
      .onComplete {

        case Success(value) =>

          println("%%%%%%%% loadGameWithMongoDB: in load on complete Success(value) %%%%%%%%")
          
          val tableAsString = Unmarshal(value.entity).to[String]

          tableAsString.onComplete {
            case Success(value) =>
              val nowAsJSON = Json.parse(value)
              // println("nowAsJSON:")
              // println(nowAsJSON.toString)
              table = fileIOAsJSON.buildTableFromJSON(nowAsJSON)

              println("table:")
              println(table)
              notifyObservers()

              notifyObservers()
              return Future(true)
            case Failure(exception) =>
              return Future(false)
          }

        case Failure(exception) =>
          println("%%%%%%%% Failure in Controller.loadGameWithMongoDB -  loading did not work. %%%%%%%%")
          return Future(false)
      }
    return Future(false)
  }

}