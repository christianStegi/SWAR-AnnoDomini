import model.gameComponent.{Card, Deck, Deckgenerator, Player, Table}

import java.io.{File, FileWriter}


val dg = new Deckgenerator
var deck = dg.createRandomDeck(10)

val player1 = Player("Johnny", deck.drawFromDeck(2)._1)
deck = deck.drawFromDeck(2)._2
val player2 = Player("Kaycee", deck.drawFromDeck(2)._1)
deck = deck.drawFromDeck(2)._2

val table1 = Table(List(player2,player1), deck.drawFromDeck(1)._1, deck.drawFromDeck(1)._2)

def cardToXML(c:Card): scala.xml.Node = <card><year>{c.year}</year><text>{c.text}</text></card>

def cardFromXML(xml: scala.xml.Node): Card = {
  val year = (xml \ "year").text.toInt
  val text = (xml \ "text").text
  Card(text, year)
}

def cardListToXML(l:List[Card]): scala.xml.NodeSeq = {
  <hand>{ for (c <- l) yield cardToXML(c) }</hand>
}

def cardListFromXML(xml: scala.xml.NodeSeq): List[Card] = {
  val x = (xml \ "hand")
  val list = for (el <- (x \ "card")) yield el
  val hand = for (el <- list) yield cardFromXML(el)
  hand.toList
}

def playerToXML(player: Player): scala.xml.NodeSeq ={
  <player>
    <name>{player.name}</name>
    {cardListToXML(player.hand)}
  </player>
}

def playerListToXML(pl: List[Player]): scala.xml.NodeSeq ={
  <players>{for(player <- pl) yield playerToXML(player)}</players>
}

def playerFromXML(xml: scala.xml.NodeSeq):Player = {
  val name = (xml \ "name").text
  val cards = {
    cardListFromXML(xml)
  }
  Player(name, cards)
}

def playerListFromXML(xml: scala.xml.NodeSeq): List[Player] ={
  val relevantXML = (xml \ "players")
  val players = for (player <- (relevantXML \ "player")) yield playerFromXML(player)
  players.toList
}

def deckToXML(d:Deck): scala.xml.NodeSeq ={
  <deck>{cardListToXML(d.cards)}</deck>
}
def deckFromXML(xml: scala.xml.NodeSeq) ={
  val relevantXML = (xml \ "deck")
  val deck = cardListFromXML(relevantXML)
  Deck(deck)
}

def tableToXML(t:Table): scala.xml.Elem = <table>{playerListToXML(t.players)}{cardListToXML(t.cardsOnTable)}{deckToXML(t.deck)}<punishmentCards>{t.numberOfPunishmentCards}</punishmentCards></table>

def tableFromXML(xml: scala.xml.Elem): Table ={
  val players = playerListFromXML(xml)
  val cards = cardListFromXML(xml)
  val deck = deckFromXML(xml)
  val punCards = (xml \ "punishmentCards").text.toInt
  Table(players, cards, deck, punCards)
}

var cx = cardToXML(deck.deckHeadAsCard)
var card = cardFromXML(cx)
var px = playerToXML(player1)

var x = (px \ "hand")
var list = for (el <- (x \ "card")) yield ((el \ "year").text, (el \ "text").text)
val hand = for (el <- list) yield Card(el._2, el._1.toInt)

var player3 = playerFromXML(px)


val tx = tableToXML(table1)
var table2 = tableFromXML(tx)

val fileWriter = new FileWriter(new File("hello.txt"))
fileWriter.write("hello there")
fileWriter.close()

scala.xml.XML.save("anoDomini.xml", tx)