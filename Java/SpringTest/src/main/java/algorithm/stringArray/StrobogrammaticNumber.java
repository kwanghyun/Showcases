package algorithm.stringArray;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * A strobogrammatic number is a number that looks the same when rotated 180
 * degrees (looked at upside down).
 * 
 * Write a function to determine if a number is strobogrammatic. The number
 * is represented as a string.
 * 
 * For example, the numbers '69', '88', and '818' are all strobogrammatic.
 */

public class StrobogrammaticNumber {

	public boolean isStrobogrammatic(String num) {
		Map<Character, Character> pairMap = new HashMap<>();
		pairMap.put('6', '9');
		pairMap.put('9', '6');
		pairMap.put('8', '8');
		pairMap.put('1', '1');
		pairMap.put('0', '0');

		if (num == null || num.length() == 0)
			return false;

		int mid = num.length() / 2;
		if (num.length() % 2 == 1) {
			if (num.charAt(mid) == '6' || num.charAt(mid) == '9' || !pairMap.containsKey(num.charAt(mid))) {
				return false;
			}
		}

		for (int i = 0; i <= num.length() / 2; i++) {

			char lch = num.charAt(i);
			char hch = num.charAt(num.length() - i - 1);
			System.out.println("i = " + i + ", lch = " + lch + ", last = " + (num.length() - i - 1) + ", hch = " + hch);
			if (pairMap.containsKey(lch)) {
				if (pairMap.get(lch) != hch)
					return false;
			} else
				return false;
		}

		return true;
	}

	public static void main(String[] args) {
		StrobogrammaticNumber ob = new StrobogrammaticNumber();
		System.out.println(ob.isStrobogrammatic("11"));

		char[] chs = new char[3];
		chs[0] = '1';
		String str = new String(chs);
		System.out.println(chs);
		System.out.println(str.length());
	}
}
