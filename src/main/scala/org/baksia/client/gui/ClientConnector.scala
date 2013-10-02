package org.baksia.client.gui

import akka.actor.Actor
import akka.io.Tcp
import akka.io.IO
import java.net.InetSocketAddress
import akka.actor.ActorRef
import akka.util.ByteString
import scala.swing.Publisher

class ClientConnector(handler: ActorRef) extends Actor {
  import Tcp._
  import context.system

  val endpoint = new InetSocketAddress("localhost", 1111)
  IO(Tcp) ! Connect(endpoint)

  def receive = {
    case Connected(remote, local) =>
      println(s"Client connected to remote ${remote}")      
      val connection = sender
      connection ! Register(handler)
      context become {  
        case ServerMessage(message) =>
          println(s"Connector Sending message ${message} to ${connection.path}")
          connection ! Write(ByteString(message))
      }
    case CommandFailed =>
      handler ! "failed"
      context stop self
      
      
  }
}