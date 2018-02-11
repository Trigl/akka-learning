package ink.baixin.akka.actor

import akka.actor.{Actor, ActorLogging, ActorRef}

class WindowBooking(bookingActor: ActorRef)
  extends Actor with ActorLogging {

  import BookingSystem._

  override def receive: Receive = {
    case (name: String, date: String) => bookingActor ! WindowTicket(name, date)
  }
}
