package algorithm.stackNQueue;

import java.util.*;

/*
 * Given a non-negative integer num represented as a string, remove k digits
 * from the number so that the new number is the smallest possible.
 * 
 * Note: The length of num is less than 10002 and will be ≥ k. The given num
 * does not contain any leading zero.
 * 
 * Example 1:
 * Input: num = "1432219", k = 3
 * Output: "1219"
 * Explanation: Remove the three digits 4, 3, and 2 to form the new number
 * 1219 which is the smallest.
 * 
 * Example 2:
 * Input: num = "10200", k = 1
 * Output: "200"
 * Explanation: Remove the leading 1 and the number is 200. Note that the
 * output must not contain leading zeroes.
 * 
 * Example 3:
 * Input: num = "10", k = 2
 * Output: "0"
 * Explanation: Remove all the digits from the number and it is left with
 * nothing which is 0.
 */
public class RemoveKDigits {
	public String removeKdigits(String num, int k) {

		StringBuilder result = new StringBuilder();
		Stack<Integer> stack = new Stack<>();
		int count = 0;

		for (int i = 0; i < num.length(); i++) {
			int n = num.charAt(i) - '0';
			while (!stack.isEmpty() && count < k && stack.peek() > n) {
				stack.pop();
				count++;
			}

			if (!stack.isEmpty() || n != 0)
				stack.push(n);
		}

		while (!stack.isEmpty()) {
			result.insert(0, stack.pop());
		}

		if (count < k) {
			result.setLength(num.length() - k);
		}

		return result.length() == 0 ? "0" : result.toString();
	}

	public static void main(String[] args) {
		RemoveKDigits ob = new RemoveKDigits();
		// String num = "1432219";
		// String num = "10200";
		// String num = "112";
		String num = "1234567890";
		// int k = 3;
		// int k = 1;
		int k = 9;
		System.out.println(ob.removeKdigits(num, k));
	}
}
