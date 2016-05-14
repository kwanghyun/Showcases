package algorithm.dynamic;

/*
 * Given a string S, find the longest palindromic subsequence.
 * 
 * Algorithm/Insights
 * 
 * 1. Initialize a palindrome DP table of size nxn where n is the length of
 * the given String
 * 2. Set single length palindrome values to 1
 * 3. Loop from lengths 2 to n and check palindrome for each length using
 * the following rule
 * palindrome[i][j] = palindrome[i+1][j-1] + 2, if s[i] == s[j]
 * palindrome[i][j] = Math.max(palindrome[i+1][j], palindrome[i][j-1]), if
 * s[i] != s[j]
 * 4. after the loop, return palindrome[0][n-1]
 */

public class LongestPalindromicSubsequence {

	public static int LPS(String s) {
		int n = s.length();
		int palindrome[][] = new int[n][n]; // Table to store lengths of
											// palindrome subsequences.

		// Trivial case: single letter palindromes
		for (int i = 0; i < n; i++) {
			palindrome[i][i] = 1;
		}

		// Finding palindromes of length 2 to n and saving the longest
		for (int curr_len = 2; curr_len <= n; curr_len++) {
			for (int i = 0; i < n - curr_len + 1; i++) {
				int j = i + curr_len - 1;
				if (s.charAt(i) == s.charAt(j))// Trim if match and add 2
				{
					palindrome[i][j] = palindrome[i + 1][j - 1] + 2;
				} else // Trim one at a time and take max
				{
					palindrome[i][j] = Math.max(palindrome[i + 1][j], palindrome[i][j - 1]);
				}
			}
		}

		return palindrome[0][n - 1];
	}

	public static void main(String args[]) {
		System.out.println(LPS("LPSSAPAL"));
	}
}
