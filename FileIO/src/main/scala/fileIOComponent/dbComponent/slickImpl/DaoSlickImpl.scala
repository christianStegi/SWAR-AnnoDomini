package DAO.SlickImpl

import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global
import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

class DaoSlickImpl extends daoInterface{
  /*
  val dbUrl: String = "jdbc:mysql://" + sys.env.getOrElse("DATABASE_HOST", "localhost:3306") + "/" + sys.env.getOrElse("MYSQL_DATABASE", "cah") + "?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true"
  val dbUser: String = sys.env.getOrElse("MYSQL_USER", "root")
  val dbPassword: String = sys.env.getOrElse("MYSQL_PASSWORD", "1234")


  val db = Database.forURL(
    url = dbUrl,
    driver = "com.mysql.cj.jdbc.Driver",
    user = databaseUser,
    password = databasePassword
  )
*/
  val gameId = 1

  val cards = TableQuery[CardTable]
  val decks = TableQuery[DeckTable]
  val games = TableQuery[GameTable]
  val players = TableQuery[PlayerTable]

  override def save(game: String): Unit =
  {
    println("Saving game in MySQL")
    val db = Database.forConfig("mysqldb")
    try{
      println("attempting to save mockdata in db:")
      val setup = DBIO.seq((decks.schema ++ games.schema ++ cards.schema ++ players.schema).createIfNotExists)

      val setupFuture = db.run(setup)
      setupFuture.onComplete {
        case Success(s) => println(s"created Database")
        case Failure(fa) => println(s"failed to insert values. error: $fa")
      }
      Await.ready(setupFuture, Duration(100, MILLISECONDS))

      game





    }finally {
      db.close()

    }


    val cardsJson = Json.parse(json)
    val questCardsJson = (cardsJson \ "cardList").head
    val answerCardsJson = (cardsJson \ "cardList").last
    Try {
      database.run(questionCardsTable ++= (
        (questCardsJson \\ "card").map(s => s.toString).toSeq
        ))

      database.run(answerCardsTable ++= (
        (answerCardsJson \\ "card").map(s => s.toString).toSeq
        ))

    }
  }


  override def load: Option[String] ={
    println("loading game from mysql db")
    val db = Database.forConfig("mysqldb")
    try{


      val game = db.run(games.result).map(_.foreach{
        case (game_id, deck_id) =>
          println(game_id, deck_id)})

      val players = db.run(players.result).map(_.foreach{
        case (player_id, player_name, game_id) =>
          (player_id, player_name, game_id)})

      val cards = db.run(cards.result).map(_.foreach{
        case (card_id, card_year, card_text, card_owner) =>
          (card_id, card_year, card_text, card_owner)})

      val deck = db.run(decks.result).map(_.foreach{
        case (deck_id) =>
          deck_id})








    }finally {
      db.close()
    }

  }

  // xml reading classes:
  def cardFromXML(xml: scala.xml.Node): (text:String, year:Int) = {
    val year = (xml \ "year").text.toInt
    val text = (xml \ "text").text
    (text, year)
  }

  def cardListFromXML(xml: scala.xml.NodeSeq): List[(text:String, year:Int)] = {
    val x = (xml \ "hand")
    val list = for (el <- (x \ "card")) yield el
    val hand = for (el <- list) yield cardFromXML(el)
    hand.toList
  }



}
