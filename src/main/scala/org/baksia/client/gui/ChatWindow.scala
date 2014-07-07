package org.baksia.client.gui

import akka.actor.{ActorSystem, Props}

import scala.swing._
import scala.swing.event._

case class MessageEvent(message: String) extends Event

case class ServerMessage(message: String)

object ChatWindow extends SimpleSwingApplication {
  val actorSystem = ActorSystem("ClientSystem")
  val publisher = new MessagePublisher()
  val clientHandler = actorSystem.actorOf(Props(classOf[ClientHandler], publisher), "ClientHandler")
  val connector = actorSystem.actorOf(Props(classOf[ClientConnector], clientHandler), "clientConnector")

  def top = new MainFrame {
    title = "Scakka"

    object sendButton extends Button {
      text = "Send"
    }

    object messagesArea extends TextArea {
      text = "Conversation"
      columns = 20
      rows = 30
      editable = false
    }

    object messageField extends TextField {
      text = "Write messages here"
      columns = 20
      horizontalAlignment = Alignment.Right
    }

    contents = new FlowPanel {
      contents.append(messageField, sendButton, messagesArea)
      border = Swing.EmptyBorder(10)
    }

    listenTo(sendButton, publisher)
    reactions += {
      case ButtonClicked(sendButton) =>
        `connector` ! ServerMessage(messageField.text)
      case MessageEvent(message) =>
        println(s"ChatWindow Received message $message")
        messagesArea.append("\n" + "Server: " + message)
    }
  }
}
