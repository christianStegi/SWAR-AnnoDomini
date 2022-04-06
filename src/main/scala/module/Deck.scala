package module
import scala.util.Random

case class Deck(cards:List[Card]=Nil, extraCards:List[Card]=Nil) {
  // def drawCard: (Card, List[Card]) = (cards.head, cards.tail)
  def drawCard(n: Int) : (List[Card], List[Card]) = cards.splitAt(n)
  // TODO: check if draw Card should return a Deck right away

  def addCard(c:Card): Deck = Deck(c :: cards)
  def addCard(d:Deck): Deck = Deck(cards ::: d.cards)
  def addCard(l:List[Card]): Deck = Deck(cards ::: l)

  def addToExtraCards(c:Card): List[Card] = c :: extraCards
  def addToExtraCards(l:List[Card]): List[Card] = l ::: extraCards
  
  def shuffle: Deck = Deck(Random.shuffle(cards))

  def deckHeadAsList: List[Card] = drawCard(1)._1
  def deckHeadAsCard: Card =  drawCard(1)._1.head
  // def deckHead(n:Int): Card = drawCard(n)._1
  def deckTail: List[Card] = drawCard(1)._2

  // these are for easing the creation of table class objects:
  def playDeck: Deck = Deck(this.deckTail) // allows to create table object directly, Is currently not used

  def length: Int = cards.length

  override def toString: String = cards.map(c => c.toString).toString().replaceAll("List", "Deck").replaceAll(",", "")
  // TODO: check why you made the toString like that
  // is this ever shown?
}
