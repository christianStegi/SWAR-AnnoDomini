package module.playerModule

import module.cardModule.Card

case class Player(name: String = "Player", hand: List[Card]) {
  // TODO: get coverage to 100%
  def showHand: String = this.toString()

  def checkNumOfCards: Int = hand.length

  def getCard(n: Int): (Card, Player) = {
    (hand(handleBadNumber(n)), copy(hand = hand.diff(List(hand(handleBadNumber(n))))))
  }
  // TODO: add Option: if none the player won.

  def handleBadNumber(i:Int): Int = {
    i match {
      case x if(x >= hand.size) => position = hand.size -1
      case x if(x < 0) => position = 0
      case _ => position = i
    }
  }

  def giveCards(newCards: List[Card]): Player = copy(hand = hand ::: newCards)

  def hasWon: Boolean = hand.isEmpty

  override def toString: String = name + ":\n" + hand.toString().replaceAll("List", "").replaceAll(",", "")
  // TODO: check if one regex can be used for proper replacement
}
