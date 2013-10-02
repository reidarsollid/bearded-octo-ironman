package org.baksia.client.gui

import akka.actor.Actor
import scala.swing.Publisher
import akka.io.Tcp
import akka.util.ByteString

class ClientHandler extends Actor with Publisher {

  import Tcp._
  import context.system
  val connection = sender
  def receive = {
    case Register => println("Handler registered")
    case Received(data) =>
      println(s"Received ${data}")
      publish(MessageEvent(data.utf8String))
    case ServerMessage(message) =>
      println(s"Sending message ${message} to sender ${sender}")
      connection ! Write(ByteString(message))
  }
}