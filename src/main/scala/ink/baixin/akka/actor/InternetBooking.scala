package ink.baixin.akka.actor

import akka.actor.{Actor, ActorLogging, ActorRef}

class InternetBooking(bookingActor: ActorRef)
  extends Actor with ActorLogging {

  import BookingSystem._

  override def receive: Receive = {
    case (name: String, date: String) =>
      bookingActor ! InternetTicket(name, date)
    case _ => log.info("no result")
  }
}
