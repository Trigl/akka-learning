package ink.baixin.akka

import java.time.LocalDateTime
import java.util.concurrent.{ExecutorService, Executors}

import akka.actor.{ActorRef, ActorSystem, Props}
import ink.baixin.akka.actor.{BookingSystem, InternetBooking, PhoneBooking, WindowBooking}

import scala.util.Random

object BuyTicket extends App {

  // Create "ticket-booking" actor system
  val system: ActorSystem = ActorSystem("ticket-booking")
  // Create the booking actor
  val booking: ActorRef = system.actorOf(Props[BookingSystem], "booking-actor")
  // Create the internet actor
  val internet: ActorRef = system.actorOf(Props(new InternetBooking(booking)), "internet-actor")
  // Create the phone actor
  val phone: ActorRef = system.actorOf(Props(new PhoneBooking(booking)), "phone-actor")
  // Create the window actor
  val window: ActorRef = system.actorOf(Props(new WindowBooking(booking)), "window-actor")

  println(system)
  println(booking)
  println(internet)
  println(phone)
  println(window)

  // Create thread pool
  val threadPool: ExecutorService = Executors.newFixedThreadPool(25)
  try {
    for (i <- 1 to 25) {
      threadPool.execute(int2Runnable(i))
    }
  } finally {
    threadPool.shutdown()
  }


  def int2Runnable(i: Int) = new Runnable {
    override def run(): Unit = {
      if (i % 3 == 0)
        internet ! (randomString(5), LocalDateTime.now.toString)
      if (i % 3 == 1)
        phone ! (randomString(5), LocalDateTime.now.toString)
      if (i % 3 == 2)
        window ! (randomString(5), LocalDateTime.now.toString)
    }
  }

  def randomString(length: Int) = Random.alphanumeric.take(length).mkString

}
