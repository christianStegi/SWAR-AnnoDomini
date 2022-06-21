package model.fileIOComponent.Impl

import org.scalatest.wordspec.AnyWordSpec

// import model.fileIOComponent.Impl.FileIOAsJSON

import org.scalatest.matchers.should.Matchers._
import model.playerComponent.Player
import model.gameComponent.Deck
import model.gameComponent.Table
import model.gameComponent.Card
import play.api.libs.json.Json

class FileInOutSpec extends AnyWordSpec:

    val fileIO = FileIOAsJSON()
    val card1: Card = Card("text of card 1000", 1000)
    val card2: Card = Card("text of card 2000", 2000)
    val card3: Card = Card("text of card 3000", 3000)
    val card4: Card = Card("text of card 4000", 4000)
    val cardList: List[Card] = List(card1, card2)
    val cardList2: List[Card] = List(card3, card4)
    val player1: Player = Player("Alfred", cardList)
    val player2: Player = Player("Berta", cardList2)
    val playerList: List[Player] = List(player1, player2)
    val deck: Deck = Deck(cardList,cardList2)
    val table: Table = Table(playerList, cardList, deck, 3)
    
    def printObjectsToBeAbleToCompareLaterOutput: Unit =
        fileIO.save(table)
        val loadedForPrePrintingAndOverview = fileIO.loadJSONFromFile()
        println("loadedForPrePrintingAndOverview:")
        println(loadedForPrePrintingAndOverview)
        fileIO.load

    // printObjectsToBeAbleToCompareLaterOutput

    "A FileIO object" when {
        // "saving" should {
        //     "be able to write a card object as JSON object" in {}
        //     "be able to write a cardList as JSON object" in {}
        // }

        "calling cardToJson" should {
            val cardAsJson = fileIO.cardToJSON(card1)
            
            "return a JsObject" in {
                cardAsJson.getClass.getSimpleName should be ("JsObject")
            }
            "save a card as JSON having two sub-objects year and text" in {
                val card = (cardAsJson \ "card").get

                /* Test the year */
                val year = (card \ "year").get
                (year.toString.toInt) should be (card1.year)
                
                /* Test the text */
                println("card1.text: " + card1.text)
                val text = (card \ "text").get
                // val testStr: String = "hello1"
                // (testStr) should be ("hello")
                // (text.toString) should be (card1.text)
                (text.toString) should include (card1.text)
            }
        }

        "reconstructing a table from the json-savefile through loading" should {
            "return a table-object that equals the saved table-object" in {
                fileIO.save(table)
                val reconstructedTable: Table = fileIO.load
                (table.toString) should be (reconstructedTable.toString)
            }
        }

    }

    /* 
    
    should have the permission to write to a savefile
    
    should be able to write a Table object as JSON object

        Therefor, it should be able to...

            should be able to write a playerList as JSON object

            should be able to write a tablesTable object as JSON object

            should be able to write a Deck object as JSON object

            should be able to write a punishmentCard object as JSON object

            should be able to write a cardList as JSON object

            should be able to write a card object as JSON object

            should be able to write a Player object as JSON object



    should have the permission to read from a savefile

    should be able to read a json object from a (json) savefile

    should be able to read a table from a (json) savefile


/* subprocedures von load */

    should be able to contruct a table
    from a playerlist, tablestable, deck, punishmentcards

    should be able to construct a playersList
    from several single players

    should be able to construct a cardList from single cards

    should be able to construct a hand for a player

    should be able to 
    
    
    
     */