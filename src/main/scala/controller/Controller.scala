package controller
import module.{Table, TableGenerator, Card}
import util.Observable


class Controller(var table:Table) extends Observable{
  def createTestTable(noOfPlayers:Int): Unit = {
    val tb = TableGenerator(noOfPlayers, 40)
    table = tb.createTable
    notifyObservers()
  }

  def tableToString: String = table.showTable + table.showCurrentPlayer

  def placeCard(card:Int, place:Int): Unit ={
    table = table.playerPlacesCard(card) (place)
    notifyObservers()
  }

  def doubt():Unit = {
    table = table.playerDoubtsCards
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

}
