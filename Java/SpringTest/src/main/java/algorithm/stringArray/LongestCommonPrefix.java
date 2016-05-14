package algorithm.stringArray;

/*
 * Problem
 * 
 * Write a function to find the longest common prefix string amongst an
 * array of strings.
 * 
 * Analysis
 * 
 * To solve this problem, we need to find the two loop conditions. One is
 * the length of the shortest string. The other is iteration over every
 * element of the string array.
 */

public class LongestCommonPrefix {

	public String longestCommonPrefix(String[] strs) {
		if (strs == null || strs.length == 0)
			return "";

		int minLen = Integer.MAX_VALUE;

		for (String str : strs) {
			if (minLen > str.length())
				minLen = str.length();
		}

		if (minLen == 0)
			return "";

		for (int chIdx = 0; chIdx < minLen; chIdx++) {
			char prev = '0';
			for (int strIdx = 0; strIdx < strs.length; strIdx++) {
				if (strIdx == 0) {
					prev = strs[strIdx].charAt(chIdx);
					continue;
				}

				if (strs[strIdx].charAt(chIdx) != prev) {
					return strs[strIdx].substring(0, chIdx);
				}
			}
		}

		return strs[0].substring(0, minLen);
	}
	
	public static void main(String[] args) {
		String[] strs = {"ABCDEF", "ABCDEFHJDR", "ABCDEFDSD", "ABCDEFABCDEF"};
		LongestCommonPrefix ob = new LongestCommonPrefix();
		System.out.println(ob.longestCommonPrefix(strs));
		
	}
}
