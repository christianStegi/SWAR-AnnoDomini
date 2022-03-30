//import java.util.Scanner

import controller.Controller
import module.TableGenerator
import view.Tui

import scala.io.StdIn.readLine


def NumberOfPlayers(num: Int) : 2 | 3 | 4 ={
  num match
    case 2 => 2
    case 3 => 3
    case 4 => 4
    case _ => correctNumber(num)
}

def correctNumber(num: Int) : 2 | 3 | 4 = {
  var returnNum = 3
  if  (num <= 2)  returnNum = 2
  else if (num >= 4) returnNum = 4
  returnNum match
    case 2 => 2
    case 4 => 4
    case _ => 3
    // TODO: check if this can be simplyfied with higher functions
}

// general things to do:
// Missing functionality form SE
// reintegrade Continuous deployment with Travis (need new Travis Account) (HA 6)
// add Design Patterns (HA 7)
// add Undo and Redo (HA 8)
// add Gui (HA 9)
// Add Interfaces (HA 10 )
// do Dependency Injection (HA 11)
// Add method to save game (HA 12 - File IO)
// Add Docker (HA 13)

// TODO: add method to check numbers of Players
// TODO: add method to view all players at once
// TODO: add better Input errorhandling


object AnnoDomini {

  val controller = new Controller(TableGenerator().createTable)
  val tui = new Tui(controller)
  controller.notifyObservers()

  // val scanner = new Scanner(System.in)

  def main(args:Array[String]):Unit = 

    println("Willkommen bei AnoDomini!")
    // create scanner for player input
    var input: String = ""

    // ask for prefered deck (start with default random deck)

    // generate deck

    // ask for amount of players
    println("how many players? (2, 3 or 4")
    input = readLine
    val numPlayers: 2 | 3 | 4 = NumberOfPlayers(input.toInt)
    controller.createTestTable(numPlayers)
    // if only one player: switch into Singleplayer mode
    // not implemented yet

    while(input != "q")
    do 
      // show table + current player
      tui.giveOptions()
      input = readLine()
      println("input is: " + input)
      tui.processInputLine(input)

      // check if player has won (no more cards in players hand)
      tui.confirmWinner()


}
