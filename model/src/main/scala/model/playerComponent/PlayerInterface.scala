package model.playerComponent

import model.gameComponent.Card

trait PlayerInterface {

  def showName: String
  def showHand: String
  override def toString: String

  def getCard(n:Int): (Card, Player)  // TODO: check if we should return the player with the card, it might not be needed
  def checkNumOfCards: Int

  def giveCards(newCards: List[Card]): Player

  def hasWon: Boolean



}
