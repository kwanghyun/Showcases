package algorithm.dynamic;

import java.util.ArrayList;
import java.util.List;

import algorithm.Utils;

/*
 * Given a string s, partition s such that every substring of the partition
 * is a palindrome.
 * 
 * Return all possible palindrome partitioning of s.
 * 
 * For example, given s = "aab",
 * Return
 * 
 * [
 * ["aa","b"],
 * ["a","a","b"]
 * ]
 */

public class PalindromePartitioning {
	/* 1. Depth-first Search */
	public ArrayList<ArrayList<String>> partition(String s) {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();

		if (s == null || s.length() == 0) {
			return result;
		}

		// track each possible partition
		ArrayList<String> partition = new ArrayList<String>();
		addPalindrome(s, 0, partition, result);

		return result;
	}

	private void addPalindrome(String s, int start, ArrayList<String> partition, ArrayList<ArrayList<String>> result) {
		// stop condition
		if (start == s.length()) {
			ArrayList<String> temp = new ArrayList<String>(partition);
			result.add(temp);
			return;
		}

		for (int i = start + 1; i <= s.length(); i++) {
			String str = s.substring(start, i);
			if (isPalindrome(str)) {
				partition.add(str);
				addPalindrome(s, i, partition, result);
				partition.remove(partition.size() - 1);
			}
		}
	}

	private boolean isPalindrome(String str) {
		int left = 0;
		int right = str.length() - 1;

		while (left < right) {
			if (str.charAt(left) != str.charAt(right)) {
				return false;
			}

			left++;
			right--;
		}

		return true;
	}

	/*
	 * 2. Dynamic Programming
	 */

	public static int partitionI(String s) {
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

	public int minCut(String str) {
		if (str.length() == 0) {
			return 0;
		}

		int[] cut = new int[str.length()];
		boolean dp[][] = new boolean[str.length()][str.length()];
		for (int r = 1; r < str.length(); r++) {
			int min = r;
			for (int c = 0; c <= r; c++) {
				if (str.charAt(r) == str.charAt(c) && (r <= c + 1 || dp[r - 1][c + 1])) {
					dp[r][c] = true;
					min = Math.min(min, c == 0 ? 0 : 1 + cut[c - 1]);
				}
			}
			cut[r] = min;
		}

		return cut[str.length() - 1];
	}

	public static void main(String[] args) {
		PalindromePartitioning ob = new PalindromePartitioning();
		System.out.println(ob.minCut("aab"));

	}
}
