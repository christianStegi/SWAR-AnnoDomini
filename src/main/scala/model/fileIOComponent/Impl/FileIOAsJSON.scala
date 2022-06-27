package model.fileIOComponent.Impl

import model.fileIOComponent.FileIOInterface
import model.gameComponent.{Card, Deck, Table}
import model.playerComponent.Player

import play.api.libs.json._
import java.io.PrintWriter
import java.io.File
import scala.io.Source
import play.api.libs.json.JsPath.json
import scala.util.Try
import scala.util.Success
import scala.util.Failure
import org.bson.json.JsonObject


class FileIOAsJSON extends FileIOInterface {

    val filename: String = "./savedAsJson.json"

/* 

//// verschiedene Varianten des Zugriffs auf JSON-Objekte via Play-API: 

(objToSearchThrough \ "fieldName")
        Return the property corresponding to the fieldName, supposing we have a JsObject.
        Also liefert die value-Seite eines JSON-Objekts zurück.

val jsonTable = (parsedFileContent \ "table").get
        das .get wandelt das returnte JsLookUpResult in ein JsValue um


val playerListAsObj = (objToSearchThrough \ "fieldName")(0)
        ODER ZB. 
        val playerListAsObj = (objToSearchThrough)(0)
        liefert Elemente zurück bei JsArrays

def (fieldName: String): Seq[JsValue]
        Lookup for fieldName in the current object and all descendants.
        liefert eine Seq zurück!

 */
    
    override def load: Table = 
        val parsedFileContent: JsValue = loadJSONFromFile()
        val jsonTable: JsValue = (parsedFileContent \ "table").get
        buildTableFromJSON(jsonTable)
        // TODO: make Errorhandeling with Option or Try


    def loadJSONFromFile(): JsValue = 
        val contentFromFileAsString: String = Source.fromFile(filename).mkString
        Json.parse(contentFromFileAsString)


    def buildTableFromJSON(jsonTable: JsValue): Table =
        val players: List[Player] = buildPlayersFromJSON(jsonTable(0))
        val table: List[Card] = buildTablesTableHandFromJSON(jsonTable(1))
        val deck: Deck = getDeckFromJSON(jsonTable(2))
        val punishment: Int = getPunishmentFromJSON(jsonTable(3))
        Table(players, table, deck, punishment)
        

    def buildPlayersFromJSON(jsonTable: JsValue): List[Player] = 
       
        val players = (jsonTable \ "playerList")
        val listOfSinglePlayers = (players \\ "player")

        val playersNotJson = for (singlePlayer <- listOfSinglePlayers) yield {
            val playerName = (singlePlayer \ "name").get
            /* clear string from ' " ' (highquote-char) */
            val cleanedPlayerName = playerName.toString.substring(1, (playerName.toString.length -1))
            val playersHand: List[Card] = getHandForPlayer(singlePlayer)
            Player(name = cleanedPlayerName, hand = playersHand)
        }

        val playersAsList: List[Player] = playersNotJson.toList
        playersAsList
        

    def getHandForPlayer(player: JsValue): List[Card] = 
        val playersHand = (player \ "playersHand").get
        getHandFromEntity(playersHand)


    def getHandFromEntity(entity: JsValue): List[Card] = 
        val hand = (entity \ "hand").get
        getCardListFromJSON(hand)


    def getCardListFromJSON(hand: JsValue): List[Card] = 

        val cardListAsJson = (hand \\ "card")
        val cardListAsSeq = for (singleCard <- cardListAsJson) yield {
            getCardFromJSON(singleCard)
        }
        val cardListDifferentFormat: List[Card] = cardListAsSeq.toList
        cardListDifferentFormat
        

    def getCardFromJSON(singleCard: JsValue): Card = 
        val cardYear = (singleCard \ "year").get.toString.toInt
        // val cardText: String = Json.stringify((singleCard \ "text").get)
        val cardText: String = (singleCard \ "text").get.toString

        /* clear string from ' " ' (highquote-char) */
        val cleanCardText = cardText.substring(1, cardText.length - 1)
        val card = Card(text = cleanCardText, year = cardYear)
        card
        

    def buildTablesTableHandFromJSON(jsonTable: JsValue): List[Card] = 

        val myTry = Try ((jsonTable \ "tablesTable").get)
        myTry match {
            case Success(result) => {
                val hand = getHandFromEntity(result)
                hand
            }
            case Failure(f) => 
                println("Failure-Fall: tablesTable konnte nicht gelesen werden, daher wird eine leere Liste zurückgegeben.")
                println(f)
                List[Card]()        
        }
        

    def getDeckFromJSON(jsonTable: JsValue): Deck = 
        val deck = (jsonTable \ "deck").get
        val cardList: List[Card] = getHandFromEntity(deck)
        Deck(cardList)


    def getPunishmentFromJSON(jsonObj: JsValue): Int = 
        val punValue = jsonObj("punishmentCards")
        val cleanedString = punValue.toString().substring(1,2)
        cleanedString.toInt


    override def save(table: Table): Unit = {
        val pw = new PrintWriter(new File(filename))
        pw.write(Json.prettyPrint(tableToJson(table)))
        pw.close
    }

    def cardToJSON(card: Card): JsValue = 
        Json.obj(
            "card" -> Json.obj(
                // "year" -> JsNumber(card.year),
                // "text" -> JsString(card.text)
                "year" -> card.year,
                "text" -> card.text
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


    def tablesTableToJSON(cards: List[Card]): JsValue = 
        Json.obj(
            "tablesTable" -> cardListToJSON(cards)
        )
        

    def tableToJson(table: Table): JsValue =
        Json.obj(
            "table" -> Json.toJson(
                playerListToJSON(table.players),
                tablesTableToJSON(table.table),
                deckToJSON(table.deck),
                Json.obj(
                    "punishmentCards" -> table.punishmentCards.toString()
                )
            )
        )

    def tableToJsonString(table: Table): String =
        Json.prettyPrint(tableToJson(table))

}