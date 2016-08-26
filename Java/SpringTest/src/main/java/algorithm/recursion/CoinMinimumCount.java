package algorithm.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import algorithm.Utils;

/*
 * You are given coins of different denominations and a total amount of
 * money amount. Write a function to compute the fewest number of coins
 * that you need to make up that amount. If that amount of money cannot
 * be made up by any combination of the coins, return -1.
 */
public class CoinMinimumCount {

	public int minCoinChangeBFS(int[] coins, int target) {
		if (target == 0)
			return 0;
		LinkedList<Integer> sums = new LinkedList<>();
		LinkedList<Integer> counts = new LinkedList<>();

		sums.offer(0);
		counts.offer(0);

		while (!sums.isEmpty()) {
			int curr = sums.poll();
			int count = counts.poll();

			if (curr == target)
				return count;

			for (int coin : coins) {
				if (curr > target) {
					break;
				} else {
					if (!sums.contains(curr + coin)) {
						sums.offer(curr + coin);
						counts.offer(count + 1);
					}
				}
			}
		}
		return -1;
	}

	public int minCoinChangeDP(int total, int coins[]) {
		int dp[][] = new int[coins.length][total + 1];

		for (int c = 0; c <= total; c++) {
			dp[0][c] = c;
		}

		for (int r = 1; r < coins.length; r++) {
			dp[r][0] = dp[r - 1][0];
		}

		for (int r = 1; r < coins.length; r++) {
			for (int c = 1; c <= total; c++) {
				// coin is smaller than total, use above row
				if (coins[r] > c) {
					dp[r][c] = dp[r - 1][c];
				} else {
					int min = Math.min(dp[r - 1][c], 1 + dp[r][c - coins[r]]);
					dp[r][c] = min;
				}
			}
			// Utils.printMetrix(dp);
		}
		return dp[coins.length - 1][total];
	}

	public int minCoinChangeDFS(int[] coins, int target, int idx, ArrayList<Integer> list, int min) {
		if (target == 0) {
			min = Math.min(min, list.size());
			System.out.println(list);
			return min;
		}

		for (int i = idx; i < coins.length; i++) {
			if (coins[i] > target)
				break;

			list.add(coins[i]);
			min = minCoinChangeDFS(coins, target - coins[i], i, list, min);
			list.remove(list.size() - 1);
		}
		return min;
	}

	public int minCoinChangeDFSI(int[] coins, int target, int idx, ArrayList<Integer> list, int min) {
		if (target == 0) {
			min = Math.min(min, list.size());
			System.out.println(list);
			return min;
		}

		for (int i = idx; i < coins.length; i++) {
			if (coins[i] > target)
				break;

			list.add(coins[i]);
			min = minCoinChangeDFSI(coins, target - coins[i], idx + 1, list, min);
			list.remove(list.size() - 1);
		}
		return min;
	}

	public static void main(String[] args) {
		CoinMinimumCount ob = new CoinMinimumCount();
		int[] coins = { 25, 10, 5, 1 };
		Arrays.sort(coins);
		int target = 16;
		System.out.println("------------------------------minCoinChangeBFS---------------------------");
		System.out.println(ob.minCoinChangeBFS(coins, target));
		System.out.println("------------------------------minCoinChangeDP---------------------------");
		System.out.println(ob.minCoinChangeDP(target, coins));
		ArrayList<Integer> list = new ArrayList<>();
		System.out.println("------------------------------minCoinChangeDFS---------------------------");
		System.out.println(ob.minCoinChangeDFS(coins, target, 0, list, Integer.MAX_VALUE));
		System.out.println("------------------------------minCoinChangeDFSI---------------------------");
		System.out.println(ob.minCoinChangeDFSI(coins, target, 0, list, Integer.MAX_VALUE));

	}
}
