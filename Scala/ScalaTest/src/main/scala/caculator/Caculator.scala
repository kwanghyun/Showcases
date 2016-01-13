package main.scala.caculator

import scala.collection.mutable.Stack

object Caculator {
  def handleOperator(token: String, stack: Stack[Int]): Boolean = token match {

    case "+" =>
      val left = stack.pop()
      val right = stack.pop()
      stack.push(left + right)
      true
    case "-" =>
      val left = stack.pop()
      val right = stack.pop()
      stack.push(left - right)
      true
    case "*" =>
      val left = stack.pop()
      val right = stack.pop()
      stack.push(left * right)
      true
    case "/" =>
      val left = stack.pop()
      val right = stack.pop()
      stack.push(left / right)
      true
    case _ => false
  }

  def handleNumber(token: String, stack: Stack[Int]): Boolean = try {
    stack.push(token.toInt)
    true
  } catch {
    case _: NumberFormatException => false
  }

  def calculate(expression: String): Int = {
    val stack = new Stack[Int]

    for (token <- expression.split(" "))
      if (!handleOperator(token, stack) && !handleNumber(token, stack))
        throw new IllegalArgumentException("invalid token" + token)
    
    stack.pop()
  }

  def main(args: Array[String]): Unit =
    if (args.length != 1) {
      throw new IllegalArgumentException("Usage: Calculator <expression>")
    } else {
      println(calculate(args(0)))
    }

}