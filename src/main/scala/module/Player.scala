package module

case class Player(name:String = "Player", hand:List[Card]){
  // TODO: get coverage to 100%
  def showHand: String = this.toString()
  def checkNumOfCards: Int = hand.length

  def getCard(n:Int):(Card, Player) = (hand(n), copy(hand=hand.diff(List(hand(n)))))
  def giveCards(newCards:List[Card]):Player = copy(hand = hand:::newCards)

  def hasWon: Boolean = hand.isEmpty

  override def toString: String = name + ":\n" + hand.toString().replaceAll("List", "").replaceAll(",", "")
  // TODO: check if one regex can be used for proper replacement
}