package algorithm.recursion;

import java.util.LinkedList;

/*
 * You are given coins of different denominations and a total amount of
 * money amount. Write a function to compute the fewest number of coins
 * that you need to make up that amount. If that amount of money cannot
 * be made up by any combination of the coins, return -1.
 */
public class CoinMinimumCount {

	public int coinChange(int[] coins, int target) {
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
	

	public static void main(String[] args) {
		CoinMinimumCount ob = new CoinMinimumCount();
		int[] coins = { 25, 10, 5, 1 };
		int target = 12226;
		System.out.println(ob.coinChange(coins, target));
	}
}
