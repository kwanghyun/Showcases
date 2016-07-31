package algorithm.dynamic;

import java.util.ArrayList;
import java.util.List;

/*
 * Given two strings S1 and S2. Find the longest common substring between S1
 * and S2.
 * 
 * Example: S1 = "LCLC" S2 = "CLCL" 
 * Longest Common Substring is "CLC"
 * 
 * Order of the Algorithm
 * 
 * Time Complexity is O(n^2) Space Complexity is O(n^2)
 */

public class LongestCommonSubstring {

	public static List<String> commonSubstring(String S1, String S2) {
		Integer dp[][] = new Integer[S1.length()][S2.length()];

		int len1 = S1.length();
		int len2 = S2.length();
		int max = Integer.MIN_VALUE; // Maximum length of the string
		ArrayList<String> result = null; // Result list

		for (int r = 0; r < len1; r++) {
			for (int c = 0; c < len2; c++) {
				if (S1.charAt(r) == S2.charAt(c)) {
					if (r == 0 || c == 0)
						dp[r][c] = 1;
					else
						dp[r][c] = dp[r - 1][c - 1] + 1;

					// If you find a longer common substring re-initialize the
					// max count and update the result list.
					if (dp[r][c] > max) {
						max = dp[r][c];
						result = new ArrayList<String>();
						result.add(S1.substring(r - max + 1, r + 1));
						// substring starts at i-max+1 and ends at i
					} else if (dp[r][c] == max) {
						// else if you find a common substring with the max
						// length, store it in the list.
						result.add(S1.substring(r - max + 1, r + 1));
					}
				} else
					dp[r][c] = 0;

			}
		}
		return result;
	}

	public static String commonSubstringI(String s1, String s2) {
		int max = 0;
		int count = 0;
		String maxString = "";
		char ch1, ch2;

		for (int i = 0; i < s1.length(); i++) {
			for (int j = 0; j < s2.length(); j++) {
				ch1 = s1.charAt(i);
				ch2 = s2.charAt(j);
				if (ch1 == ch2) {
					count = 1;
					while (i + count < s1.length() && j + count < s2.length()) {
						ch1 = s1.charAt(i + count);
						ch2 = s2.charAt(j + count);
						if (ch1 == ch2) {
							count++;
						} else {
							break;
						}
					}
					if (count > max) {
						max = count;
						maxString = s1.substring(i, i + count);
					}
				}
			}
		}
		return maxString;
	}

	public static void main(String args[]) {
		List<String> result = commonSubstring("CLCL", "LCLC");
		for (String str : result) {
			System.out.println(str);
		}
		System.out.println("-----------------------------------");
		System.out.println(commonSubstringI("CLCL", "LCLC"));
		
		
	}
}
