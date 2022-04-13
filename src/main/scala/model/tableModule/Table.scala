package model.tableModule

import model.playerModule.Player
import model.deckModule.{Deck, Deckgenerator}
import model.cardModule.Card

case class Table(players: List[Player], table: List[Card], deck: Deck, punishmentCards: Int = 3) {
  def showTable: String = "The board:\n" + table.toString() + "\n"
  def showAllPlayers: String = players.toString() // might not be needed, the other players cards should not be visible
  def showCurrentPlayer: String = currentPlayer.toString
  def showStatus: String = showTable + players.map(p => p.name + ": (" + p.checkNumOfCards + ")\n").toString()
  def currentPlayer: Player = players.head
  def previousPlayer: Player = players.last
  def getNumOfPlayers: Int = players.length
  def playerWon: Boolean = previousPlayer.hasWon
  def takePlayerCard(index: Int): (Card, Player) = currentPlayer.getCard(index)

  def placeCard(card: Card)(position: Int): List[Card] = table.splitAt(position)._1 ::: card :: table.splitAt(position)._2

  def playerDrawsCard(player: Player, numOfCards: Int = 1): (Player, Deck) =
    (player.giveCards(deck.drawCard(numOfCards)._1), deck.drawCard(numOfCards)._2)

  def playerPlacesCard(takeThisCard: Int, placeCardAt: Int): Table = {
    val playerCard = takePlayerCard(takeThisCard)._1
    val player = takePlayerCard(takeThisCard)._2
    copy(players = getNextPlayer(player), table = placeCard(playerCard)(placeCardAt))
  }
  
  // TODO: make compatible with 1 Player session
  def getNextPlayer(player: Player = currentPlayer, keepCurrentPlayer: Boolean = false): List[Player] = {
    keepCurrentPlayer match
      case false => players.tail ::: List(player)
      case true => changePrevPlayer(player)
  }

  def changePrevPlayer(changedPlayer: Player): List[Player] = {
    currentPlayer :: players.tail.dropRight(1) ::: List(changedPlayer)
  }

  def allCardsInOrder: Boolean = {
    val sortedList = table.sortWith(_.year < _.year)
    table == sortedList
  }
  
  def playerDoubtsCards: Table = {
    // TODO: change to match case and put stuff in different functions for better overview
    allCardsInOrder match
      case true => punishPlayer(currentPlayer, punishmentCards - 1)
      case false => punishPlayer(previousPlayer, punishmentCards)
  }

  def punishPlayer(player: Player, numOfCards: Int): Table = {
    val changedPlayer = playerDrawsCard(player, numOfCards)._1
    val newDeck = playerDrawsCard(player, numOfCards)._2
    copy(
      players = getNextPlayer(changedPlayer, numOfCards == punishmentCards),
      table = newDeck.deckHeadAsList,
      newDeck.copy(extraCards = newDeck.addToExtraCards(table))
    )

  }
}
