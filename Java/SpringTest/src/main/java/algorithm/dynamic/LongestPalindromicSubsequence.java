package algorithm.dynamic;

/* 
 * Given a sequence, find the length of the longest palindromic subsequence
 * in it. For example, if the given sequence is “BBABCBCAB”, then the output
 * should be 7 as “BABCBAB” is the longest palindromic subseuqnce in it.
 * “BBBBB” and “BBCBB” are also palindromic subsequences of the given
 * sequence, but not the longest ones.
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
		int dp[][] = new int[n][n];

		for (int i = 0; i < n; i++) {
			dp[i][i] = 1;
		}

		// Finding palindromes of length 2 to n and saving the longest
		for (int len = 2; len <= n; len++) {
			for (int r = 0; r < n - len + 1; r++) {
				int c = r + len - 1;
				if (s.charAt(r) == s.charAt(c))// Trim if match and add 2
				{
					dp[r][c] = dp[r + 1][c - 1] + 2;
				} else // Trim one at a time and take max
				{
					dp[r][c] = Math.max(dp[r + 1][c], dp[r][c - 1]);
				}
			}
		}

		return dp[0][n - 1];
	}

	public static void main(String args[]) {
		System.out.println(LPS("BBABCBCAB"));
		System.out.println(LPS("LPSSAPAL"));
	}
}
