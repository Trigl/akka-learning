package ink.baixin.akka.example

import akka.actor.{Actor, ActorSystem, Props}

object BecomeHulkExample extends App {
  val system = ActorSystem("BecomeHulkExample")
  val davidBanner = system.actorOf(Props[DavidBanner], name = "DavidBanner")

  davidBanner ! ActNormalMessage // init to normalState
  davidBanner ! Solution
  davidBanner ! BadGuysMakeMeAngry
  davidBanner ! Solution
  davidBanner ! ActNormalMessage
  Thread.sleep(1000)

  system.terminate
}

case object ActNormalMessage
case object Solution
case object BadGuysMakeMeAngry

class DavidBanner extends Actor {
  import context._

  def angryState: Receive = {
    case Solution =>
      println("Fight with you!")
    case ActNormalMessage =>
      println("Phew, I'm back to become David.")
      become(normalState)
  }

  def normalState: Receive = {
    case Solution =>
      println("Looking for solution to my problem ...")
    case BadGuysMakeMeAngry =>
      println("I'm getting angry ...")
      become(angryState)
  }

  def receive = {
    case BadGuysMakeMeAngry => become(angryState)
    case ActNormalMessage => become(normalState)
  }
}
