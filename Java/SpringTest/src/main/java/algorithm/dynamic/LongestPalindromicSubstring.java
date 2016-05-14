package algorithm.dynamic;

/*
 * Given a string S, find the longest palindromic substring.
 * 
 * Algorithm/Insights
 * 
 * 1. Initialize a palindrome boolean table of size nxn where n is the
 * length of the given String
 * 2. Set single length palindrome values to true
 * 3. Set palindromes of lenght 2 as true
 * 4. Loop from lengths 3 to n and check palindrome for each length using
 * the following rule 
 * palindrome[i][j] = true, if palindrome[i+1][j-1] and s[i] == s[j]
 * 5. after the loop, return the longest palindromic substring
 */
public class LongestPalindromicSubstring {

	public static String LPS(String s) {
		int strLen = s.length();
		int palindromeBeginsAt = 0; // index where the longest palindrome begins
		int maxLen = 1;
		boolean dp[][] = new boolean[strLen][strLen];

		// Trivial case: single letter palindromes
		for (int i = 0; i < strLen; i++) {
			dp[i][i] = true;
		}

		// Finding palindromes of two characters.
		for (int i = 0; i < strLen - 1; i++) {
			if (s.charAt(i) == s.charAt(i + 1)) {
				dp[i][i + 1] = true;
				palindromeBeginsAt = i;
				maxLen = 2;
			}
		}

		// Finding palindromes of length 3 to n and saving the longest
		for (int curLen = 3; curLen <= strLen; curLen++) {
			for (int start = 0; start < strLen - curLen + 1; start++) {
				int end = start + curLen - 1;
				/*
				 * 1. The first and last characters should match 
				 * 2. Rest of the substring should be a palindrome
				 */
				if (s.charAt(start) == s.charAt(end) && dp[start + 1][end - 1]) {
					dp[start][end] = true;
					palindromeBeginsAt = start;
					maxLen = curLen;
				}
			}
		}
		return s.substring(palindromeBeginsAt, maxLen + palindromeBeginsAt);
	}

	public static void main(String args[]) {
		System.out.println(LPS("banana"));
	}
}
