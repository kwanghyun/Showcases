package algorithm.recursion;

import java.util.ArrayList;
import java.util.List;

/*
 * Implement an algorithm to print all valid (e.g., properly opened and closed) combinations of n-pairs of parentheses.
 * EXAMPLE:
 * input: 3 (e.g., 3 pairs of parentheses)output: ()()(), ()(()), (())(), ((()))
 * 
 * Note : 
 * 
 */
public class PrintValidParan {

	public void printValidParen(char[] chs, int open, int close, int idx) {
		if (open == 0 && close == 0) {
			System.out.println(chs);
			return;
		}

		if (open > close)
			return;

		if (open >= 0 && close >= 0) {
			chs[idx] = '[';
			printValidParen(chs, open - 1, close, idx + 1);
			chs[idx] = ']';
			printValidParen(chs, open, close - 1, idx + 1);
		}
	}

	List<String> strList = new ArrayList<String>();

	public void paren(int open, int close, char chs[], int index) {

		if (open > 3 || close > 3)
			return;
		if (close > open)
			return;

		if (open == 3 && close == 3) {
			strList.add(new String(chs));
			return;
		}
		chs[index] = '(';
		paren(open + 1, close, chs, index + 1);

		chs[index] = ')';
		paren(open, close + 1, chs, index + 1);

	}

	public static void main(String args[]) {
		char[] charlist = new char[6];
		PrintValidParan cp = new PrintValidParan();

		cp.paren(0, 0, charlist, 0);
		int count1 = 0;
		System.out.println("--------------------@paren()@-------------------");
		for (String s : cp.strList) {
			count1++;
			System.out.println(count1 + "::" + s);
		}

		char[] chs = new char[6];
		System.out.println("-------------------@Print Valid Paran @-------------------");
		cp.printValidParen(chs, 3, 3, 0);
	}
}
