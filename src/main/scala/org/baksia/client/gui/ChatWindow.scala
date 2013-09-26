package org.baksia.client.gui

import swing._
import event._

object ChatWindow extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "Scakka"
    contents = newField		
  }

  def newField = new TextField {
    text = "0"
    columns = 5
    horizontalAlignment = Alignment.Right
  }
}
