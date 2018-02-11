package ink.baixin.akka.actor

import akka.actor.{Actor, ActorLogging}

object BookingSystem {

  trait Ticket

  case class InternetTicket(name: String, date: String) extends Ticket

  case class PhoneTicket(name: String, date: String) extends Ticket

  case class WindowTicket(name: String, date: String) extends Ticket

}

class BookingSystem extends Actor with ActorLogging {

  import BookingSystem._

  var ticketCount = 20

  override def receive: Receive = {
    case InternetTicket(name, date) =>
      if (ticketCount > 0) {
        val order = 21 - ticketCount
        log.info(s"Dear Internet User - $name: Your ticket booking of $date was successful, you are ${order}st user!")
        ticketCount -= 1
      } else {
        log.error(s"Dear Internet User - $name: Sorry to inform you that the ticket of $date had been sold out!")
      }

    case PhoneTicket(name, date) =>
      if (ticketCount > 0) {
        val order = 21 - ticketCount
        log.info(s"Dear Phone User - $name: Your ticket booking of $date was successful, you are ${order}st user!")
        ticketCount -= 1
      } else {
        log.error(s"Dear Phone User - $name: Sorry to inform you that the ticket of $date had been sold out!")
      }

    case WindowTicket(name, date) =>
      if (ticketCount > 0) {
        val order = 21 - ticketCount
        log.info(s"Dear Window User - $name: Your ticket booking of $date was successful, you are ${order}st user!")
        ticketCount -= 1
      } else {
        log.error(s"Dear Window User - $name: Sorry to inform you that the ticket of $date had been sold out!")
      }

    case _ =>
      throw new IllegalArgumentException("Wrong parameter!")
  }
}
