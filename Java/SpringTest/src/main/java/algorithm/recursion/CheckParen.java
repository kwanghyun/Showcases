package algorithm.recursion;

import java.util.ArrayList;

/*
 * Implement an algorithm to print all valid (e.g., properly opened and closed) combinations of n-pairs of parentheses.
 * EXAMPLE:
 * input: 3 (e.g., 3 pairs of parentheses)output: ()()(), ()(()), (())(), ((()))
 * 
 * Note : 
 * 
 */
public class CheckParen {
	// char[] closeParen = { '}', '}', '}' };
	// char[] openParen = { '{', '{', '{' };

	public void checkParen(int open, int close, String str,
			ArrayList<String> list) {
		if (open < 0 || close < 0)
			return;

		if (open == 0 && close == 0) {
			list.add(str);
			return;
		}

		str += '{';
		System.out.println(open);
		System.out.println(close);
		System.out.println(str);
		System.out.println("---------------");
		checkParen(open - 1, close, str, list);

		str += '}';
		System.out.println(open);
		System.out.println(close);
		System.out.println(str);
		System.out.println("---------------");
		checkParen(open, close - 1, str, list);

	}

	public static void printPar(int l, int r, char[] str, int count) {
		if (l < 0 || r < l)
			return; // invalid state
		if (l == 0 && r == 0) {
			System.out.println(str); // found one, so print it
		} else {
			if (l > 0) { // try a left paren, if there are some available
				str[count] = '(';
				printPar(l - 1, r, str, count + 1);
			}
			if (r > l) { // try a right paren, if there¡¯s a matching left
				str[count] = ')';
				printPar(l, r - 1, str, count + 1);
			}
		}
	}

	public static void printPar(int count) {
		char[] str = new char[count * 2];
		printPar(count, count, str, 0);
	}

	public static void main(String args[]) {
		CheckParen cp = new CheckParen();
		ArrayList<String> list = new ArrayList<String>();
		// System.out.println(cp.openParen.length);
		cp.checkParen(2, 2, "", list);
		for (String s : list)
			System.out.println(s);

		// cp.printPar(3);
	}
}
