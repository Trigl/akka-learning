package ink.baixin.akka.example

import akka.actor.{Actor, ActorSystem, Kill, Props}

object KillTest extends App {
  val system = ActorSystem("KillTestSystem")
  val number5 = system.actorOf(Props[Number5], name = "Number5")
  number5 ! "hello"
  // send the kill message
  number5 ! Kill
  system.terminate
}

class Number5 extends Actor {
  def receive = {
    case _ => println("Number5 got a message")
  }
  override def preStart { println("Number5 is alive") }
  override def postStop { println("Number5::postStop called")}

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    println("Number5::preRestart called")
  }

  override def postRestart(reason: Throwable): Unit = {
    println("Number5::postRestart called")
  }
}