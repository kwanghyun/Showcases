package algorithm.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* 
 * Given an infinite number of quarters (25 cents), dimes (10 cents), nickels (5 cents) 
 * and pennies (1 cent), write code to calculate the number of ways of representing n cents.
 */

public class CoinCounter {

	public void countCoins(int target) {
		int[] coins = { 1, 5, 10, 25 };
		List<Integer> list = new ArrayList<>();
		countCoins(coins, target, list, 0);
	}

	public void countCoins(int[] coins, int target, List<Integer> list, int idx) {
		if (target < 0)
			return;

		if (target == 0)
			System.out.println(list);

		for (int i = idx; i < coins.length; i++) {
			list.add(coins[i]);

			// Allow the same position re-selection. Let re-visit first idx "0"
			// by using ++
			countCoins(coins, target - coins[i], list, idx++);
			list.remove(list.size() - 1);
		}
	}

	public void countCoins2(int target) {
		int[] coins = { 1, 5, 10, 25 };
		List<Integer> list = new ArrayList<>();
		countCoins2(coins, target, list, 0);
	}

	public void countCoins2(int[] coins, int target, List<Integer> list, int idx) {
		if (target < 0)
			return;

		if (target == 0)
			System.out.println(list);

		for (int i = idx; i < coins.length; i++) {
			list.add(coins[i]);
			// Not allow the same position re-selection
			countCoins2(coins, target - coins[i], list, idx + 1);
			list.remove(list.size() - 1);
		}
	}

	// Permuations (Different order is considered as unique count)
	List<Integer> coins = Arrays.asList(1, 5, 10, 25);

	public void countCoins3(int target, List<Integer> list) {

		if (target < 0)
			return;

		if (target == 0)
			System.out.println(list);

		for (int i = 0; i < coins.size(); i++) {
			int curr = coins.get(i);
			list.add(curr);
			countCoins3(target - curr, list);
			list.remove(list.size() - 1);
		}
	}

	public static void main(String args[]) {

		int TARGET_AMOUNT = 16;

		CoinCounter counter = new CoinCounter();

		System.out.println("------------------countCoins---------------------");
		counter.countCoins(TARGET_AMOUNT);

		System.out.println("------------------countCoins2---------------------");
		counter.countCoins2(TARGET_AMOUNT);

		System.out.println("------------------countCoins3---------------------");
		List<Integer> list = new ArrayList<>();
		counter.countCoins3(TARGET_AMOUNT, list);
	}

	/*
	 * 
	 * 
	 * The reason we getting duplicated numbers....
	 * 
	 * [5, 5, 1, 1, 1, 1, 1, 1] 0, 0, 2, 6 [5, 1, 5, 1, 1, 1, 1, 1] 0, 0, 2, 6
	 * [5, 1, 1, 5, 1, 1, 1, 1] 0, 0, 2, 6 [5, 1, 1, 1, 5, 1, 1, 1] 0, 0, 2, 6
	 * [5, 1, 1, 1, 1, 5, 1, 1] 0, 0, 2, 6 [5, 1, 1, 1, 1, 1, 5, 1] 0, 0, 2, 6
	 * [5, 1, 1, 1, 1, 1, 1, 5] 0, 0, 2, 6 [1, 5, 5, 1, 1, 1, 1, 1] 0, 0, 2, 6
	 * [1, 5, 1, 5, 1, 1, 1, 1] 0, 0, 2, 6 [1, 5, 1, 1, 5, 1, 1, 1] 0, 0, 2, 6
	 * [1, 5, 1, 1, 1, 5, 1, 1] 0, 0, 2, 6 [1, 5, 1, 1, 1, 1, 5, 1] 0, 0, 2, 6
	 * [1, 5, 1, 1, 1, 1, 1, 5] 0, 0, 2, 6 [1, 1, 5, 5, 1, 1, 1, 1] 0, 0, 2, 6
	 * [1, 1, 5, 1, 5, 1, 1, 1] 0, 0, 2, 6 [1, 1, 5, 1, 1, 5, 1, 1] 0, 0, 2, 6
	 * [1, 1, 5, 1, 1, 1, 5, 1] 0, 0, 2, 6 [1, 1, 5, 1, 1, 1, 1, 5] 0, 0, 2, 6
	 * [1, 1, 1, 5, 5, 1, 1, 1] 0, 0, 2, 6 [1, 1, 1, 5, 1, 5, 1, 1] 0, 0, 2, 6
	 * [1, 1, 1, 5, 1, 1, 5, 1] 0, 0, 2, 6 [1, 1, 1, 5, 1, 1, 1, 5] 0, 0, 2, 6
	 * [1, 1, 1, 1, 5, 5, 1, 1] 0, 0, 2, 6 [1, 1, 1, 1, 5, 1, 5, 1] 0, 0, 2, 6
	 * [1, 1, 1, 1, 5, 1, 1, 5] 0, 0, 2, 6 [1, 1, 1, 1, 1, 5, 5, 1] 0, 0, 2, 6
	 * [1, 1, 1, 1, 1, 5, 1, 5] 0, 0, 2, 6 [1, 1, 1, 1, 1, 1, 5, 5] 0, 0, 2, 6
	 */
}
