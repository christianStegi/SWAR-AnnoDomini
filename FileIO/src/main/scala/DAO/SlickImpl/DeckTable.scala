package DAO.SlickImpl

import slick.jdbc.MySQLProfile.api._


class DeckTable extends Table[(Int, String)]{
  // TODO: Include Deckname, but this requires configurations in Deck class.
  val id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  val name = column[String]("DeckName")

  override def * = (id, name)

}
