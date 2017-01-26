package algorithm.dynamic;

import java.util.ArrayList;
import java.util.List;

/*
 * Given a rod of length n inches and an array of prices that contains
 * prices of all pieces of size smaller than n. Determine the maximum value
 * obtainable by cutting up the rod and selling the pieces. For example, if
 * length of the rod is 8 and the values of different pieces are given as
 * following, then the maximum obtainable value is 22 (by cutting in two
 * pieces of lengths 2 and 6)
 * length   | 1   2   3   4   5   6   7   8  
 * --------------------------------------------
 * price    | 1   5   8   9  10  17  17  20
 * And if the prices are as following, then the maximum obtainable value is 24 (
 * 	by cutting in eight pieces of length 1)
 * 
 * length   | 1   2   3   4   5   6   7   8  
 * --------------------------------------------
 * price    | 3   5   8   9  10  17  17  20
 * The naive solution for this problem is to generate all configurations of different 
 * pieces and find the highest priced configuration. This solution is exponential 
 * in term of time complexity. Let us see how this problem possesses both important 
 * properties of a Dynamic Programming (DP) Problem and can efficiently solved 
 * using Dynamic Programming.
 */
public class CuttingRod {

	int max(int a, int b) {
		return (a > b) ? a : b;
	}

	/*
	 * Returns the best obtainable price for a rod of length n and price[] as
	 * prices of different pieces
	 */
	int cutRod(int price[], int n) {
		if (n <= 0)
			return 0;
		int max_val = 0;

		// Recursively cut the rod in different pieces and compare different
		// configurations
		for (int i = 0; i < n; i++)
			max_val = max(max_val, price[i] + cutRod(price, n - i - 1));

		return max_val;
	}

	/*
	 * Returns the best obtainable price for a rod of length n and price[] as
	 * prices of different pieces
	 */
	int cutRodDP(int price[], int n) {
		int dp[] = new int[n + 1];
		dp[0] = 0;
		int i, j;

		// Build the table val[] in bottom up manner and return the last entry
		// from the table
		for (i = 1; i <= n; i++) {
			int max_val = Integer.MIN_VALUE;
			for (j = 0; j < i; j++) {
				// condition "j < i", because max rod length is already given.
				max_val = max(max_val, price[j] + dp[i - j - 1]);
				dp[i] = max_val;
			}

		}

		return dp[n];
	}

	public static void main(String args[]) {
		List<Integer> list = new ArrayList<>();
		CuttingRod cr = new CuttingRod();
		int[] price = { 3, 5, 8, 9, 10, 20, 22, 25 };
		long t1 = System.currentTimeMillis();
		System.out.println(cr.cutRod(price, 8));
		System.out.println(cr.cutRodDP(price, 8));
		long t2 = System.currentTimeMillis();
		// System.out.println(r);
//		System.out.println(t2 - t1);
	}
}
