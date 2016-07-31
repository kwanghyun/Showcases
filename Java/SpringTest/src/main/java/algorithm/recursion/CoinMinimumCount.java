package algorithm.recursion;

import java.util.LinkedList;

/*
 * You are given coins of different denominations and a total amount of
 * money amount. Write a function to compute the fewest number of coins
 * that you need to make up that amount. If that amount of money cannot
 * be made up by any combination of the coins, return -1.
 */
public class CoinMinimumCount {

	public int minCoinChange(int[] coins, int target) {
		if (target == 0)
			return 0;
		LinkedList<Integer> sums = new LinkedList<>();
		LinkedList<Integer> steps = new LinkedList<>();

		sums.offer(0);
		steps.offer(0);

		while (!sums.isEmpty()) {
			int curr = sums.poll();
			int step = steps.poll();

			if (curr == target)
				return step;

			for (int coin : coins) {
				if (curr > target) {
					continue;
				} else {
					if (!sums.contains(curr + coin)) {
						sums.offer(curr + coin);
						steps.offer(step + 1);
					}
				}
			}
		}
		return -1;
	}

	/**
	 * minCoinChangeDP way of solving this problem. Keep input sorted. Otherwise
	 * temp[j-arr[i]) + 1 can become Integer.Max_value + 1 which can be very low
	 * negative number Returns Integer.MAX_VALUE - 1 if solution is not
	 * possible.
	 */
	public int minCoinChangeDP(int total, int coins[]) {
		int dp[] = new int[total + 1];
		int usedCoinIdx[] = new int[total + 1];
		dp[0] = 0;
		for (int i = 1; i <= total; i++) {
			dp[i] = Integer.MAX_VALUE - 1;
			usedCoinIdx[i] = -1;
		}
		for (int i = 0; i < coins.length; i++) {
			for (int p = 1; p <= total; p++) {
				if (p >= coins[i]) {
					if (dp[p - coins[i]] + 1 < dp[p]) {
						dp[p] = 1 + dp[p - coins[i]];
						usedCoinIdx[p] = i;
					}
				}
			}
		}
		printCoinCombination(usedCoinIdx, coins);
		return dp[total];
	}

	private void printCoinCombination(int usedCoinIdx[], int coins[]) {
		if (usedCoinIdx[usedCoinIdx.length - 1] == -1) {
			System.out.print("No solution is possible");
			return;
		}
		int start = usedCoinIdx.length - 1;
		System.out.print("Coins used to form total ");
		while (start != 0) {
			int idx = usedCoinIdx[start];
			System.out.print(coins[idx] + " ");
			start = start - coins[idx];
		}
		System.out.print("\n");
	}

	public static void main(String[] args) {
		CoinMinimumCount ob = new CoinMinimumCount();
		int[] coins = { 25, 10, 5, 1 };
		int target = 46;
		System.out.println(ob.minCoinChange(coins, target));
		System.out.println(ob.minCoinChangeDP(target, coins));
	}
}
