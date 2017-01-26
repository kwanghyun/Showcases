package algorithm.dynamic;

import java.util.Arrays;
import java.util.HashSet;

import algorithm.Utils;

/*
 * Given a sorted positive integer array nums and an integer n, add/patch
 * elements to the array such that any number in range [1, n] inclusive can
 * be formed by the sum of some elements in the array. Return the minimum
 * number of patches required.
 * 
 * Example 1: 
 * nums = [1, 3], n = 6 
 * Return 1.
 * 
 * Combinations of nums are [1], [3], [1,3], which form possible sums of: 1,
 * 3, 4. Now if we add/patch 2 to nums, the combinations are: [1], [2], [3],
 * [1,3], [2,3], [1,2,3]. Possible sums are 1, 2, 3, 4, 5, 6, which now
 * covers the range [1, 6]. So we only need 1 patch.
 * 
 * Example 2: 
 * nums = [1, 5, 10], n = 20 
 * Return 2. 
 * The two patches can be [2,4].
 * 
 * Example 3: 
 * nums = [1, 2, 2], n = 5 
 * Return 0.
 */
public class PatchingArray {
	public int minPatches(int[] nums, int n) {
		boolean dp[] = new boolean[n + 1];
		combinationSum(nums, n, dp);
		Utils.printArray(dp);
		int patch = 0;

		HashSet<Integer> targetSet = new HashSet<>();
		getRestNums(dp, targetSet);

		if (targetSet.size() == 0)
			return 0;

		for (int i = 1; i < n; i++) {

			if (dp[i] == false) {
				patch++;
				System.out.println(targetSet);
				for (int j = 0; j < dp.length - i; j++) {
					System.out.println("i = " + i + ", j = " + j + ", dp[j] == true => " + dp[j] );
					if (dp[j] == true && targetSet.contains(i + j))
						targetSet.remove(i + j);

				}
				if (targetSet.size() == 0)
					break;
			}

		}
		return patch;
	}

	public void combinationSum(int[] nums, int n, boolean[] dp) {

		int comb = 1 << nums.length;

		for (int i = 0; i < comb; i++) {
			int sum = 0;
			for (int j = 0; j < nums.length; j++) {

				if ((i >> j & 1) == 1) {
					sum += nums[j];
				}
			}
			dp[sum] = true;
		}
	}

	private void getRestNums(boolean[] dp, HashSet<Integer> set) {
		for (int i = 0; i < dp.length; i++) {
			if (dp[i] == false)
				set.add(i);
		}
	}

	public static void main(String[] args) {
		PatchingArray ob = new PatchingArray();
		// int[] nums = { 1, 3 };
		// int n = 6;
		int[] nums = { 1, 5, 10 };
		int n = 20;

		System.out.println(ob.minPatches(nums, n));
	}
}
