import module.deckModule.Deckgenerator

case class Card(text: String, year: Int) {
  def showYear:String = year.toString
  def reveal:String = "year: " + showYear + " " +  this.toString

  override def toString: String = "[" + text + "]\n"
}

val card = Card("text", 42)
card.toString
card.text
card.year
card.showYear
card.reveal



val deckGen = new Deckgenerator

val deck = deckGen.createRandomDeck()

def sum(x:Int, y:Int):Int = x + y

def curriedSum: Int => Int => Int = (sum _).curried

sum(3, 4)

curriedSum(3) (sum(1, 5))


//val player = Player("Player 1", deck.head)

//case class Table(players:List[Player], deck:Deck, table:List[Card]);
