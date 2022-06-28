package model.fileIOComponent.dbComponent.slickImpl

import model.gameComponent.{Card, Deck, Deckgenerator, Player, Table}
import model.fileIOComponent.dbComponent.DaoInterface


class DaoSlickImpl extends DaoInterface{

  override def save(table: Table): Unit = println("Saved Game")

  override def load(): Table={
    val dg = new Deckgenerator
    var deck = dg.createRandomDeck(10)

    val player1 = Player("Johnny", deck.drawFromDeck(2)._1)
    deck = deck.drawFromDeck(2)._2
    val player2 = Player("Kaycee", deck.drawFromDeck(2)._1)
    deck = deck.drawFromDeck(2)._2

    val table1 = Table(List(player2,player1), deck.drawFromDeck(1)._1, deck.drawFromDeck(1)._2)
    table1
  }
}
