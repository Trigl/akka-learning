package ink.baixin.akka.example

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object PingPongTest extends App {
  val system = ActorSystem("PingPongSystem")
  val pong = system.actorOf(Props[Pong], name = "pong")
  val ping = system.actorOf(Props(new Ping(pong)), name = "ping")
  ping ! StartMessage
}

case object PingMessage
case object PongMessage
case object StartMessage
case object StopMessage

class Ping(pong: ActorRef) extends Actor {
  var count = 0
  def incrementAndPrint{ count += 1; println("ping") }
  def receive = {
    case StartMessage =>
      incrementAndPrint
      pong ! PingMessage
    case PongMessage =>
      if (count > 99) {
        sender ! StopMessage
        println("ping stopped")
        context.stop(self)
      } else {
        incrementAndPrint
        sender ! PingMessage
      }
    case _ => println("Ping got something unexpected.")
  }
}

class Pong extends Actor {
  def receive = {
    case PingMessage =>
      println(" pong")
      sender ! PongMessage
    case StopMessage =>
      println("pong stopped")
      context.stop(self)
    case _ => println("Pong got something unexpected.")
  }
}
