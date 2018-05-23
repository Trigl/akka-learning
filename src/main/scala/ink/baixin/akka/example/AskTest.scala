package ink.baixin.akka.example

import akka.actor.{Actor, ActorSystem, Props}
import akka.util.Timeout
import akka.pattern.ask

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

object AskTest extends App {
  // create the system and actor
  val system = ActorSystem("AskTestSystem")
  val myActor = system.actorOf(Props[AskActor], name = "myActor")

  // (1) this is one way to "ask" another actor for information
  implicit val timeout = Timeout(5 seconds)
  val future = myActor ? AskNameMessage
  val result = Await.result(future, timeout.duration).asInstanceOf[String]
  println(result)

  // (2) a slightly different way to ask another actor for information
  val future2: Future[String] = ask(myActor, AskNameMessage).mapTo[String]
  val result2 = Await.result(future2, 1 second)
  println(result2)

  system.terminate
}

case object AskNameMessage

class AskActor extends Actor {
  def receive = {
    case AskNameMessage =>
      // response to the 'ask' request
      sender ! "Fred"
    case _ => println("that was unexpected")
  }
}
