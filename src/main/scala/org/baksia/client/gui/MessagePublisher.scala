package org.baksia.client.gui

import scala.swing.Publisher

class MessagePublisher extends Publisher {
	def sendMessage(message: String) = {
	  publish(MessageEvent(message))
	}
}