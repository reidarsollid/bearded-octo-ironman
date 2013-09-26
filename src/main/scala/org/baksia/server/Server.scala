package org.baksia.server

import akka.actor.Actor
import akka.io.Tcp

class Server extends Actor {
  import Tcp._
	def receive = {
	  case Connect => println("Connected")
	}
}