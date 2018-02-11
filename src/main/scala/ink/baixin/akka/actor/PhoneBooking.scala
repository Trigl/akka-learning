package ink.baixin.akka.actor

import akka.actor.{Actor, ActorLogging, ActorRef}

class PhoneBooking(bookingActor: ActorRef)
  extends Actor with ActorLogging {

  import BookingSystem._

  override def receive: Receive = {
    case (name: String, date: String) => bookingActor ! PhoneTicket(name, date)
  }
}
