package model.fileIOComponent.Impl

import model.fileIOComponent.FileIOInterface
import model.gameComponent.{Card, Deck, Table}
import model.playerComponent.Player

import play.api.libs.json._
import java.io.PrintWriter
import java.io.File


class FileIOAsJSON extends FileIOInterface {

    val filename: String = "./savedAsJson.json"


    override def load: Table = 
        ???
        // TODO: make Errorhandeling with Option or Try

    override def save(table: Table): Unit = {
        val pw = new PrintWriter(new File(filename))
        // pw.write(Json.stringify(tableToJson(table)))
        pw.write(Json.prettyPrint(tableToJson(table)))
        pw.close
    }

    def tableFromJSON(contentAsJSON: String) = 
        ???

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
            "player" -> player.name,
            "playersHand" -> cardListToJSON(player.hand)
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