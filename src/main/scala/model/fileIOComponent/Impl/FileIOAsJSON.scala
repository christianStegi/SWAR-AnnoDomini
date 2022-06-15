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
        ???
        // TODO: make Errorhandeling with Option or Try

    def dummyLoad(): Unit = 
        val parsedFileContent = loadJSONFromFile()
        val jsonTable = (parsedFileContent \ "table").get
        val table: Table = buildTableFromJSON(jsonTable)


    def loadJSONFromFile(): JsValue = 
        // val loaded: String = Source.fromFile(filename).getLines.mkString
        val contentFromFileAsString: String = Source.fromFile(filename).mkString
        Json.parse(contentFromFileAsString)


    def buildTableFromJSON(jsonTable: JsValue): Table =
        val players: List[Player] = buildPlayersFromJSON(jsonTable)
        val table: List[Card] = buildTablesTableHandFromJSON(jsonTable)
        val deck: Deck = getDeckFromJSON(jsonTable)
        val punishment: Int = getPunishmentFromJSON(jsonTable)
        Table(players, table, deck, punishment)
        

    def buildPlayersFromJSON(jsonTable: JsValue): List[Player] = 
        val players = (jsonTable \ "playerList").get

        /* GET LIST OF SINGLE PLAYERS */
        val listOfSinglePlayers = (players \\ "player")
        println("listOfSinglePlayers: \n" + listOfSinglePlayers)

        val playersNotJson = for (singlePlayer <- listOfSinglePlayers) yield {
            // println("singlePlayer: " + singlePlayer)
            val playerName = (singlePlayer \ "name").get
            // println("playerName: " + playerName)
            val playersHand: List[Card] = getCardListFromJSON(singlePlayer)
            Player(name = playerName.toString, hand = playersHand)
        }

        val playersAsList: List[Player] = playersNotJson.toList
        playersAsList
        

    def getHandForPlayer(player: JsValue): List[Card] = 
        val playersHand = (player \ "playersHand")
        val hand = (playersHand \ "hand")
        val cardListAsJson = (hand \\ "card")


    def getCardListFromJSON(player: JsValue): List[Card] = 

        /* GET CARDLIST */
        val playersHand = (player \ "playersHand")
        val hand = (playersHand \ "hand")
        val cardListAsJson = (hand \\ "card")

        /* GET SINGLE CARDS FROM CARDLISTASJSON */
        val cardListNotJSON = for (singleCard <- cardListAsJson) yield {
            println("singleCard: " + singleCard)
            getCardFromJSON(singleCard)
        }

        val cardListDifferentFormat: List[Card] = cardListNotJSON.toList
        cardListDifferentFormat
        

    def getCardFromJSON(singleCard: JsValue): Card = 
        val cardYear = (singleCard \ "year").get.toString.toInt
        val cardText: String = Json.stringify((singleCard \ "text").get)
        val card = Card(text = cardText, year = cardYear)
        card

    def buildTablesTableHandFromJSON(jsonTable: JsValue): List[Card] = 
        (jsonTable \ "hand").get
        ???

    def getDeckFromJSON(jsonTable: JsValue): Deck = 
        ???

    def dummy_getDeckFromJSON(jsonTable: JsValue): Unit = 
        val deck = (jsonTable \\ "deck")

        /* hand extrahieren und wie bei playerFromJson-Methoden cardlist bzw hand auslesen */

        println(deck)
        

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