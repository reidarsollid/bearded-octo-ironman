package org.baksia.server

import akka.actor.Actor
import akka.io.Tcp
import akka.util.ByteString
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.io.IO
import java.net.InetSocketAddress


class Server extends Actor {
  import Tcp._
  import context.system
  
  IO(Tcp)!  Bind(self, new InetSocketAddress("localhost", 1111))
  
  def receive = {
    case b @ Bound(localaddress) =>
      println(s"local address is ${localaddress}")
    
    case CommandFailed(_: Bind) => context stop self

    case Connected(remote, local) =>
      println(s"Connected Remote : ${remote} and local : ${local}" )
      val handler = context.actorOf(Props[ServerHandler])
      val connection = sender
      connection ! Register(handler)
      //Register server handler
      //Add to list of receivers      
  }

  def sendToAll(data: ByteString, sender: ActorRef) = {
    //Send to all except sender
  }
}

object ServerMain extends App {
  ActorSystem("ServerScakkaSystem").actorOf(Props[Server])
}

