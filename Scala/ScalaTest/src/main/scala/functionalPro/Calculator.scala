package main.scala.functionalPro

/**
 * @author kwjang
 */
object Calculator {

  type Operator = (Int, Int) => Int

  object Operator {
    val operators: Map[String, Operator] =
      Map(
        "+" -> { _ + _ },
        "-" -> { _ - _ },
        "*" -> { _ * _ },
        "/" -> { _ / _ })
    val tokens = operators map { _.swap }
    def unapply(token: String): Option[Operator] =
      operators.get(token)
  }

  object Number {
    def unapply(token: String): Option[Int] = try {
      Some(token.toInt)
    } catch {
      case _: NumberFormatException => None
    }
  }

  sealed trait Expression
  case class NumberExpression(value: Int) extends Expression
  case class OperationExpression(left: Expression, right: Expression, op: Operator) extends Expression

  def step(stack: List[Expression], token: String): List[Expression] = token match {
    case Number(num) => NumberExpression(num) :: stack
    case Operator(op) => stack match {
      case right :: left :: rest => OperationExpression(left, right, op) :: rest
      case _ => throw new IllegalArgumentException("not enough operands")
    }
    case _ => throw new IllegalArgumentException("invalid token" + token)
  }

  def parse(expression: String): Expression = {
   val tokens = expression.split(" ")
   val stack = tokens.foldLeft(List.empty[Expression]){step}
   stack.head
  }

  def calculate(expression: Expression): Int = expression match {
    case NumberExpression(value) => value
    case OperationExpression(left, right, op) => op(calculate(left), calculate(right))
  }

  def toInfix(expression: Expression): String = expression match {
    case NumberExpression(value) => value.toString
    case OperationExpression(left, right, op) => s"(${toInfix(left)} ${Operator.tokens(op)} ${toInfix(right)})"
  }

  def main(args: Array[String]): Unit =
    if (args.length != 1)
      throw new IllegalArgumentException("Uage: Caculator <expression>")
    else {
      val expression = parse(args(0))
      println(s"${toInfix(expression)} = ${calculate(expression)}")
    }
}