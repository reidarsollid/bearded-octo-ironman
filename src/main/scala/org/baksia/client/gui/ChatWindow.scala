package org.baksia.client.gui

import swing._
import event._
import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

case class MessageEvent(message: String) extends Event
case class ServerMessage(message: String)

object ChatWindow extends SimpleSwingApplication {
  val actorSystem = ActorSystem("ClientSystem")
  val clientHandler = actorSystem.actorOf(Props[ClientHandler])
  val connector = actorSystem.actorOf(Props(classOf[ClientConnector], clientHandler))
  
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

    listenTo(sendButton)
    reactions += {
      case ButtonClicked(sendButton) =>
        `clientHandler` ! ServerMessage(messageField.text)
        messagesArea.append("\n" + messageField.text)
      case MessageEvent(message) =>
        messagesArea.append(message)
    }
  }
}
