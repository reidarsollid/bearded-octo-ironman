package org.baksia.server

import akka.actor.Actor
import akka.io.Tcp
import akka.util.ByteString
import akka.actor.ActorRef

class Server extends Actor {
  import Tcp._
  import context.system
  
	def receive = {
	  case Connected(remote, local) =>
	    //Register server handler
	    //Add to list of receivers
	    println("Connected")
	  case Received(data) =>
	    sendToAll(data, sender)
	}
  
  def sendToAll(data: ByteString, sender: ActorRef) = {
	  //Send to all except sender
  }
}