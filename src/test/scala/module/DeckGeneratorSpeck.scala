package module

import module.deckModule.{Deck, Deckgenerator}
import org.scalatest.wordspec.AnyWordSpec

class DeckGeneratorSpeck extends AnyWordSpec{
  "A Deckgenerator" should{
    "have a Method createRandomDeck" in{
      val deckGen = new Deckgenerator
      assert(deckGen.createRandomDeck().isInstanceOf[Deck])
      assert(deckGen.createRandomDeck().length == 10)
    }
  }
}
