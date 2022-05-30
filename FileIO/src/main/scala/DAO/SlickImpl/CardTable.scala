package DAO.SlickImpl

import slick.jdbc.MySQLProfile.api._

class CardTable extends Table[(Int, Int, String, Int, Int)]{

  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def year = column[Int]("year")
  def text = column[String]("text")
  def owner = column[Int]("ownerID")
  def deck = column[Int]("")

  override def * = (id, year, text)
}
