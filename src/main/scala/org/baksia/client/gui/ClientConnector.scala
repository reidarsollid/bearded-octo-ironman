package org.baksia.client.gui

import akka.actor.Actor
import akka.io.Tcp
import akka.io.IO
import java.net.InetSocketAddress
import akka.actor.ActorRef

class ClientConnector(handler: ActorRef) extends Actor {
  import Tcp._
  import context.system

  val endpoint = new InetSocketAddress("localhost", 1111)
  IO(Tcp) ! Bind(self, endpoint)

  def receive = {
    case c @ Connected =>
      sender ! Register(handler)
    case CommandFailed =>
      context stop self	
  }
}