package algorithm.recursion;

/*
 * Given a positive integer n, break it into the sum of at least two
 * positive integers and maximize the product of those integers. Return the
 * maximum product you can get.
 * 
 * For example, given n = 2, return 1 (2 = 1 + 1); given n = 10, return 36
 * (10 = 3 + 3 + 4).
 * 
 * Note: You may assume that n is not less than 2 and not larger than 58.
 */
public class IntegerBreak {
	int max = 0;

	public int integerBreak(int n) {
		int sum = 0;
		int prod = 1;
		if (n == 2)
			return 1;
		if (n == 3)
			return 2;

		integerBreak(n, 1, sum, prod);
		return max;
	}

	public void integerBreak(int n, int idx, int sum, int prod) {
		if (sum == n) {
			max = Math.max(max, prod);
		}

		for (int i = idx; i <= n; i++) {
			if (sum + i > n)
				break;
			integerBreak(n, i, sum + i, prod * i);
		}
	}

	public int integerBreakDP(int n) {
		int[] dp = new int[n + 1];

		dp[0] = 0;
		dp[1] = 1;
		dp[2] = 1;
		dp[3] = 2;
		dp[4] = 4;

		for (int i = 5; i <= n; i++) {
			dp[i] = dp[i - 2] + dp[i - 1];
		}

		return dp[n];
	}

	public int integerBreakDPI(int n) {
		int[] dp = new int[n + 1];

		for (int i = 1; i < n; i++) {
			for (int j = 1; j < i + 1; j++) {
				if (i + j <= n) {
					dp[i + j] = Math.max(Math.max(dp[i], i) * Math.max(dp[j], j), dp[i + j]);
				}
			}
		}

		return dp[n];
	}

	public static void main(String[] args) {
		IntegerBreak ob = new IntegerBreak();
		System.out.println(ob.integerBreak(13));
		System.out.println(ob.integerBreakDP(13));
		System.out.println(ob.integerBreakDPI(13));
	}
}
