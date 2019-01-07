package com.upload

import akka.actor.{ ActorRef, ActorSystem }
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import akka.http.scaladsl.model._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives.{ as, complete, entity, post, _ }
import akka.stream.Materializer
import com.upload.utils.Configuration

object ServiceController extends App with Service {

  override implicit val system = ActorSystem()
  override implicit val executor = system.dispatcher
  override implicit val materializer = ActorMaterializer()
  override val logger = Logging(system, getClass)

  val routes: Route = servRoutes

  Http().bindAndHandle(routes, Configuration.httpInterface, Configuration.httpPort)

}
