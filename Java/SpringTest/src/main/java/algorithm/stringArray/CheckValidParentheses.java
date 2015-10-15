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
			
			//Keys are open parentheses
			if (map.keySet().contains(curr)) {
				stack.push(curr);
				
				//values are close parentheses
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
}
