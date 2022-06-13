package controller

import model.gameComponent.{Card, Table, TableGenerator}
import model.persistenceComponent.XMLImpl.FileIO
import util.{Observable, UndoManager}
import controller.commands.{DoubtCommand, PlaceCardCommand}

class Controller(var table: Table) extends Observable{

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

  def showAllPlayers(): String =table.showAllPlayers
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

}