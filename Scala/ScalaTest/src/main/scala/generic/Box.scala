package main.scala.generic

import java.lang.reflect.Member

/**
 * @author kwjang
 */
case class Box(var contents: Any)

case class GenericBox[T](var contents: T)

case class PrivateGenericBox[T](private var contents: T)
object PrivateGenericBox {
  def put[T](contents: T, box: PrivateGenericBox[T]): Unit = box.contents = contents
  def get[T](box: PrivateGenericBox[T]): T = box.contents

}

class Dog{ override val toString = "Dog"}
class Puppy extends Dog {override val toString = "Puppy"}

case class House[T] (private var members: T)
object House {
  def putPuppy [T >: Puppy](house: House[T]): Unit =house.members = new Puppy
  def getDog[T <: Dog](house: House[T]): T = house.members
} 