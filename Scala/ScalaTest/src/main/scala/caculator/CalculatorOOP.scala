package main.scala.caculator

import scala.collection.mutable.Stack

/**
 * @author kwjang
 */
class CalculatorOOP {

  trait Operator {
    def operate(left: Int, right: Int): Int
  }

  object Operator {
    val operators: Map[String, Operator] =
      Map("+" -> Add, "-" -> Subsract, "*" -> Multiply, "/" -> Divide)
    def unapply(token: String): Option[Operator] =
      operators.get(token)
  }

  case object Add extends Operator {
    def operate(left: Int, right: Int): Int = left + right
    override val toString = "+"
  }
  case object Subsract extends Operator {
    def operate(left: Int, right: Int): Int = left - right
    override val toString = "+"
  }

  case object Multiply extends Operator {
    def operate(left: Int, right: Int): Int = left * right
    override val toString = "+"
  }
  case object Divide extends Operator {
    def operate(left: Int, right: Int): Int = left / right
    override val toString = "+"
  }

  object Number {
    def unapply(token: String): Option[Int] = try {
      Some(token.toInt)
    } catch {
      case _: NumberFormatException => None
    }
  }
  
  def calculate(expression: String): Int ={
    val stack = new Stack[Int]
    
    for(token <- expression.split(" ")) token match{
      case Number(num) => stack.push(num)
      case Operator(op) =>
        val right = stack.pop()
        val left = stack.pop()
        stack.push(op.operate(left, right))
      case _ => throw new IllegalArgumentException("Invalid token :: " + token)
    }
    stack.pop();
  }
}