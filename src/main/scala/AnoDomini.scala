//import java.util.Scanner

import controller.Controller
import module.TableGenerator
import view.Tui

import scala.io.StdIn.readLine
object AnoDomini {

  val controller = new Controller(TableGenerator().createTable)
  val tui = new Tui(controller)
  controller.notifyObservers()

  // val scanner = new Scanner(System.in)

  def main(args:Array[String]):Unit = {

    println("Willkommen bei AnoDomini!")
    // create scanner for player input
    var input: String = ""

    // ask for prefered deck (start with default random deck)


    // generate deck

    // ask for amount of players
    println("how many players?")
    input = readLine()
    controller.createTestTable(input.toInt)
    // if only one player: switch into Singleplayer mode
    // not implemented yet

    do{
      // show table + current player
      tui.giveOptions()
      input = readLine()
      println("input is: " + input)
      tui.processInputLine(input)

      // check if player has won (no more cards in players hand)
      tui.confirmWinner()
    }while(input != "q")
  }



}
