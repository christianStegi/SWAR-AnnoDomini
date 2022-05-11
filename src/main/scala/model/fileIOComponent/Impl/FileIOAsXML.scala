package model.fileIOComponent.Impl

import model.fileIOComponent.FileIOInterface
import model.gameComponent.{Card, Deck, Table}
import model.playerComponent.Player
import java.io.PrintWriter
import java.io.File

class FileIOAsXML extends FileIOInterface{

  override def save(table: Table): Unit = {
    println(tableToXML(table))
    scala.xml.XML.save("save.xml", tableToXML(table))   
  }


  def saveFromString(tableAsString: String): Unit = {
    println("\n\nsaveFromString:\n AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHHHHHHHHHHHH\n\n\n")
    val pw = new PrintWriter(new File("./savedAsString.txt"))
    pw.write(tableAsString)
    pw.close
  }



  override def load: Table = {
    val xml = scala.xml.XML.loadFile("save.xml")
    tableFromXML(xml)
    // TODO: make Errorhandeling with Option or Try
  }


    def loadFromStringFile: Table = {
    val xml = scala.xml.XML.loadFile("./savedAsString.txt")
    tableFromXML(xml)
    // TODO: make Errorhandeling with Option or Try
  }



  // entweder als scala.xml.Elem auslesen via methode aus scala.xml.XML oder Datei einfach als String lesen
  def loadAsStringForSending: String = {
    // val lines = scala.io.Source.fromFile("file.txt").mkString
    val source = scala.io.Source.fromFile("./savedAsString.txt")
    val lines = try source.mkString finally source.close()
    // val xml = scala.xml.XML.loadFile("save.xml")
    lines
    // TODO: make Errorhandeling with Option or Try
  }


  def cardToXML(c:Card): scala.xml.Node = <card><year>{c.year}</year><text>{c.text}</text></card>
  def cardListToXML(l:List[Card]): scala.xml.NodeSeq = <hand>{ for (c <- l) yield cardToXML(c) }</hand>

  def cardFromXML(xml: scala.xml.Node): Card = {
    val year = (xml \ "year").text.toInt
    val text = (xml \ "text").text
    Card(text, year)
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

  def playerListToXML(pl: List[Player]): scala.xml.NodeSeq = <players>{for(player <- pl) yield playerToXML(player)}</players>

  def playerFromXML(xml: scala.xml.NodeSeq):Player = {
    val name = (xml \ "name").text
    val cards = cardListFromXML(xml)
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

  def tableToXML(t:Table): scala.xml.Elem = <table>{playerListToXML(t.players)}{cardListToXML(t.table)}{deckToXML(t.deck)}<punishmentCards>{t.punishmentCards}</punishmentCards></table>

  def tableFromXML(xml: scala.xml.Elem): Table ={
    val players = playerListFromXML(xml)
    val cards = cardListFromXML(xml)
    val deck = deckFromXML(xml)
    val punCards = (xml \ "punishmentCards").text.toInt
    Table(players, cards, deck, punCards)
  }

}
