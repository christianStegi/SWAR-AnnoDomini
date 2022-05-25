package DAO

trait DAOInterface {
  def save(game:String): Unit
  def load(): String
}
