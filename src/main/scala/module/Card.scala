package module

case class Card(text: String, year: Int) {
  // TODO: get coverage to 100%
  def showYear:String = year.toString
  def reveal:String = s"year: $showYear $text"

  override def toString: String = "[" + text + "]\n"
}