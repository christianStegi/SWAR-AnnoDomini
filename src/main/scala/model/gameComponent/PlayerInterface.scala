package model.gameComponent

trait PlayerInterface {
  def showName: String
  def showHand: String
  override def toString: String

  def getCard(n: Int): (Card, Player)
  def checkNumOfCards: Int
  def giveCards(newCards: List[Card]): Player

  def hasWon: Boolean
}
