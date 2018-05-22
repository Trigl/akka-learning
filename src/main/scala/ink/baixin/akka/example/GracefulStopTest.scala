package ink.baixin.akka.example

import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.gracefulStop

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

object GracefulStopTest extends App {
  val system = ActorSystem("GracefulStopTest")
  val testActor = system.actorOf(Props[TestActor1], name = "TestActor")

  // try to stop the actor gracefully
  try {
    val stopped: Future[Boolean] = gracefulStop(testActor, 2 seconds)
    Await.result(stopped, 3 seconds)
    println("testActor was stopped")
  } catch {
    case e: Exception => e.printStackTrace
  } finally {
    system.terminate
  }
}

class TestActor1 extends Actor {
  def receive = {
    case _ => println("TestActor got message")
  }

  override def postStop { println("TestActor: postStop") }
}