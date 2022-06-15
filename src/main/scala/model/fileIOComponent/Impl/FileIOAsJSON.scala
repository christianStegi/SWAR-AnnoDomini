package model.fileIOComponent.Impl

import model.fileIOComponent.FileIOInterface
import model.gameComponent.{Card, Deck, Table}
import model.playerComponent.Player

import play.api.libs.json._
import java.io.PrintWriter
import java.io.File
import scala.io.Source


class FileIOAsJSON extends FileIOInterface {

    val filename: String = "./savedAsJson.json"

    override def load: Table = 
        val loaded: String = Source.fromFile(filename).getLines.mkString
        println(loaded.getClass())
        val result = Json.parse(loaded)
        println(result)
        // result.asInstanceOf[Table]
        ???

        // load(Json.parse(loaded))
        // val table = tableFromJson()
        // TODO: make Errorhandeling with Option or Try

    def dummyLoad(): Unit = 
        // val loaded: String = Source.fromFile(filename).getLines.mkString
        val loaded: String = Source.fromFile(filename).mkString
        val parsedLoaded = Json.parse(loaded)
     
        val jsonTable = (parsedLoaded \ "table").get
        val table: Table = tableFromJSON(jsonTable)

/* 
jetzt:

Json aus Datei laden
table aus json rekonstruieren

cardFromJson
cardList...

player
    name und hand, also cardlist

usw.. 
    deck, playerlist, punishment, table


erstmal einzelne model-typen aus großem json-objekt heraus-extrahieren
dann diese einzeln als jeweilige model-objekten speichern
dann diese wiederum wieder zu einem table vereinen

/player             name    

/playersHand        hand


/card               year, text



 */


    def tableFromJSON(jsonTable: JsValue): Table =
        val players = getPlayerListFromJSON(jsonTable)
        val hand = getHandFromJSON(jsonTable)
        val deck = getDeckFromJSON(jsonTable)
        val punishment = getPunishmentFromJSON(jsonTable)
        Table(players, hand, deck, punishment)
        // case class Table(players: List[Player], table: List[Card], deck: Deck, punishmentCards: Int = 3)

    // def cardFromJSON(card: Card): Card = 
    // def cardListFromJSON(list: List[Card]): List[Card] =
    // def playerFromJSON(player: Player): Player =
    // def playerListFromJSON(pList: List[Player]): List[Player] =
    // def deckFromJSON(deck: Deck): Deck =

    def getPlayerListFromJSON(jsonTable: JsValue): List[Player] = 
        val players = (jsonTable \ "playerList").get
        val tmp = getSinglePlayer(players)
        ???

    def dummy_getPlayerListFromJSON(jsonTable: JsValue): Unit = 
        val players = (jsonTable \ "playerList").get
        val tmp = getSinglePlayer(players)

    def dummy_getSinglePlayer(players: JsValue): Unit = 

        println("Ausgangsobjekt players: \n" + players)
        println()

        /* GET LIST OF SINGLE PLAYERS */
        
        val listOfSinglePlayers = (players \\ "player")
        println("listOfSinglePlayers: \n" + listOfSinglePlayers)
        // println("listOfPlayerNames.getClass: \n" + listOfPlayerNames.getClass)
        // println("listOfPlayerNames.size: \n" + listOfPlayerNames.size)
        // println()

        // //gets the value of the complete playerList-Objekt
        // val listOfPlayerNames1 = (players \\ "playerList")
        // println("listOfPlayerNames1: \n" + listOfPlayerNames1)
        // println("listOfPlayerNames1.getClass: \n" + listOfPlayerNames1.getClass)
        // println("listOfPlayerNames1.size: \n" + listOfPlayerNames1.size)
        // println()

        // val listOfPlayerNames10 = players("playerList")(1)
        // println("listOfPlayerNames10: \n" + listOfPlayerNames10)
        // println("listOfPlayerNames10.getClass: \n" + listOfPlayerNames10.getClass)
        // println()

        // val listOfPlayerNames2 = (players \ "playerList")
        // println("listOfPlayerNames2: \n" + listOfPlayerNames2)
        // println("listOfPlayerNames2.getClass: \n" + listOfPlayerNames2.getClass)
        // println()

        // val listOfPlayerNames3 = (players \ "playerList").get
        // println("listOfPlayerNames3: \n" + listOfPlayerNames3)
        // println("listOfPlayerNames3.getClass: \n" + listOfPlayerNames3.getClass)
        // println()

        val playersNotJson = for (singlePlayer <- listOfSinglePlayers) yield {
            println("singlePlayer: " + singlePlayer)
            val playerName = (singlePlayer \ "name").get
            println("playerName: " + playerName)
            // val playersHand: List[Card] = dummy_getCardListFromJSON(singlePlayer)
            dummy_getCardListFromJSON(singlePlayer)
            // Player(name = playerName.toString, hand = playersHand)
        }

        val playersAsList = playersNotJson.toList
        

    def dummy_getCardListFromJSON(player: JsValue): Unit = 
        println()

        println("Ausgangsobjekt player: \n" + player)
        println()

        /* GET CARDLIST */
        
        val playersHand = (player \ "playersHand")
        println("playersHand: \n" + playersHand)
        println()

        val hand = (playersHand \ "hand")
        println("hand: \n" + hand)
        println()

        val cardListAsJson = (hand \\ "card")

        /* GET SINGLE CARDS FROM CARDLISTASJSON */
        
        val cardListNotJSON = for (singleCard <- cardListAsJson) yield {
            println("singleCard: " + singleCard)
            getCardFromJSON(singleCard)
        }

        // println("cardListNotJSON: " + cardListNotJSON)
        val cardListDifferentFormat = cardListNotJSON.toList
        // println("cardListDifferentFormat: " + cardListDifferen/at)


    def getCardFromJSON(singleCard: JsValue): Card = 
        println()
        println("Ausgangsobjekt singleCard: \n" + singleCard)
        println()

        val cardYear = (singleCard \ "year").get.toString.toInt
        println("cardyear: \n" + cardYear)
        println()

        val cardText = (singleCard \ "text").get.toString
        println("cardText: \n" + cardText)
        println()

        val card = Card(text = cardText, year = cardYear)
        println("card: " + card)
        card


    def getCardListFromJSON(player: JsValue): List[Card] = 
        ???

    def getSinglePlayer(players: JsValue): Player = 
        val tmpPlayer = (players \ "player").get
        tmpPlayer.getClass()
        ???



    def getHandFromJSON(jsonTable: JsValue): List[Card] = 
        (jsonTable \ "hand").get
        ???

    def getDeckFromJSON(jsonTable: JsValue): Deck = 
        (jsonTable \ "deck").get
        ???

    def getPunishmentFromJSON(jsonTable: JsValue): Int = 
        (jsonTable \ "punishmentCards").get.as[Int]
      

    override def save(table: Table): Unit = {
        val pw = new PrintWriter(new File(filename))
        // pw.write(Json.stringify(tableToJson(table)))
        pw.write(Json.prettyPrint(tableToJson(table)))
        pw.close
    }

    def cardToJSON(card: Card): JsValue = 
        Json.obj(
            "card" -> Json.obj(
                "year" -> JsNumber(card.year),
                "text" -> JsString(card.text)
            )
        )

    def cardListToJSON(list: List[Card]): JsValue =
        Json.obj(
            "hand" -> Json.toJson(
                for (elem <- list) yield cardToJSON(elem)
            )
        )

    def playerToJSON(player: Player): JsValue =
        Json.obj(
            "player" -> Json.obj(
            // "player" -> player.name,
                "name" -> player.name,
                "playersHand" -> cardListToJSON(player.hand)
            )
        )

    def playerListToJSON(pList: List[Player]): JsValue =
        Json.obj(
            "playerList" -> Json.toJson(
                for (pl <- pList) yield playerToJSON(pl)
            )
        )

    def deckToJSON(deck: Deck): JsValue =
        Json.obj(
            "deck" -> cardListToJSON(deck.cards)
        )

    def tableToJson(table: Table): JsValue =
        Json.obj(
            "table" -> Json.toJson(
                 playerListToJSON(table.players),
                cardListToJSON(table.table),
                deckToJSON(table.deck),
                Json.obj(
                    "punishmentCards" -> table.punishmentCards.toString()
                )
            )
        )


}