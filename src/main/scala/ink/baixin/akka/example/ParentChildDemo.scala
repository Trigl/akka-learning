package ink.baixin.akka.example

import akka.actor.{Actor, ActorSystem, PoisonPill, Props}

object ParentChildDemo extends App {

  val system = ActorSystem("ParentChildTest")
  val parent = system.actorOf(Props[Parent], name = "Parent")

  // send messages to Parent to create child actors
  parent ! CreateChild("Jonathan")
  parent ! CreateChild("Jordan")
  Thread.sleep(500)

  // lookup Jonathan, then kill it
  println("Sending Jonathon a PoisonPill ...")
  val jonathon = system.actorSelection("/user/Parent/Jonathan")
  jonathon ! PoisonPill
  println("Jonathon was killed")

  Thread.sleep(5000)
  system.terminate
}

case class CreateChild(name: String)
case class Name(name: String)

class Child extends Actor {
  var name = "no name"
  override def postStop: Unit = {
    println(s"oh! They killed me ($name): ${self.path}")
  }

  override def receive: Receive = {
    case Name(name) => this.name = name
    case _ => println(s"Child $name got message")
  }
}

class Parent extends Actor {
  override def receive: Receive = {
    case CreateChild(name) =>
      //Parent creates a new Child here
    println(s"Parent about to create Child ($name) ...")
      val child = context.actorOf(Props[Child], name = name)
      child ! Name(name)
    case _ => println("Parent got some other message")
  }
}