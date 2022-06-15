import akka.actor.FSM.Failure
import akka.actor.Status.Success
// Worksheet for general tests in the persistencen component

println("starting worksheet")

val future = Future(loadTime("www.spiegel.de"))

future.onComplete(
  case Success(time) => println(time)
  case Failure(e) => println("Failed to connect")
)

val test = 1