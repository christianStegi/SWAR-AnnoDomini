import model.deckModule.Deckgenerator

import scala.util.{Try, Success, Failure}

val string1:String = "1"
val string2: String = "hallo"

def toInt(s: String): Try[Int] = Try(Integer.parseInt(s.trim))
def handleInput(input:Try[Int]): Int ={
  input match {
    case Success(i) => i
    case Failure(s) => handleInput(toInt("4"))
  }
}

handleInput(toInt(string1))
handleInput(toInt(string2))

