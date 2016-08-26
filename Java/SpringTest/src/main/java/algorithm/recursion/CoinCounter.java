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

	public void countCoinsI(int target) {
		int[] coins = { 1, 5, 10, 25 };
		List<Integer> list = new ArrayList<>();
		countCoinsI(coins, target, list, 0);
	}

	public void countCoinsII(int target) {
		int[] coins = { 1, 5, 10, 25 };
		List<Integer> list = new ArrayList<>();
		countCoinsII(coins, target, list, 0);
	}

	public void countCoinsIV(int target) {
		int[] coins = { 1, 5, 10, 25 };
		List<Integer> list = new ArrayList<>();
		countCoinsIV(coins, target, list);
	}

	public void countCoinsIII(int target) {
		int[] coins = { 1, 5, 10, 25 };
		List<Integer> list = new ArrayList<>();
		countCoinsII(coins, target, list, 0);
	}

	public void countCoins(int[] coins, int target, List<Integer> list, int idx) {
		if (target < 0)
			return;

		if (target == 0)
			System.out.println(list);

		for (int i = idx; i < coins.length; i++) {
			list.add(coins[i]);
			countCoins(coins, target - coins[i], list, i); // same as idx++
			list.remove(list.size() - 1);
		}
	}

	public void countCoinsI(int[] coins, int target, List<Integer> list, int idx) {
		if (target < 0)
			return;

		if (target == 0)
			System.out.println(list);

		for (int i = idx; i < coins.length; i++) {
			list.add(coins[i]);

			// Allow the same position re-selection. Let re-visit first idx "0"
			// by using ++,
			countCoinsI(coins, target - coins[i], list, idx++);
			list.remove(list.size() - 1);
		}
	}

	public void countCoinsII(int[] coins, int target, List<Integer> list, int idx) {
		if (target < 0)
			return;

		if (target == 0)
			System.out.println(list);

		for (int i = idx; i < coins.length; i++) {
			list.add(coins[i]);
			// Not allow the same position re-selection
			countCoinsII(coins, target - coins[i], list, idx + 1);
			list.remove(list.size() - 1);
		}
	}

	public void countCoinsIII(int[] coins, int target, List<Integer> list, int idx) {
		if (target < 0)
			return;

		if (target == 0)
			System.out.println(list);

		for (int i = idx; i < coins.length; i++) {
			list.add(coins[i]);
			// Not allow the same position re-selection
			countCoinsIII(coins, target - coins[i], list, ++idx);
			list.remove(list.size() - 1);
		}
	}

	// Permuations (Different order is considered as unique count)
	public void countCoinsIV(int[] coins, int target, List<Integer> list) {

		if (target < 0)
			return;

		if (target == 0)
			System.out.println(list);

		for (int i = 0; i < coins.length; i++) {
			int curr = coins[i];
			list.add(curr);
			countCoinsIV(coins, target - curr, list);
			list.remove(list.size() - 1);
		}
	}

	public static void main(String args[]) {

		int TARGET_AMOUNT = 16;
		CoinCounter counter = new CoinCounter();

		System.out.println("------------------countCoins---------------------");
		counter.countCoins(TARGET_AMOUNT);

		System.out.println("------------------countCoinsI---------------------");
		counter.countCoinsI(TARGET_AMOUNT);

		System.out.println("------------------countCoinsII---------------------");
		counter.countCoinsII(TARGET_AMOUNT);

		System.out.println("------------------countCoinsIII---------------------");
		counter.countCoinsII(TARGET_AMOUNT);

		System.out.println("------------------countCoinsIV---------------------");
		counter.countCoinsIV(TARGET_AMOUNT);
	}
}
