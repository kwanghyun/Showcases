package algorithm.etc;

import java.util.Stack;

/*
 * Evaluate the value of an arithmetic expression in Reverse Polish Notation.

 Valid operators are +, -, *, /. Each operand may be an integer or another expression.

 Some examples:
 ["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9
 ["4", "13", "5", "/", "+"] -> (4 + (13 / 5)) -> 6

 */
public class EvaluationReversePolish {

	public int eval(String[] tokens) {

		String operators = "+-/*";
		Stack<Integer> nums = new Stack<Integer>();

		for (String token : tokens) {

			if (!operators.contains(token)) {
				nums.push(Integer.parseInt(token));
			} else {
				int val1 = nums.pop();
				int val2 = nums.pop();
				if (token == "+")
					nums.push(val2 + val1);
				else if (token == "-")
					nums.push(val2 - val1);
				else if (token == "*")
					nums.push(val2 * val1);
				else if (token == "/")
					nums.push(val2 / val1);
			}
		}
		return nums.pop();
	}
	
	public static void main(String args[]){
		EvaluationReversePolish erp = new EvaluationReversePolish();
		String[] arr1 = {"2", "1", "+", "3", "*"};
		String[] arr2 = {"4", "13", "5", "/", "+"};
		System.out.println(erp.eval(arr1));
		System.out.println(erp.eval(arr2));
	}
}
