package algorithm.dynamic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import algorithm.Utils;

/*
 * Given a total and coins of certain denomination with infinite supply, what is
 * the minimum number of coins it takes to form this total.
 *
 * Time complexity - O(coins.size * total) Space complexity - O(coins.size *
 * total)
 */
public class CoinChangingMinimumCoin {

	/**
	 * Top down dynamic programing. Using map to store intermediate results.
	 * Returns Integer.MAX_VALUE if total cannot be formed with given coins
	 */
	public int minimumCoinTopDown(int total, int coins[], Map<Integer, Integer> map) {

		// if total is 0 then there is nothing to do. return 0.
		if (total == 0) {
			return 0;
		}

		// if map contains the result means we calculated it before. Lets return
		// that value.
		if (map.containsKey(total)) {
			return map.get(total);
		}

		// iterate through all coins and see which one gives best result.
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < coins.length; i++) {
			// if value of coin is greater than total we are looking for just
			// continue.
			if (coins[i] > total) {
				continue;
			}
			// recurse with total - coins[i] as new total
			int val = minimumCoinTopDown(total - coins[i], coins, map);

			// if val we get from picking coins[i] as first coin for current
			// total is less
			// than value found so far make it minimum.
			if (val < min) {
				min = val;
			}
		}

		// if min is MAX_VAL dont change it. Just result it as is. Otherwise add
		// 1 to it.
		min = (min == Integer.MAX_VALUE ? min : min + 1);

		// memoize the minimum for current total.
		map.put(total, min);
		return min;
	}

	/**
	 * Bottom up way of solving this problem. Keep input sorted. Otherwise
	 * temp[j-arr[i]) + 1 can become Integer.Max_value + 1 which can be very low
	 * negative number Returns Integer.MAX_VALUE - 1 if solution is not
	 * possible.
	 */
	public int minimumCoinBottomUp(int total, int coins[]) {
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

	public int coinChange(int[] coins, int amount) {
		if (amount == 0)
			return 0;

		Arrays.sort(coins);

		int[][] dp = new int[coins.length][amount + 1];
		for (int r = 0; r < coins.length; r++) {
			for (int c = 0; c <= amount; c++) {
				if (c == 0) {
					dp[r][c] = 0;
				} else if (r == 0) {
					if (c % coins[0] == 0)
						dp[r][c] = c / coins[0];
					else
						dp[r][c] = -1;

				} else if (coins[r] > c) {
					dp[r][c] = dp[r - 1][c];
				} else {
					int top = dp[r - 1][c];
					int right = dp[r][c - coins[r]] + 1;
					if (top != -1 && right != -1)
						dp[r][c] = Math.min(top, right);
					else if (top != -1)
						dp[r][c] = top;
					else if (right != -1)
						dp[r][c] = right;
					else
						dp[r][c] = -1;
				}
			}
		}
		Utils.printMetrix(dp);
		return dp[coins.length - 1][amount];
	}

	public static void main(String args[]) {
		int total = 6249;
		int coins[] = { 186, 419, 83, 408 };

		CoinChangingMinimumCoin cc = new CoinChangingMinimumCoin();
		System.out.println(cc.coinChange(coins, total));

		// Map<Integer, Integer> map = new HashMap<>();

		// int topDownValue = cc.minimumCoinTopDown(total, coins, map);
		// int bottomUpValue = cc.minimumCoinBottomUp(total, coins);

		// System.out.print(String.format("Bottom up and top down result %s %s",
		// bottomUpValue, topDownValue));

	}
}