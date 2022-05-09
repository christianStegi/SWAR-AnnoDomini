package controller

import model.gameComponent.{Card, Table, TableGenerator}
import model.fileIOComponent.XMLImpl.FileIO
import model.fileIOComponent.XMLImpl.FileIORestImplXML
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

class Controller(var table: Table) extends Observable{


//AKKA STUFF (REST)
  val fileIoHost: String = "localhost"
  val fileIoPort: Int = 8081
  val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "my-system-Controller")
  given ActorSystem[Any] = system
  val executionContext: ExecutionContextExecutor = system.executionContext
  given ExecutionContextExecutor = executionContext




  val undoManager = new UndoManager
  val fileIO = new FileIO


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

  def saveGame(): Unit = fileIO.save(table)

  def loadGame(): Unit= {
    table = fileIO.load
    notifyObservers()
  }

  def saveGameViaRest(): Unit = fileIOViaRest.save(table)



  override def save(): Unit = {
    val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "SingleRequest")
    given ActorSystem[Any] = system
    val executionContext: ExecutionContextExecutor = system.executionContext
    given ExecutionContextExecutor = executionContext

    val responseFuture: Future[HttpResponse] = Http().singleRequest(
      HttpRequest(
        method =  HttpMethods.PUT,
        uri = s"http://$fileIoHost:$fileIoPort/fileIO/json/save",
        entity = HttpEntity(ContentTypes.`application/json`, Json.stringify(boardToJson(board)))
      ))

    notifyObservers()
  }


  def loadGameViaRest(): Future[Boolean] = {
    val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "SingleRequest")
    given ActorSystem[Any] = system
    val executionContext: ExecutionContextExecutor = system.executionContext
    given ExecutionContextExecutor = executionContext

    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = s"http://$fileIoHost:$fileIoPort/fileIO/xml/load"))

    responseFuture
      .onComplete {
        case Success(value) =>
          val tableAsString = Unmarshal(value.entity).to[String]
          boardAsString.onComplete {
            case Success(value) =>
              board = loadFromString(value)
              notifyObservers()
              return Future(true)
            case Failure(exception) =>
              return Future(false)
          }
        case Failure(exception) =>
          return Future(false)
      }
    return Future(false)
  }



  //nur kopiervorlage, kann nachher gelöscht werden!
  override def load_CopiedMethod(): Future[Boolean] = {
    val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "SingleRequest")
    given ActorSystem[Any] = system
    val executionContext: ExecutionContextExecutor = system.executionContext
    given ExecutionContextExecutor = executionContext

    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = s"http://$fileIoHost:$fileIoPort/fileIO/json/load"))

    responseFuture
      .onComplete {
        case Success(value) =>
          val boardAsString = Unmarshal(value.entity).to[String]
          boardAsString.onComplete {
            case Success(value) =>
              board = loadFromString(value)
              notifyObservers()
              return Future(true)
            case Failure(exception) =>
              return Future(false)
          }
        case Failure(exception) =>
          return Future(false)
      }
    return Future(false)
  }

}