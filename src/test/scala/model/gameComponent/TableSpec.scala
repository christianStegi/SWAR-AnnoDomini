package model.gameComponent

import model.gameComponent.{Card, Deck, TableGenerator, Table}
import org.scalatest.wordspec.AnyWordSpec

class TableSpec extends AnyWordSpec {
  val tg = TableGenerator(2, 40, 5)
  val table = tg.createTable
  "a Table:" when(
    "in general" should{
      "has List of Playeres, that can play the game"{

      }
      "has a Deck"{

      }
      "an Int punishmendCards, " +
        "that determines how many cards a player draws when punished"{

      }
      "has a good Stringrepresentations" in{

      }
    }
    "when new" should {
      "have one Card in itself" in{
        assert(table.cardsOnTable.length == 1)
      }
      "have all cards in order"{

      }
      "have no winning player"{

      }
    }
    "when a player places a card"{

    }
    "if a player doubts" should{
      "the doubting player should be punished, if the order of cards on the table was correct" in{

      }
      "the previous player should be punished, if the order of cards on the table was incorrect" in{

      }

    }
    "if a player has no more cards" should{
      "declare check if the order of the table cards are correct" in{

      }
      "declare the player the winner" in{

      }
    }
  )


}
