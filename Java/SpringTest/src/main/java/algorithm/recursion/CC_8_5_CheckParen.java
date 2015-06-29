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

	List<String> list = new ArrayList<String>();
	
	List<String> strList = new ArrayList<String>();
	
	public void paren(int open, int close, char chs[], int count) {

		if (open > 3 || close > 3)
			return;

		if (open == 3 && close == 3) {
			strList.add(new String(chs));
		} else {
			chs[count] = '(';
			paren(open + 1, close, chs, count + 1);

			chs[count] = ')';
			paren(open, close + 1, chs, count + 1);

		}
	}

	//String didn't work, when return to open 2, remaining string is "{{{"
	public void checkParen(int open, int close, String str) {

		if (open > 3 || close > 3){
			return ;
		}
		
		if (open == 3 && close == 3) {
//			System.out.println(str);
			list.add(str);
		}else{
			if(open < 3){
				str = str + '{';
				checkParen(open + 1, close, str);				
			}
			if(close < 3){
				str= str + '}' ;
				checkParen(open, close + 1, str);							
			}
		}
	}

	public static void main(String args[]) {
		char[] charlist = new char[6];
		CC_8_5_CheckParen cp = new CC_8_5_CheckParen();
		cp.paren(0, 0, charlist, 0);
		int count1 = 0;
		 for (String s : cp.strList){
			 count1++;
			 System.out.println( count1 + "::" + s);
		 }
		 
		System.out.println("---------------------------------------");		 
		 
		// System.out.println(cp.openParen.length);
		 cp.checkParen(0, 0, "");
		 int count = 0;
		 for (String s : cp.list){
			 count++;
			 System.out.println( count + "::" + s);
		 }
		// cp.printPar(3);
	}
}
