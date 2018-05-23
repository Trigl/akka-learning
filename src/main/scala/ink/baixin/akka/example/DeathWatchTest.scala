package ink.baixin.akka.example

import akka.actor.{Actor, ActorSystem, PoisonPill, Props, Terminated}

object DeathWatchTest extends App {
  val system = ActorSystem("DeathWatchTest")

  // create the Parent that will create Kenny
  val parent = system.actorOf(Props[Parent1], name = "Parent")

  // lookup kenny, then kill it
  val kenny = system.actorSelection("/user/Parent/Kenny")
  kenny ! PoisonPill

  Thread.sleep(5000)
  println("calling system.terminate")
  system.terminate
}

class Kenny1 extends Actor {
  def receive = {
    case _ => println("Kenny received a message")
  }
}

class Parent1 extends Actor {
  // start Kenny as a child, then keep an eye on it
  val kenny = context.actorOf(Props[Kenny1], name = "Kenny")
  context.watch(kenny)

  def receive = {
    case Terminated(kenny) => println(s"OMG, they killed Kenny: $kenny")
    case _ => println("Parent received a messge")
  }
}