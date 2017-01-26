package algorithm.dynamic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import algorithm.Utils;

/*
 * A message containing letters from A-Z is being encoded to numbers using
 * the following mapping:
 *
 * 	'A' -> 1
 * 	'B' -> 2
 * 	...
 * 	'Z' -> 26
 * 		
 * Given an encoded message containing digits, determine the total number of
 * ways to decode it.
 */

public class DecodeWays {

	public int decodeI(String str) {
		if (str.length() == 0) {
			return 1;
		}

		int count = 0;

		if (str.length() > 0 && Integer.parseInt(str.substring(0, 1)) < 26) {
			count += decodeI(str.substring(1));
		}

		if (str.length() > 1 && Integer.parseInt(str.substring(0, 2)) < 26) {
			count += decodeI(str.substring(2));
		}
		return count;
	}

	HashMap<String, Integer> dpMap = new HashMap<>();

	public int decodeI_dp(String str) {
		if (str.length() == 0) {
			return 1;
		}

		if (dpMap.containsKey(str)) {
			System.out.println("HIT THE MEM =" + str + ", val = " + dpMap.get(str));
			return dpMap.get(str);
		}

		int count = 0;

		if (str.length() > 0 && Integer.parseInt(str.substring(0, 1)) < 26) {
			count += decodeI_dp(str.substring(1));
		}

		if (str.length() > 1 && Integer.parseInt(str.substring(0, 2)) < 26) {
			count += decodeI_dp(str.substring(2));
		}

		dpMap.put(str, count);

		return count;
	}

	public void decode(String str, List<Character> list) {
		if (str.length() == 0) {
			System.out.println(list);
			return;
		}

		for (int i = 1; i <= 2; i++) {
			if (str.length() < i)
				return;

			int num = Integer.parseInt(str.substring(0, i));
			if (num > 26)
				return;

			char c = (char) (num + 'a' - 1);
			list.add(c);
			decode(str.substring(i), list);
			list.remove(list.size() - 1);
		}
	}

	Map<String, Integer> map = new HashMap<>();

	public int decodeDP(String str) {
		int count = 0;
		if (str.length() == 0) {
			return 1;
		}

		if (map.containsKey(str)) {
			System.out.println("GOTCHA" + " => " + str);
			return map.get(str);
		}

		for (int i = 1; i <= 2; i++) {
			if (str.length() < i)
				break;

			int num = Integer.parseInt(str.substring(0, i));
			if (num > 26)
				break;
			count += decodeDP(str.substring(i));
			map.put(str, count);
		}
		return count;
	}

	public int decodeDPCS(String str, int callstack) {
		int count = 0;
		if (str.length() == 0) {
			return 1;
		}

		if (map.containsKey(str)) {
			Utils.printCS(callstack, " GOTCHA => " + str);
			return map.get(str);
		}

		for (int i = 1; i <= 2; i++) {
			if (str.length() < i)
				break;

			int num = Integer.parseInt(str.substring(0, i));
			if (num > 26)
				break;
			Utils.printCS(callstack, " [+] i =" + i + ", str = " + str + ", num = " + num);

			count += decodeDPCS(str.substring(i), callstack + 1);
			map.put(str, count);
			Utils.printCS(callstack, " [-] i =" + i + ", map = " + map);
		}
		Utils.printCsEOL(callstack);
		return count;
	}

	public int numDecodings(String s) {
		if (s == null || s.length() == 0 || s.charAt(0) == '0')
			return 0;
		if (s.length() == 1)
			return 1;

		int[] dp = new int[s.length()];
		dp[0] = 1;
		if (Integer.parseInt(s.substring(0, 2)) > 26) {
			if (s.charAt(1) != '0') {
				dp[1] = 1;
			} else {
				dp[1] = 0;
			}
		} else {
			if (s.charAt(1) != '0') {
				dp[1] = 2;
			} else {
				dp[1] = 1;
			}
		}

		for (int i = 2; i < s.length(); i++) {
			if (s.charAt(i) != '0') {
				dp[i] += dp[i - 1];
			}

			int val = Integer.parseInt(s.substring(i - 1, i + 1));
			if (val <= 26 && val >= 10) {
				dp[i] += dp[i - 2];
			}
		}

		return dp[s.length() - 1];
	}

	public static void main(String[] args) {
		String testString = "12145";
		List<Character> list = new ArrayList<>();
		DecodeWays ob = new DecodeWays();
		System.out.println("-----------decodeI-------------");
		System.out.println(ob.decodeI(testString));
		System.out.println("-----------decodeI_dp-------------");
		System.out.println(ob.decodeI_dp(testString));
		System.out.println(ob.dpMap);
		System.out.println("-----------numDecodings-------------");
		System.out.println(ob.numDecodings(testString));
		System.out.println("-----------decodeDPCS-------------");
		System.out.println(ob.decodeDPCS(testString, 0));
	}
}
