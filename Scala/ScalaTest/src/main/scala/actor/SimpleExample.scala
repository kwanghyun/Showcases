package main.scala.actor

import akka.actor.ActorSystem
import akka.actor.Actor


/**
 * @author kwjang
 */

object SimpleExample extends App{
  class SimpleActor extends Actor{
    def receive = {
      case s: String => println("String :: " + s)
      case i: Int => println("Int :: " + i)
      case _=> println("UNKNOWN")
    }
    def foo() = {}
  }
  
  val system = ActorSystem("SimpleExample")
  val actor = system.actorOf(Propsp[SimpleActor], "FirstActor")
}