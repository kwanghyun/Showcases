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
 * 
 * 2. Dynamic Programming
 * 
 * Let s be the input string, i and j are two indices of the string. Define
 * a 2-dimension array "table" and let table[i][j] denote whether a
 * substring from i to j is palindrome.
 * 
 * Changing condition:
 * 
 * table[i+1][j-1] == 1 && s.charAt(i) == s.charAt(j)
 * =>
 * table[i][j] == 1
 * 
 * 1 0 0 0 0 0 
 * 0 1 0 0 0 1 
 * 0 0 1 0 1 0 
 * 0 0 0 1 0 0 
 * 0 0 0 0 1 0 
 * 0 0 0 0 0 1 
 */
public class LongestPalindromicSubstring {

	/*
	 * 1. Dynamic Programming
	 * 
	 * Let s be the input string, i and j are two indices of the string. Define
	 * a 2-dimension array "table" and let table[i][j] denote whether a
	 * substring from i to j is palindrome.
	 * 
	 * Changing condition:
	 * 
	 * table[i+1][j-1] == 1 && s.charAt(i) == s.charAt(j) => table[i][j] == 1
	 * 
	 * Time O(n^2) Space O(n^2)
	 */
	public String longestPalindrome(String s) {
		if (s == null || s.length() <= 1)
			return s;

		int len = s.length();
		int maxLen = 1;
		boolean[][] dp = new boolean[len][len];

		String longest = null;
		for (int l = 0; l < s.length(); l++) {
			for (int i = 0; i < len - l; i++) {
				int j = i + l;
				if (s.charAt(i) == s.charAt(j) && (j - i <= 2 || dp[i + 1][j - 1])) {
					dp[i][j] = true;

					if (j - i + 1 > maxLen) {
						maxLen = j - i + 1;
						longest = s.substring(i, j + 1);
					}
				}
			}
		}

		return longest;
	}
	
	public String longestPalindromeMy(String s) {

		if (s == null || s.length() <= 1)
			return s;

		int strlen = s.length();
		int maxLen = 0;
		boolean[][] dp = new boolean[strlen][strlen];
		String maxStr = null;

		for (int i = 0; i < strlen; i++) {
			dp[i][i] = true;
		}

		for (int len = 1; len <= strlen; len++) {
			for (int r = 0; r < strlen - len; r++) {
				int start = r;
				int end = r + len;
				if (s.charAt(start) == s.charAt(end) && dp[start + 1][end - 1] == true) {
					dp[start][end] = true;
					if (maxLen < len) {
						maxLen = len;
						maxStr = s.substring(start, end + 1);
					}
				} else
					dp[start][end] = false;
			}
		}
		return maxStr;
	}


	public String longestPalindromeI(String s) {
		if (s.isEmpty() || s.length() == 1) {
			return s;
		}

		String longest = s.substring(0, 1);
		for (int i = 0; i < s.length(); i++) {
			// get longest palindrome with center of i
			String tmp = helper(s, i, i);
			if (tmp.length() > longest.length()) {
				longest = tmp;
			}

			// get longest palindrome with center of i, i+1
			tmp = helper(s, i, i + 1);
			if (tmp.length() > longest.length()) {
				longest = tmp;
			}
		}
		return longest;
	}

	// Given a center, either one letter or two letter,
	// Find longest palindrome
	public String helper(String s, int begin, int end) {
		while (begin >= 0 && end <= s.length() - 1 && s.charAt(begin) == s.charAt(end)) {
			begin--;
			end++;
		}
		return s.substring(begin + 1, end);
	}

	public static void main(String args[]) {
		LongestPalindromicSubstring ob = new LongestPalindromicSubstring();
		System.out.println(ob.longestPalindromeI("banana"));
		System.out.println("-------------------------");
		System.out.println(ob.longestPalindromeMy("banana"));

	}
}
