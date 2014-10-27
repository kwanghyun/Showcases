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
public class CC_8_5_CheckParen {
	char[] closeParen = { '}', '}', '}' };
	char[] openParen = { '{', '{', '{' };

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

	// ArrayList<String> list = new ArrayList<String>();

	public void paren(int open, int close, char chs[], int count) {

		if (open > 3 || close > 3)
			return;

		if (open == 3 && close == 3) {
			System.out.println(chs);
		} else {
			System.out.println("open : " + open + " , close : " + close);
			chs[count] = '(';
			paren(open + 1, close, chs, count + 1);

			System.out.println("open : " + open + " , close : " + close);
			// if (close < open) {
			chs[count] = ')';
			paren(open, close + 1, chs, count + 1);
			// }
		}
	}

	public static void printPar(int count) {
		char[] str = new char[count * 2];
		printPar(count, count, str, 0);
	}

	public static void main(String args[]) {
		char[] charlist = new char[6];
		CC_8_5_CheckParen cp = new CC_8_5_CheckParen();
		cp.paren(0, 0, charlist, 0);
		// ArrayList<String> list = new ArrayList<String>();
		// System.out.println(cp.openParen.length);
		// cp.checkParen(2, 2, "", cp.list);
		// for (String s : cp.list)
		// System.out.println(s);

		// cp.printPar(3);
	}
}
