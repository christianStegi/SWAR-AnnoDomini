package DAO

trait DAOInterface {
  def save(game:String): Unit
  def load(): Option[String]
}
