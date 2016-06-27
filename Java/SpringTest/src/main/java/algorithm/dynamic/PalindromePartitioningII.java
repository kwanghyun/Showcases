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

		for (int j = 0; j < n; j++) {
			cut[j] = j; // set maximum # of cut
			for (int i = 0; i <= j; i++) {
				if (s.charAt(i) == s.charAt(j) && (j - i <= 1 || dp[i + 1][j - 1])) {
					dp[i][j] = true;

					// if need to cut, add 1 to the previous cut[i-1]
					if (i > 0) {
						cut[j] = Math.min(cut[j], cut[i - 1] + 1);
					} else {
						// if [0...j] is palindrome, no need to cut
						cut[j] = 0;
					}
				}
			}
		}

		return cut[n - 1];
	}
}
