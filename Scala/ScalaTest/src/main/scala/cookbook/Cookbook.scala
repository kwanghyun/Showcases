package main.scala.cookbook

/**
 * @author kwjang
 */

case class Recipe(ingredients: Map[String, Mass], directions: List[String]) {
  def shoppingList(kitchen: Map[String, Mass]): List[String] =
    for{
      (name, need) <- ingredients.toList
      have = kitchen.getOrElse(name, Grams(0))
      if have.compareTo(need) < 0
    } yield name
}

/*
 * TRAIT 
 * */
/*case class Recipe1(ingredients: Map[String, Mass1], directions: List[String]) {
  def shoppingList(kitchen: Map[String, Mass1]): List[String] =
    for{
      (name, need) <- ingredients.toList
      have = kitchen.getOrElse(name, Grams(0))
      if have < need
    } yield name
}*/
/*object Recipe {
  def apply(
    ingredients: List[String] = List.empty,
    directions: List[String] = List.empty): Recipe =
    new Recipe(ingredients, directions)
  
  def unapply(recipe: Recipe): Option[(List[String], List[String])] =
    Some((recipe.ingredients, recipe.directions))
}
*/

abstract class Mass extends Comparable[Mass]{
  def amount: Double
  def toGrams: Grams
  def compareTo(that: Mass): Int = (this.toGrams.amount - that.toGrams.amount).toInt
}
case class Grams(amount: Double) extends Mass{
  override def toGrams: Grams = this
  override def toString: String = amount + "g"
}
case class Milligrams(amount: Double) extends Mass{
  override def toGrams: Grams = Grams(amount/1000)
  override def toString: String = amount + "mg"
}
case class Killograms(amount: Double) extends Mass{
  override def toGrams: Grams = Grams(amount/1000000)
  override def toString: String = amount + "kg"
}

/*
 * TRAIT 
 * */

trait Measured{
  def amount: Double
  def symbol: String
  override def toString: String = amount + symbol
}

abstract class Mass1 extends Ordered[Mass1] with Measured{
  def toGrams: Grams1
  def compare(that: Mass1): Int = (this.toGrams.amount - that.toGrams.amount).toInt
}
case class Grams1(amount: Double) extends Mass1{
  def toGrams = this
  def symbol = "g"
}
case class Milligrams1(amount: Double) extends Mass1{
  def toGrams = Grams1(amount/1000)
  def symbol = "mg"
}
case class Killograms1(amount: Double) extends Mass1{
  def toGrams = Grams1(amount/1000000)
  def symbol = "kg"
}


object Cookbook {
  val pbj = Recipe(
    Map("peanut butter" -> new Grams(10), 
        "jelly" -> new Killograms(0.5), 
        "bread" -> new Grams(250)),
    List("put the peanut butter and jelly on the bread"))

  val baconPanacakes = Recipe(
    Map("bacan" -> new Killograms(1), "pancake" -> new Milligrams(10)),
    List("take some bacon and pu it in a pancake"))
}


