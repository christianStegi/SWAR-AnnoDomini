package view

import controller.Controller
import util.Observer

import scala.io.StdIn.readLine

class Tui(controller: Controller) extends Observer{

  controller.add(this)

  def processInputLine(input: String): Unit ={
    input match {
      case "q" => println("End game.")
      case "d" => controller.doubt()
      case "p" => placeCard()
      case "l" => showAllPlayers()
      case _ => wrongInput()
    }
  }
  def giveOptions(): Unit = {
    println("what will you do?" +
      "\n p = place card " +
      "\n d = doubt" +
      "\n q = quit game" +
    "\n l = look at all Players")
  }

  def placeCard(): Unit = {
    println(controller.tableToString)
    println("wich card?")
    val card = readLine().toInt

    println("you selected: " +
      controller.getCard(card).toString +
      "where should it be placed?")
    val place = readLine().toInt
    controller.placeCard(card, place)
  }

  def showAllPlayers(): Unit = {
    println(controller.showAllPlayers())
  }

  def wrongInput(): Unit = {
    println("Didn't understand your input, please type again.")
  }

  def confirmWinner(): Unit = {
    println(controller.confirmWinner)
  }
  override def update(): Unit = println(controller.tableToString)


}
