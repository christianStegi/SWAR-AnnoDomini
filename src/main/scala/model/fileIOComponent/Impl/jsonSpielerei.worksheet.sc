import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Spielklasse():

    val content = "asdfasdf"
    val value = 105


val klasse = Spielklasse()    
println(klasse.content)
println(Json.toJson (klasse.content))
klasse.content.getClass

val liste = List("Fiver", "Bigwig")
val asJson1 = Json.toJson(List("Fiver", "Bigwig"))

val asJson2 = Json.toJson("Fiver")

// implicit val spielKlassenFormat = Json.format[Spielklasse]
// val asJson3 = Json.reads[Spielklasse].as[Spielklasse]



