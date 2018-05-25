package ink.baixin.akka.example

import akka.actor.{Actor, ActorSystem, Props}

import scala.concurrent.Future
import akka.pattern._

case object SpecialMessage
case class Event(i: Int)
case class Error(reason: Throwable)

class PipetoActor extends Actor {
  import context.dispatcher

  def receive = {
    case SpecialMessage =>
      val f = Future {
        Thread.sleep(100)
        50
      }
      f.map { result =>
        println(result)
        Event(result)
      }.recover {
        case ex =>
          println(ex)
          Error(ex)
      }.pipeTo(sender())

    case Event(i) =>
      println(s"Successfully got message: $i")
    case Error(reason) =>
      println(s"Failed! Reason: $reason")
  }
}

object PipetoTest extends App {
  val system = ActorSystem("PipetoTestSystem")
  val myActor = system.actorOf(Props[PipetoActor], "PipetoActor")
  myActor ! SpecialMessage
  Thread.sleep(1000)
  system.terminate
}