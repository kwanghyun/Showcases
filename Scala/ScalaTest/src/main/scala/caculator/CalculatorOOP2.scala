package main.scala.caculator

import scala.collection.mutable.Stack

/**
 * @author kwjang
 */
class CalculatorOOP2 {

  sealed trait Expression
  case class NumberExpression(value: Int) extends Expression
  case class OperatorExpression(left: Expression, right: Expression, op: Operator) extends Expression

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

  def parse(expression: String): Expression = {
    val stack = new Stack[Expression]

    for (token <- expression.split(" ")) token match {
      case Number(num) => stack.push(NumberExpression(num))
      case Operator(op) =>
        val right = stack.pop()
        val left = stack.pop()
        stack.push(OperatorExpression(left, right, op))
      case _ => throw new IllegalArgumentException("Invalid token :: " + token)
    }
    stack.pop();
  }

  def calculate(expression: Expression): Int = expression match {
    case NumberExpression(value) => value
    case OperatorExpression(left, right, op) => op.operate(calculate(left), calculate(right))
  }

  def toInfix(expression: Expression): String = expression match {
    case NumberExpression(value) => value.toString
    case OperatorExpression(left, right, op) => s"(${toInfix(left)} $op ${toInfix(right)})"
  }

  def main(args: Array[String]): Unit =
    if (args.length != 1)
      throw new IllegalArgumentException("Uage: Caculator <expression>")
    else {
      val expression = parse(args(0))
      println(s"${toInfix(expression)} = ${calculate(expression)}")
    }
}