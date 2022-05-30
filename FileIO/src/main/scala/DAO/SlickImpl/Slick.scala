package DAO.SlickImpl

class Slick extends daoInterface{
  val dbUrl: String = "jdbc:mysql://" + sys.env.getOrElse("DATABASE_HOST", "localhost:3306") + "/" + sys.env.getOrElse("MYSQL_DATABASE", "cah") + "?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true"
  val dbUser: String = sys.env.getOrElse("MYSQL_USER", "root")
  val dbPassword: String = sys.env.getOrElse("MYSQL_PASSWORD", "1234")


  val db = Database.forURL(
    url = dbUrl,
    driver = "com.mysql.cj.jdbc.Driver",
    user = databaseUser,
    password = databasePassword
  )

  val questionCardsTable = TableQuery[QuestionCardsTable]
  val answerCardsTable = TableQuery[AnswerCardsTable]
  database.run(questionCardsTable.schema.createIfNotExists)
  database.run(answerCardsTable.schema.createIfNotExists)

  override def save(game: String): Unit =
    println("Saving game in MySQL")

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

  override def load(): Try[String] =


}
