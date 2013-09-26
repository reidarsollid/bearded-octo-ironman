package org.baksia.client.gui

import akka.actor.Actor
import scala.swing.Publisher
import akka.io.Tcp

class ClientHandler extends Actor with Publisher {
  import Tcp._
	def receive = {
	  case Received(data) => 
	    publish(MessageEvent(data.utf8String))
	}
}