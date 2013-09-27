package org.baksia.client.gui

import akka.actor.Actor
import scala.swing.Publisher
import akka.io.Tcp
import akka.util.ByteString

class ClientHandler extends Actor with Publisher {

  import Tcp._
  import context.system

  def receive = {
    case Received(data) =>
      publish(MessageEvent(data.utf8String))
    case ServerMessage(message) =>
      println(message)
      sender ! Write(ByteString(message))
  }
}