package ink.baixin.akka.example

import akka.actor.{Actor, ActorSystem, Props}

object LifecycleDemo extends App {
  val system = ActorSystem("LifecycleDemo")
  val kenny = system.actorOf(Props[Kenny], name = "Kenny")

  println("sending kenny a simple String message")
  kenny ! "hello"
  Thread.sleep(1000)

  println("make kenny restart")
  kenny ! ForceRestart
  Thread.sleep(1000)

  println("stopping kenny")
  system.stop(kenny)

  println("shutting down system")
  system.terminate
}

case object ForceRestart

class Kenny extends Actor {
  println("entered the Kenny constructor")
  override def preStart() { println("Kenny: preStart") }
  override def postStop() { println("Kenny: postStop") }

  override def preRestart(reason: Throwable, message: Option[Any]) {
    println("Kenny: preRestart")
    println(s" MESSAGE: ${message.getOrElse("")}")
    println(s" REASON: ${reason.getMessage}")
    super.preRestart(reason, message)
  }

  override def postRestart(reason: Throwable) {
    println("Kenny: postRestart")
    println(s" REASON: ${reason.getMessage}")
    super.postRestart(reason)
  }

  override def receive: Receive = {
    case ForceRestart => throw new Exception("Boom!")
    case _ => println("Kenny received a message")
  }
}