package org.baksia.server

import akka.actor.Actor
import akka.io.Tcp
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.io.IO
import java.net.InetSocketAddress

case class NewClientSet(client: Set[ActorRef])

class Server extends Actor {

  import Tcp._
  import context.system

  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 1111))
  var clientSet = Set.empty[ActorRef]
  var handlerSet = Set.empty[ActorRef]

  def receive = {
    case b@Bound(localaddress) =>
      println(s"local address is bound ${localaddress}")

    case CommandFailed(_: Bind) => context stop self

    case Connected(remote, local) =>
      println(s"Connected Remote : ${remote} and local : ${local}")
      val handler = context.actorOf(Props[ServerHandler])
      val connection = sender
      clientSet += connection
      handlerSet += handler
      handlerSet.foreach(h => h ! NewClientSet(clientSet))
      connection ! Register(handler)
  }
}

object ServerMain extends App {
  ActorSystem("ServerScakkaSystem").actorOf(Props[Server], "Server")
}

