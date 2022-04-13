package model.gameComponent

import scala.util.Random

case class Deck(cards: List[Card] = Nil, extraCards: List[Card] = Nil) {
  def drawCard(n: Int): (List[Card], Deck) = {
    n match
      case x if (x <= cards.size) => (cards.splitAt(n)._1, copy(cards = cards.splitAt(n)._2))
      case _ => createDeckFromExtraCards().drawCard(n)
    // TODO: Problem: if there aren't enough extraCards this will end in a loop
    // what should we do to stop an error from happening here?
  }

  def createDeckFromExtraCards(): Deck = addCard(Deck(extraCards).shuffle)

  def addCard(c: Card): Deck = Deck(c :: cards)

  def addCard(d: Deck): Deck = Deck(cards ::: d.cards)

  def addCard(l: List[Card]): Deck = Deck(cards ::: l)

  def addToExtraCards(c: Card): List[Card] = c :: extraCards

  def addToExtraCards(l: List[Card]): List[Card] = l ::: extraCards

  def shuffle: Deck = Deck(Random.shuffle(cards))

  def deckHeadAsList: List[Card] = drawCard(1)._1

  def deckHeadAsCard: Card = drawCard(1)._1.head

  def deckTail: List[Card] = cards.tail

  // these are for easing the creation of table class objects:
  def playDeck: Deck = Deck(this.deckTail) // allows to create table object directly, Is currently not used

  def length: Int = cards.length

  override def toString: String = cards.map(c => c.toString).toString().replaceAll("List", "Deck").replaceAll(",", "")
}
