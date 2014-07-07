package org.baksia.client.gui

import akka.actor.Actor
import scala.swing.Publisher
import akka.io.Tcp
import akka.util.ByteString

class ClientHandler(guiSender: Publisher) extends Actor {

  import Tcp._

  val connection = sender()

  def receive = {
    case Register => println("Handler registered")
    case Received(data) =>
      println(s"Received ${data.utf8String} from server")
      val message = data.utf8String
      guiSender.publish(MessageEvent(message))
    case ServerMessage(message) =>
      println(s"Sending message $message to sender $connection")
      connection ! Write(ByteString(message))
  }
}