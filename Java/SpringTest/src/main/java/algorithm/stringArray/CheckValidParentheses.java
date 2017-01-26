package algorithm.stringArray;

import java.util.HashMap;
import java.util.Stack;

/*
 Given a string containing just the characters '(', ')', '', '', '[' and ']', determine if the
 input string is valid. The brackets must close in the correct order, "()" and "()[]" are all
 valid but "(]" and "([)]" are not.
 */
public class CheckValidParentheses {
	public boolean isValid(String s) {
		HashMap<Character, Character> map = new HashMap<Character, Character>();
		map.put('(', ')');
		map.put('[', ']');
		map.put('{', '}');

		Stack<Character> stack = new Stack<Character>();

		for (int i = 0; i < s.length(); i++) {
			char curr = s.charAt(i);

			// Keys are open parentheses
			if (map.keySet().contains(curr)) {
				stack.push(curr);

				// values are close parentheses
			} else if (map.values().contains(curr)) {

				if (!stack.empty() && map.get(stack.peek()) == curr) {
					stack.pop();
				} else {
					return false;
				}
			}
		}
		return stack.empty();
	}

	public int lvp(String s) {
		if (s == null || s.length() == 0)
			return 0;

		HashMap<Character, Character> map = new HashMap<Character, Character>();
		map.put('(', ')');
		map.put('[', ']');
		map.put('{', '}');

		int validCount = 0;

		Stack<Character> stack = new Stack<Character>();
		for (int i = 0; i < s.length(); i++) {
			char curr = s.charAt(i);

			// Keys are open parentheses
			if (map.containsKey(curr)) {
				stack.push(curr);
				// values are close parentheses
			} else {
				System.out.println("i = " + i + ", curr = " + curr + ", stack = " + stack);
				if (!stack.empty() && map.get(stack.peek()) == curr) {
					validCount += 2;
					stack.pop();
				} else {
					break;
				}
			}
		}
		System.out.println(validCount);
		return validCount;
	}

	public int longestValidParentheses(String s) {
		int max = 0;
		for (int i = 0; i < s.length(); i++) {
			max = Math.max(max, lvp(s.substring(i)));
		}
		return max;
	}

	public static void main(String[] args) {
		CheckValidParentheses ob = new CheckValidParentheses();
		System.out.println(ob.longestValidParentheses("()(()"));
	}
}
