package algorithm.dynamic;

import java.util.ArrayList;
import java.util.List;

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
	 * 
	 * The dynamic programming approach is very similar to the problem of
	 * longest palindrome substring.
	 */
	public static List<String> palindromePartitioning(String s) {

		List<String> result = new ArrayList<String>();

		if (s == null)
			return result;

		if (s.length() <= 1) {
			result.add(s);
			return result;
		}

		int length = s.length();

		int[][] table = new int[length][length];

		// len is length, i is index of left boundary, j is index of right
		// boundary
		for (int len = 1; len <= length; len++) {
			for (int lidx = 0; lidx <= length - len; lidx++) {
				int ridx = lidx + len - 1;
				if (s.charAt(lidx) == s.charAt(ridx)) {
					if (len == 1 || len == 2) {
						table[lidx][ridx] = 1;
					} else {
						table[lidx][ridx] = table[lidx + 1][ridx - 1];
					}
					if (table[lidx][ridx] == 1) {
						result.add(s.substring(lidx, ridx + 1));
					}
				} else {
					table[lidx][ridx] = 0;
				}
			}
		}

		return result;
	}
	

    public int minCut(String str){
        if (str.length() == 0) {
            return 0;
        }

        int[] cut = new int[str.length()];
        boolean isPal[][] = new boolean[str.length()][str.length()];
        for (int i = 1; i < str.length(); i++) {
            int min = i;
            for (int j = 0; j <= i; j++) {
                if (str.charAt(i) == str.charAt(j) && (i <= j + 1 || isPal[i - 1][j + 1])) {
                    isPal[i][j] = true;
                    min = Math.min(min, j == 0 ? 0 : 1 + cut[j - 1]);
                }
            }
            cut[i] = min;
        }
        return cut[str.length() - 1];
    }
}
