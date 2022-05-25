package DAO.SlickImpl

import slick.jdbc.MySQLProfile.api._

class PlayerTable extends Table[(Int, String)]{

  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")

  def * = (id, name)
}
