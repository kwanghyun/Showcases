package algorithm.etc;

import java.util.Stack;

/*
 * Evaluate the value of an arithmetic expression in Reverse Polish
 * Notation. Valid operators are +, -, *, /. Each operand may be an integer
 * or another expression. For example:
 * 
 * ["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9 
 * ["4", "13", "5", "/","+"] -> (4 + (13 / 5)) -> 6
 * 
 * This problem can be solved by using a stack. We can loop through each
 * element in the given array. When it is a number, push it to the stack.
 * When it is an operator, pop two numbers from the stack, do the
 * calculation, and push back the result.
 */
public class EvaluateReversePolishNotation {

	public static int evalRPN(String[] tokens) {
		int returnValue = 0;
		String operators = "+-*/";

		Stack<String> stack = new Stack<String>();

		for (String t : tokens) {
			if (!operators.contains(t)) { // push to stack if it is a number
				stack.push(t);
			} else {// pop numbers from stack if it is an operator
				int a = Integer.valueOf(stack.pop());
				int b = Integer.valueOf(stack.pop());
				switch (t) {
				case "+":
					stack.push(String.valueOf(a + b));
					break;
				case "-":
					stack.push(String.valueOf(b - a));
					break;
				case "*":
					stack.push(String.valueOf(a * b));
					break;
				case "/":
					stack.push(String.valueOf(b / a));
					break;
				}
			}
		}

		returnValue = Integer.valueOf(stack.pop());

		return returnValue;
	}

	public static void main(String[] args) {
		String[] tokens = new String[] { "2", "1", "+", "3", "*" };
		System.out.println(evalRPN(tokens));
	}

}
