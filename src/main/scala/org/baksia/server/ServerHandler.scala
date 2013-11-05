package org.baksia.server

import akka.actor.Actor
import akka.io.Tcp
import akka.util.ByteString
import akka.actor.ActorRef

class ServerHandler extends Actor {
  var clientSet = Set.empty[ActorRef]

  import Tcp._

  def receive = {
    case Received(data) =>
      val message = data.utf8String
      println(s"Received data ${message}")
      sendToAll(data)
    case PeerClosed =>
      println("Peer closed")
      context stop self
    case NewClientSet(client) =>
      clientSet = client
      println(clientSet)
  }

  def sendToAll(data: ByteString) = {
    clientSet.filter(_ != sender).foreach(c => c ! Write(data))
  }
}