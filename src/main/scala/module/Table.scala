package module

val cardsPlayedThusOutOfGame: List[Card] = List.empty

case class Table(players:List[Player], table:List[Card], deck:Deck, punishmentCards:Int=3) {
  def showTable: String = "The board:\n" + table.toString() + "\n"
  def showAllPlayers: String = players.toString() // might not be needed, the other players cards should not be visible
  def showCurrentPlayer: String = currentPlayer.toString
  def showStatus: String =
    showTable + players.map(p => p.name + ": (" + p.checkNumOfCards + ")\n").toString()

  def currentPlayer: Player = players.head
  def previousPlayer: Player = players.last
  def getNumOfPlayers: Int = players.length

  def playerWon: Boolean = previousPlayer.hasWon

  def takePlayerCard(index:Int):(Card,Player) = currentPlayer.getCard(index)
  def placeCard(card: Card) (position: Int): List[Card] = {
    // place card at given position
    // position 0 = far left, supposed to be earliest date
    // position table.length = far right, supposed to be latest date
    table.splitAt(position)._1 ::: card :: table.splitAt(position)._2
  }
  def playerDrawsCard(player: Player, numOfCards:Int=1): (Player, Deck) =
    if (deck.length == 0) 
    then
        //neues Deck anlegen aus den bereits gespielten Karten.
        //Problem: nicht bekannt, welche Karten bisher schon gespielt wurden.
        //bisherige "table"-Liste muss jedesmal bei Verwerfen (in punishPlayer) zu einer Merk-Liste 
        //hinzugefügt werden.
        ???
    (player.
      giveCards(deck.drawCard(numOfCards)._1), Deck(deck.drawCard(numOfCards)._2))

  def playerPlacesCard(takeThisCard:Int) (placeCardAt:Int): Table = {

    val playerCard = takePlayerCard(takeThisCard)._1
    val player = takePlayerCard(takeThisCard)._2
    Table(getNextPlayer(player), placeCard(playerCard) (placeCardAt), this.deck)
    // TODO: this could be done with copy, check if it makes sense and implement if reasonable
  }


  // TODO: make compatible with 1 Player session
  def getNextPlayer(player:Player=currentPlayer, keepCurrentPlayer:Boolean=false): List[Player] = {
    keepCurrentPlayer match
      case false => players.tail ::: List(player)
      case true => changePrevPlayer(player)
  }

  def changePrevPlayer(changedPlayer:Player): List[Player] = {
    currentPlayer :: players.tail.dropRight(1) ::: List(changedPlayer)
  }

  def allCardsInOrder: Boolean = {
    val sortedList = table.sortWith(_.year < _.year)
    table == sortedList
  }


  def playerDoubtsCards:Table = {
    // TODO: change to match case and put stuff in different functions for better overview
    allCardsInOrder match
      case true => punishPlayer(currentPlayer, punishmentCards-1)
      case false =>
          //hier bisher gespielte Karten merken. Nötig, wenn deck keine Karten mehr hat und man eine neues Deck 
          //aus den bereits gespielten Karten machen muss.
          //um die Liste veränderbar zu halten: ausserhalb der Table-Class definiert. var statt val?! Listbuffer statt List?
          //Use a ListBuffer when you want a “List” you can modify
          //https://alvinalexander.com/scala/how-add-elements-to-a-list-in-scala-listbuffer-immutable/
          
          table::cardsPlayedThusOutOfGame
          

          punishPlayer(previousPlayer, punishmentCards)
      


  }

  def punishPlayer(player: Player, numOfCards: Int): Table ={
    val changedPlayer = playerDrawsCard(player, numOfCards)._1
    val newDeck = playerDrawsCard(player, numOfCards)._2
    Table(
      getNextPlayer(changedPlayer, numOfCards==punishmentCards),
      newDeck.deckHeadAsList,
      newDeck.playDeck
    )
  }
}