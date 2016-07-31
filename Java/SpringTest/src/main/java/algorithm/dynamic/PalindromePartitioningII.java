package algorithm.dynamic;

/*
 * Given a string s, partition s such that every substring of the partition
 * is a palindrome. Return the minimum cuts needed for a palindrome
 * partitioning of s. For example, given s = "aab", return 1 since the
 * palindrome partitioning ["aa","b"] could be produced using 1 cut.
 * 
 * Analysis
 * 
 * This problem is similar to Palindrome Partitioning. It can be efficiently
 * solved by using dynamic programming. Unlike "Palindrome Partitioning", we
 * need to maintain two cache arrays, one tracks the partition position and
 * one tracks the number of minimum cut.
 */
public class PalindromePartitioningII {
	public int minCut(String s) {
		int n = s.length();

		boolean dp[][] = new boolean[n][n];
		int cut[] = new int[n];

		for (int c = 0; c < n; c++) {
			cut[c] = c; // set maximum # of cut
			for (int r = 0; r <= c; r++) {
				if (s.charAt(r) == s.charAt(c) && (c - r <= 1 || dp[r + 1][c - 1])) {
					dp[r][c] = true;

					// if need to cut, add 1 to the previous cut[i-1]
					if (r > 0) {
						cut[c] = Math.min(cut[c], cut[r - 1] + 1);
					} else {
						// if [0...j] is palindrome, no need to cut
						cut[c] = 0;
					}
				}
			}
		}

		return cut[n - 1];
	}

	public static int partition(String s) {
		int n = s.length();
		boolean palindrome[][] = new boolean[n][n]; // boolean table

		// Trivial case: single letter palindromes
		for (int i = 0; i < n; i++) {
			palindrome[i][i] = true;
		}

		// Finding palindromes of two characters.
		for (int i = 0; i < n - 1; i++) {
			if (s.charAt(i) == s.charAt(i + 1)) {
				palindrome[i][i + 1] = true;
			}
		}

		// Finding palindromes of length 3 to n
		for (int curr_len = 3; curr_len <= n; curr_len++) {
			for (int r = 0; r < n - curr_len + 1; r++) {
				int c = r + curr_len - 1;
				// 1. The first and last characters should match
				// 2. Rest of the substring should be a palindrome
				if (s.charAt(r) == s.charAt(c) && palindrome[r + 1][c - 1]) {
					palindrome[r][c] = true;
				}
			}
		}

		int[] cuts = new int[n];
		for (int c = 0; c < n; c++) {
			int temp = Integer.MAX_VALUE;
			if (palindrome[0][c])
				cuts[c] = 0;
			else {
				for (int r = 0; r < c; r++) {
					if ((palindrome[r + 1][c]) && temp > cuts[r] + 1) {
						temp = cuts[r] + 1;
					}
				}
				cuts[c] = temp;
			}
		}
		return cuts[n - 1];
	}

	public static void main(String args[]) {
		System.out.println(partition("aab"));
	}
}
