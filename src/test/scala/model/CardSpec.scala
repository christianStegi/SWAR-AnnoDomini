package model

import model.gameComponent.Card
import org.scalatest.wordspec.AnyWordSpec

class CardSpec extends AnyWordSpec {
  val card:Card = Card("A Card Text", 10)
  "A Card" when{
      "new" should  {
        "be of its own kind" in{
          assert(card.isInstanceOf[Card])
        }
        "have a year" which{
          "is an Integeger" in {
            assert(card.year == 10)
          }
          "has a String representation" in {
            assert(card.showYear == "10")
          }
        }
        "Have a text" in{
          assert(card.text.isInstanceOf[String])
          assert(card.text == "A Card Text")
        }
        "Have a String-reperesentation" in {
          assert(card.toString == "[A Card Text]\n")
        }
        "Have a reveal function" in {
          assert(card.reveal == "year: 10 [A Card Text]\n")
        }
    }
  }
}
