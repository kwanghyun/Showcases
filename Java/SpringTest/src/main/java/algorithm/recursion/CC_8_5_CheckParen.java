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
public class CC_8_5_CheckParen {

	List<String> strList = new ArrayList<String>();

	public void paren(int open, int close, char chs[], int index) {

		if (open > 3 || close > 3)
			return;

		if (open == 3 && close == 3) {
			strList.add(new String(chs));
		} else {
			chs[index] = '(';
			paren(open + 1, close, chs, index + 1);

			chs[index] = ')';
			paren(open, close + 1, chs, index + 1);

		}
	}

	public void parenValid(int open, int close, char chs[], int index) {
		if (open > 3 || close > 3)
			return;

		if (open == 3 && close == 3) {
			strList.add(new String(chs));
		} else {

			chs[index] = '(';
			parenValid(open + 1, close, chs, index + 1);
			if (close < open) {
				chs[index] = ')';
				parenValid(open, close + 1, chs, index + 1);
			}
		}
	}

	List<String> list = new ArrayList<String>();

	// String didn't work, when return to open 2, remaining string is "{{{"
	// To avoid this, need undo mechanism.
	public boolean checkParen(int open, int close, String str) {

		if (open > 3 || close > 3) {
			return false;
		}

		if (open == 3 && close == 3) {
			list.add(str);
		} else {

			str = str + '{';
			if (!checkParen(open + 1, close, str)) {
				str = str.substring(0, str.length() - 1);
			}

			str = str + '}';
			if (!checkParen(open, close + 1, str)) {
				str = str.substring(0, str.length() - 1);
			}
		}
		return false;
	}
	
	public boolean checkParenValid(int open, int close, String str) {

		if (open > 3 || close > 3) {
			return false;
		}

		if (open == 3 && close == 3) {
			list.add(str);
		} else {

			str = str + '{';
			if (!checkParen(open + 1, close, str)) {
				str = str.substring(0, str.length() - 1);
				open --;
			}
			
			if(close < open){
				str = str + '}';
				if (!checkParen(open, close + 1, str)) {
					str = str.substring(0, str.length() - 1);
					close--;
				}				
			}
		}
		return false;
	}


	public static void main(String args[]) {
		char[] charlist = new char[6];
		char[] charlist2 = new char[6];
		CC_8_5_CheckParen cp = new CC_8_5_CheckParen();

		cp.paren(0, 0, charlist, 0);
		int count1 = 0;
		System.out.println("--------------------@paren()@-------------------");
		for (String s : cp.strList) {
			count1++;
			System.out.println(count1 + "::" + s);
		}
		cp.strList.clear();
		count1 = 0;
		System.out.println("--------------------@parenValid()@-------------------");
		cp.parenValid(0, 0, charlist2, 0);
		for (String s : cp.strList) {
			count1++;
			System.out.println(count1 + "::" + s);
		}
		System.out.println("-------------------@CheckParan() - undo @-------------------");
		cp.checkParen(0, 0, "");
		int count = 0;
		for (String s : cp.list) {
			count++;
			System.out.println(count + "::" + s);
		}
		System.out.println("-------------------@CheckParanValid() - undo @-------------------");
		cp.list.clear();
		count = 0;
		cp.checkParenValid(0, 0, "");
		for (String s : cp.list) {
			count++;
			System.out.println(count + "::" + s);
		}

	}
}
