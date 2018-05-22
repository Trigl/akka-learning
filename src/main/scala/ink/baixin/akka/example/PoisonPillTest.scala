package ink.baixin.akka.example

import akka.actor.{Actor, ActorSystem, PoisonPill, Props}

object PoisonPillTest extends App {
  val system = ActorSystem("PoisonPillTest")
  val actor = system.actorOf(Props[TestActor], name = "test")

  // a simple message
  actor ! "before PoisonPill"

  // the PoisonPill
  actor ! PoisonPill

  // these messages will not be processed
  actor ! "after PoisonPill"
  actor ! "hello?!"

  system.terminate
}

class TestActor extends Actor {
  def receive = {
    case s: String => println(s"Message Received: $s")
    case _ => println("TestActor got an unknown message")
  }

  override def postStop(): Unit = {
    println("TestActor::postStop called")
  }
}
