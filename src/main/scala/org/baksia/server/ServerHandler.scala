package org.baksia.server

import akka.actor.Actor
import akka.io.Tcp

class ServerHandler extends Actor {
  import Tcp._
  def receive = {
    case Received(data) => sender ! Write(data)
    case PeerClosed => context stop self
  }
}