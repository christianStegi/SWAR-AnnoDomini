package model.fileIOComponent.Impl

import model.gameComponent.{Card, Deck, Table}
import model.playerComponent.Player
import play.api.libs.json.Json
import play.api.libs.json.JsValue

object JsonTesting {

    val fileIo = FileIOAsJSON()        
    val card1: Card = Card("1900", 1900)
    val card2: Card = Card("2000", 2000)
    val card3: Card = Card("2100", 2100)
    val card4: Card = Card("2200", 2200)
    val cardList: List[Card] = List(card1, card2)
    val cardList2: List[Card] = List(card3, card4)
    val player1: Player = Player("manne", cardList)
    val player2: Player = Player("freddi", cardList2)
    val playerList: List[Player] = List(player1, player2)
    val deck: Deck = Deck(cardList,cardList2)
    val table: Table = Table(playerList, cardList, deck, 3)

    @main def mainForTesting(): Unit = {
        // singeCardStuff()
        // cardListStuff()
        // playerStuff()
        // playerListStuff()
        // deckStuff()
        tableStuff()


        // println(Json.prettyPrint(obj))
    }


    def singeCardStuff() = 
        val obj = fileIo.cardToJSON(card1)
        printResult("singeCardStuff", obj)

    def cardListStuff() = 
        val obj = fileIo.cardListToJSON(cardList)
        printResult("cardListStuff", obj)

    def playerStuff() = 
        val obj = fileIo.playerToJSON(player1)
        printResult("playerStuff", obj)

    def playerListStuff() = 
        val obj = fileIo.playerListToJSON(playerList)
        printResult("playerListStuff", obj)

    def deckStuff() = 
        val obj = fileIo.deckToJSON(deck)
        printResult("deckStuff", obj)

    def tableStuff() =
        val obj = fileIo.tableToJson(table)
        printResult("tableStuff", obj)

        
    def printResult(methodName: String, obj: JsValue): Unit = 
        println("objectToPrint in " + methodName)
        println(obj.toString())
        println("type of obj:")
        println(obj.getClass())
        println(Json.prettyPrint(obj))

}
