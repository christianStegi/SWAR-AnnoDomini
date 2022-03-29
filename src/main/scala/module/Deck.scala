package module
import scala.util.Random

case class Deck(cards:List[Card]=Nil) {
  // TODO: get coverage to 100%
  // def drawCard: (Card, List[Card]) = (cards.head, cards.tail)
  def drawCard(n: Int) : (List[Card], List[Card]) = cards.splitAt(n)

  def addCard(c:Card): Deck = Deck(c :: cards)
  def addCard(d:Deck): Deck = Deck(cards ::: d.cards)

  def shuffle: Deck = Deck(Random.shuffle(cards))

  def deckHead: Card = drawCard(1)._1.head
  def deckTail: List[Card] = drawCard(1)._2

  // these are for easing the creation of table class objects:
  def playDeck: Deck = Deck(this.deckTail) // allows to create table object directly, w

  def length: Int = cards.length

  override def toString: String = cards.map(c => c.toString).toString().replaceAll("List", "Deck").replaceAll(",", "")
}
