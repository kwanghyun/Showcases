package algorithm.etc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;

/*
 * Note :
 * Start simplely 
 * 1. Just one operator,
 * 2. all operator
 * 3. paren
 */

public class ExpressionEvaluation {

	public int calculate(String str) {
		Stack<Integer> nums = new Stack<Integer>();
		Stack<String> ops = new Stack<String>();

		StringTokenizer tokens = new StringTokenizer(str);

		boolean flag = false;

		while (tokens.hasMoreTokens()) {
			String token = tokens.nextToken();
			if(token.equalsIgnoreCase(")")){
				
			} else if (token.equalsIgnoreCase("+") || token.equalsIgnoreCase("-")) {
				ops.push(token);
			} else if (token.equalsIgnoreCase("*") || token.equalsIgnoreCase("/")) {
				ops.push(token);
				flag = true;
			} else if(isNumaric(token)){
				nums.push(Integer.parseInt(token));
			} else if (flag) {
				nums.push(operate(nums.pop(), ops.pop(), nums.pop()));
				flag = false;
			}
			System.out.println(Arrays.toString(ops.toArray()));
			System.out.println(Arrays.toString(nums.toArray()));
			System.out.println("-----token--------");
		}

		while (nums.size() > 1) {
			nums.push(operate(nums.pop(), ops.pop(), nums.pop()));
		}
		return nums.pop();
	}

	public boolean isNumaric(String str){
		char[] chars = str.toCharArray();
		for(char ch : chars){
			if(!Character.isDigit(ch))
				return false;
		}
		return true;
	}
	
	public int operate(int first, String operator, int second) {
		int num = 0;

		if (operator.equalsIgnoreCase("+"))
			num = first + second;
		else if (operator.equalsIgnoreCase("-"))
			num = first - second;
		else if (operator.equalsIgnoreCase("*"))
			num = first * second;
		else if (operator.equalsIgnoreCase("/"))
			num = first / second;
		return num;
	}

	public int eval(){
		
//		Stack<String>  
		return 0;
	}
	
	public static void main(String args[]) {
		ExpressionEvaluation ee = new ExpressionEvaluation();
		System.out.println(ee.calculate("5 + 2 * 3"));
	}
}
