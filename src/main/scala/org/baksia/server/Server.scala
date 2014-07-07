package org.baksia.server

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.io.{IO, Tcp}

case class NewClientSet(client: Set[ActorRef])

class Server extends Actor {

  import akka.io.Tcp._
  import context.system

  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 1111))
  var clientSet = Set.empty[ActorRef]
  var handlerSet = Set.empty[ActorRef]

  def receive = {
    case b@Bound(localAddress) =>
      println(s"local address is bound $localAddress")

    case CommandFailed(_: Bind) => context stop self

    case Connected(remote, local) =>
      println(s"Connected Remote : $remote and local : $local")
      val handler = context.actorOf(Props[ServerHandler])
      val connection = sender()
      clientSet += connection
      handlerSet += handler
      handlerSet.foreach(h => h ! NewClientSet(clientSet))
      connection ! Register(handler)
  }
}

object ServerMain extends App {
  ActorSystem("ServerScakkaSystem").actorOf(Props[Server], "Server")
}

