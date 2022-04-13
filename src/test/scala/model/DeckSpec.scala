package model

import model.cardModule.Card
import model.deckModule.{Deck, Deckgenerator}
import org.scalatest.wordspec.AnyWordSpec

class DeckSpec extends AnyWordSpec{
  val deckGen:Deckgenerator = new Deckgenerator
  val deck:Deck = deckGen.createRandomDeck()
  val card:Card = Card("Card No. 0", 0)
  "A Deck" should{
    "Have a value cards, which is a List of Card" in {
      assert(deck.cards.isInstanceOf[List[Card]])
    }
    "Have a function draw Card" in {
      /*
      assert(deck.drawCard(1)._1.isInstanceOf[Card])
      assert(deck.drawCard._1 == deck.cards.head)
      assert(deck.drawCard._2.isInstanceOf[List[Card]])
      -> removed the methodoverloading for drawCard, because it stopped working in Scala 3 (ambiguous overload)
      TODO: repair methodoverlaod, as it is kinda handy and I wanna know why it stopped working
        */

      assert(deck.drawCard(4)._1.isInstanceOf[List[Card]])
      assert(deck.drawCard(4)._2.isInstanceOf[Deck])
      assert(deck.drawCard(4)._1.length == 4)
    }
    "Have a function addCard" in {
      assert(deck.addCard(card).cards.contains(card))
      assert(deck.addCard(card).length == deck.length + 1)
      assert(deck.addCard(deck).length == deck.length*2)
    }
    "Have a function shuffle" in {
      assert(deck != deck.shuffle)
      assert(deck.cards.length == deck.shuffle.cards.length)
    }
    "Have a function deckHead" in {
      assert(deck.deckHeadAsCard.isInstanceOf[Card])
    }
    "Have a function deckTail" in {
      assert(deck.deckTail.isInstanceOf[List[Card]])
      assert(deck.deckTail == deck.cards.tail)
    }
    "Have a function lenght" in{
      assert(deck.length == 10)
    }
    "Have a function playDeck" in {
      assert(deck.playDeck.isInstanceOf[Deck])
      assert(deck.playDeck.cards == deck.cards.tail)
    }
    "Have a nice Stringrepresentation" in {
      assert(deck.toString.isInstanceOf[String])
      assert(deck.toString.startsWith("Deck(" ))
      // Todo: change double nextLine to single nextLine
      assert(deck.toString.endsWith("1]\n)"))
    }
    "Can be copied" in {
      assert(deck.copy() == deck)
    }
    "Have a method equals" in {
      assert(deck.equals(deck))
    }

  }

}
