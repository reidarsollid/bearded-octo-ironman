package org.baksia.client.gui

import swing._
import event._

object ChatWindow extends SimpleSwingApplication {

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
        println(messageField.text)
        messagesArea.append("\n" + messageField.text)
    }
  }
}
