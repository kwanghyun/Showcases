package algorithm.dynamic;

/*
 * Minimum number of squares whose sum equals to given number n A number can
 * always be represented as a sum of squares of other numbers.
 * 
 * Note that 1 is a square and we can always break a number as (1*1 + 1*1 +
 * 1*1 + …). Given a number n, find the minimum number of squares that sum
 * to X.
 * 
 * Examples:
	Input:  n = 100
	Output: 1
	100 can be written as 10^2. 
	Note that 100 can also be  written as 5^2 + 5^2 + 5^2 + 5^2, but this
	representation requires 4 squares.
	
	Input:  n = 6
	Output: 3
 */
public class MinimumSquaresNSum {

	public int getMinSquareSumCount(int n) {
		if (n <= 3) {
			return n;
		}
		int minCount = n;

		for (int i = 1; i <= Math.sqrt(n); i++) {
			int val = i * i;
			if (val > n)
				break;
			minCount = Math.min(minCount, 1 + getMinSquareSumCount(n - val));
		}
		return minCount;
	}

	public int getMinSquareSumCountDP(int n) {

		int[] dp = new int[n + 1];

		dp[0] = 0;
		dp[1] = 1;
		dp[2] = 2;
		dp[3] = 3;

		for (int i = 4; i <= n; i++) {

			dp[i] = i;

			for (int j = 1; j <= Math.sqrt(i); j++) {
				int val = j * j;
				if (val > i)
					break;
				System.out.println(" i = " + i + ", val = " + val);
				dp[i] = Math.min(dp[i], 1 + dp[i - val]);
			}
		}

		return dp[n];
	}

	public int getMinSquareSumCountDPI(int n) {

		int[] dp = new int[n + 1];

		dp[0] = 0;
		dp[1] = 1;
		dp[2] = 2;
		dp[3] = 3;

		for (int i = 1; i <= Math.sqrt(n); i++) {

			int val = i * i;

			for (int j = 4; j <= n; j++) {
				if (val <= j) {
					System.out.println(" i = " + i + ", j = " + j + ", val = " + val);
					dp[j] = Math.min(j, 1 + dp[j - val]);
				}
			}
		}

		return dp[n];
	}

	public static void main(String[] args) {
		MinimumSquaresNSum ob = new MinimumSquaresNSum();
		int testN = 10;
		System.out.println("---------- getMinSquareSumCountDP ----------");
		System.out.println(ob.getMinSquareSumCountDP(testN));
		System.out.println("---------- getMinSquareSumCountDPI ----------");
		System.out.println(ob.getMinSquareSumCountDPI(testN));
		System.out.println("---------- getMinSquareSumCount ----------");
		System.out.println(ob.getMinSquareSumCount(testN));

	}
}
