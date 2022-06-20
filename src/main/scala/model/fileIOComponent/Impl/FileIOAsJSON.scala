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

def \\ (fieldName: String): Seq[JsValue]
        Lookup for fieldName in the current object and all descendants.
        liefert eine Seq zurück!

 */
    
    override def load: Table = 
        //parsedFileContent is a JsObject
        val parsedFileContent = loadJSONFromFile()
        
        //jsonTable is a JsArray
        val jsonTable = (parsedFileContent \ "table").get
        println("jsonTable:") 
        
        println(Json.prettyPrint(jsonTable))
        
        // buildTableFromJsObject(parsedFileContent)
        buildTableFromJSON(jsonTable)
        // TODO: make Errorhandeling with Option or Try


    def loadJSONFromFile(): JsValue = 
        // val loaded: String = Source.fromFile(filename).getLines.mkString
        val contentFromFileAsString: String = Source.fromFile(filename).mkString
        Json.parse(contentFromFileAsString)


    def buildTableFromJsObject(jsObj: JsValue): Table =
        val players: List[Player] = buildPlayersFromObj(jsObj)
        // val table: List[Card] = buildTablesTableHandFromJSON(jsonTable)
        // // val deck: Deck = getDeckFromJSON(jsonTable)
        // val deck = Deck()
        // val punishment: Int = getPunishmentFromJSON(jsonTable)
        // Table(players, table, deck, punishment)
        // Table(players, table, deck, 5)
        Table(List[Player](), List[Card](), Deck(), 5)


    def buildPlayersFromObj(jsObj: JsValue): List[Player] = 

        // val playerList = (jsObj \ "table" \ "playerList")
        /* SELEKTIERE playerList-Json-Objekt aus JsArray table */
        val playerListAsObj = (jsObj \ "table")(0)
        println("================================")
        println("playerList: ")
        println(playerListAsObj.getClass)
        println(playerListAsObj)

        val realPlayerList = (playerListAsObj \ "playerList").get
        println("================================")
        println("realPlayerList: ")
        println(realPlayerList.getClass)
        println(realPlayerList)
        
        // val listOfSinglePlayers = (realPlayerList(0) \ "player")
        val listOfSinglePlayers = (realPlayerList \\ "player")
        println("================================")
        println("listOfSinglePlayers: \n" + listOfSinglePlayers)

        for (singlePlayer <- listOfSinglePlayers) yield
            println("singlePlayer:")
            println(singlePlayer)

        // val playersNotJson = for (singlePlayer <- listOfSinglePlayers) yield {
        //     // println("singlePlayer: " + singlePlayer)
        //     val playerName = (singlePlayer \ "name").get
        //     // println("playerName: " + playerName)
        //     val playersHand: List[Card] = getHandForPlayer(singlePlayer)
        //     Player(name = playerName.toString, hand = playersHand)
        // }

        // val playersAsList: List[Player] = playersNotJson.toList
        // playersAsList
        
        
        List[Player]()
    

    def buildTableFromJSON(jsonTable: JsValue): Table =
        val players: List[Player] = buildPlayersFromJSON(jsonTable(0))
        val table: List[Card] = buildTablesTableHandFromJSON(jsonTable(1))
        val deck: Deck = getDeckFromJSON(jsonTable(2))
        // val deck = Deck()
        val punishment: Int = getPunishmentFromJSON(jsonTable(3))
        Table(players, table, deck, punishment)
        // Table(players, table, deck, 5)
        

    def buildPlayersFromJSON(jsonTable: JsValue): List[Player] = 
        println("jsonTable:")
        println(jsonTable)
        println("jsonTable.getClass: " + jsonTable.getClass)
        
        val players = (jsonTable \   "playerList")
        // val players = (jsonTable.asInstanceOf[JsObject] \ "playerList")
        // println()
        println("players: ")
        println(players)
        // println()
        println("players.getClass: " + players.getClass)
        // println()

        /* GET LIST OF SINGLE PLAYERS */
        val listOfSinglePlayers = (players \\ "player")
        // println("listOfSinglePlayers: \n" + listOfSinglePlayers)

        val playersNotJson = for (singlePlayer <- listOfSinglePlayers) yield {
            // println("singlePlayer: " + singlePlayer)
            val playerName = (singlePlayer \ "name").get
            // println("playerName: " + playerName)
            val playersHand: List[Card] = getHandForPlayer(singlePlayer)
            Player(name = playerName.toString, hand = playersHand)
        }

        val playersAsList: List[Player] = playersNotJson.toList
        playersAsList
        

    def getHandForPlayer(player: JsValue): List[Card] = 
        val playersHand = (player \ "playersHand").get
        getHandFromEntity(playersHand)
        // val hand = (playersHand \ "hand")
        // getCardListFromJSON(hand)
    
    def getHandFromEntity(entity: JsValue): List[Card] = 
        val hand = (entity \ "hand").get
        getCardListFromJSON(hand)

    def getCardListFromJSON(hand: JsValue): List[Card] = 

        /* GET CARDLIST */
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
        println("================================")
        // println(jsonTable.getClass())
        // println(Json.prettyPrint(jsonTable))

        val myTry = Try ((jsonTable \ "tablesTable").get)
        myTry match {
            case Success(result) => {
                println("Erfolgsfall! :)")
                println(result)
            }
            case Failure(f) => 
                println("Failure-Fall:")
                println(f)
                println(myTry.getClass())
        }
        
        // val tablesTable = (jsonTable \ "tablesTable").get
        println("================================")
        // println(Json.prettyPrint(tablesTable))
        // getHandFromEntity(tablesTable)
        List[Card]()


    def tryingATry(jsonTable: JsValue): Try[JsValue] = {
                
        Try ((jsonTable \ "tablesTable").get)
        // Try (List[Card]())
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


}