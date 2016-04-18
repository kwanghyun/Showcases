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

	public void countCoins2(int amount, int c25, int c10, int c5, int c1) {

		int total = 25 * c25 + 10 * c10 + 5 * c5 + c1;

		if (amount < total)
			return;

		if (amount == total) {
			System.out.println(c25 + ", " + c10 + ", " + c5 + ", " + c1);
		} else {
			countCoins2(amount, c25 + 1, c10, c5, c1);
			countCoins2(amount, c25, c10 + 1, c5, c1);
			countCoins2(amount, c25, c10, c5 + 1, c1);
			countCoins2(amount, c25, c10, c5, c1 + 1);
		}
		return;
	}

	public Set<String> resultSet = new HashSet<>();

	public void countCoins(int target, int c25, int c10, int c5, int c1) {

		if (target < 0)
			return;

		if (target == 0) {
			resultSet.add(c25 + ", " + c10 + ", " + c5 + ", " + c1);

		} else {
			countCoins(target - 25, c25 + 1, c10, c5, c1);
			countCoins(target - 10, c25, c10 + 1, c5, c1);
			countCoins(target - 5, c25, c10, c5 + 1, c1);
			countCoins(target - 1, c25, c10, c5, c1 + 1);
		}
	}

	List<Integer> sortedCoins = Arrays.asList(1, 5, 10, 25);

	public void countCoins5(int target, int startIdx, List<Integer> list) {

		if (target < 0)
			return;

		if (target == 0)
			System.out.println(list);

		for (int idx = startIdx; idx < sortedCoins.size(); idx++) {
			int curr = sortedCoins.get(idx);
			if (curr > target)
				return;

			list.add(curr);
			countCoins5(target - curr, idx, list);
			list.remove(list.size() - 1);
		}
	}

	// DFS Approach,
	List<Integer> coins = Arrays.asList(25, 10, 5, 1);

	public void countCoins4(int target, List<Integer> list) {

		if (target < 0)
			return;

		if (target == 0)
			System.out.println(list);

		for (int idx = 0; idx < coins.size(); idx++) {
			int curr = coins.get(idx);
			list.add(curr);
			countCoins4(target - curr, list);
			list.remove(list.size() - 1);
		}
	}

	public static void main(String args[]) {
		CoinCounter counter = new CoinCounter();

		System.out.println("---------------------------------------");
		counter.countCoins(16, 0, 0, 0, 0);
		counter.resultSet.stream().forEach(System.out::println);

		System.out.println("---------------------------------------");
		List<Integer> alist = new ArrayList<>();
		counter.countCoins5(16, 0, alist);

		System.out.println("---------------------------------------");
		List<Integer> list = new ArrayList<>();
		counter.countCoins4(16, list);

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
