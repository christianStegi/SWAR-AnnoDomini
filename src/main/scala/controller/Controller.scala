package controller
import controller.commands.{DoubtCommand, PlaceCardCommand}
import model.gameComponent.{Card, Table, TableGenerator}
import util.{Observable, UndoManager}



class Controller(var table:Table) extends Observable{

  val undoManager = new UndoManager

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

  def showAllPlayers(): String ={
    table.showAllPlayers
    // TODO: I don'T think this is compliant to the MVC Model, might have to change this one
  }

  def confirmWinner: Any = {
    if table.playerWon then "congratulations, player: " + table.previousPlayer + " has won!"
  }

  def getCard(index:Int): Card = table.takePlayerCard(index)._1

  def undo(): Unit ={
    undoManager.undoStep
    notifyObservers()
  }

  def redo(): Unit ={
    undoManager.redoStep
    notifyObservers()
  }
}