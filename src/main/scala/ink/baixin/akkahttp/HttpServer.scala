package ink.baixin.akkahttp

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import ink.baixin.akkahttp.actor.UserRegistryActor
import ink.baixin.akkahttp.route.UserRoutes

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object HttpServer extends App with UserRoutes {

  implicit val system: ActorSystem = ActorSystem("AkkaHttpServer")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val userRegistryActor: ActorRef = system.actorOf(UserRegistryActor.props, "userRegistryActor")

  lazy val routes: Route = userRoutes

  Http().bindAndHandle(routes, "localhost", 8080)

  println(s"Server online at http://localhost:8080/")

  Await.result(system.whenTerminated, Duration.Inf)

}
