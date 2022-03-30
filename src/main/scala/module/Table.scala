package module

case class Table(players:List[Player], table:List[Card], deck:Deck) {
  def showTable: String = "The board:\n" + table.toString() + "\n"
  def showAllPlayers: String = players.toString() // might not be needed, the other players cards should not be visible
  def showCurrentPlayer: String = currentPlayer.toString
  def showStatus: String =
    showTable + players.map(p => p.name + ": (" + p.checkNumOfCards + ")\n").toString()

  def currentPlayer: Player = players.head
  def previousPlayer: Player = players.tail.head
  def getNumOfPlayers: Int = players.length

  def playerWon: Boolean = previousPlayer.hasWon

  def takePlayerCard(index:Int):(Card,Player) = currentPlayer.getCard(index)
  def placeCard(card: Card) (position: Int): List[Card] = {
    // place card at given position
    // position 0 = far left, supposed to be earliest date
    // position table.length = far right, supposed to be latest date
    table.splitAt(position)._1 ::: card :: table.splitAt(position)._2
  }
  def playerDrawsCard(player: Player) (numOfCards:Int=1): (Player, Deck) =
    (player.
      giveCards(deck.drawCard(numOfCards)._1), Deck(deck.drawCard(numOfCards)._2))

  def playerPlacesCard(takeThisCard:Int) (placeCardAt:Int): Table = {

    val playerCard = takePlayerCard(takeThisCard)._1
    val player = takePlayerCard(takeThisCard)._2
    Table(getNextPlayer(player), placeCard(playerCard) (placeCardAt), this.deck)
    // TODO: this could be done with copy, check if it makes sense and implement if reasonable
  }


  def getNextPlayer(changedCurPlayer:Player=currentPlayer): List[Player] = {
    // just put the current player at the end of the list,
    // allows for current player to be changed (e.g. placed a card)
    players.tail ::: List(changedCurPlayer)
  }
  def changePrevPlayer(changedPlayer:Player): List[Player] = {
    players.splitAt(1)._1 ::: List(changedPlayer)  ::: players.splitAt(2)._2
  }



  def allCardsInOrder: Boolean = {
    val sortedList = table.sortWith(_.year < _.year)
    table == sortedList
  }
  def playerDoubtsCards:Table = {
    // TODO: change to match case and put stuff in different functions for better overview
    if(allCardsInOrder){
      val newDeck = playerDrawsCard(currentPlayer) (2)._2
      val changedPlayer = playerDrawsCard(currentPlayer) (2)._1
      Table(
        getNextPlayer(changedPlayer), // current player draws 2 cards and gets to the end of the list
        List(newDeck.deckHead), // new table, with 1 card on it
        Deck(newDeck.deckTail) // new deck, with the drawn cards removed
      )
    } else{
      val newDeck = playerDrawsCard(previousPlayer) (3)._2
      val changedPlayer = playerDrawsCard(previousPlayer) (3)._1
      Table(
        changePrevPlayer(changedPlayer),
        List(newDeck.deckHead), // new table, with 1 card on it
        Deck(newDeck.deckTail) // new deck, with the drawn cards removed
      )
    }
  }
}