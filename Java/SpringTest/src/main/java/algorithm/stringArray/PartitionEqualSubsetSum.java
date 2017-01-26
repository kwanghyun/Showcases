package algorithm.stringArray;

import java.util.Arrays;

/*
 * Given a non-empty array containing only positive integers, find if the
 * array can be partitioned into two subsets such that the sum of elements
 * in both subsets is equal.
 * 
 * Note: Each of the array element will not exceed 100. The array size will
 * not exceed 200. 
 * 
 * Example 1:
 * Input: [1, 5, 11, 5]
 * Output: true
 * Explanation: The array can be partitioned as [1, 5, 5] and [11]. 
 * 
 * Example 2:
 * Input: [1, 2, 3, 5]
 * Output: false
 * Explanation: The array cannot be partitioned into equal sum subsets.
 */
public class PartitionEqualSubsetSum {
	public boolean canPartition(int[] nums) {

		if (nums.length <= 1)
			return false;

		int sum = 0;
		for (int i = 0; i < nums.length; i++) {
			sum += nums[i];
		}

		if (sum % 2 != 0)
			return false;

		if (canPartition(nums, sum / 2))
			return true;

		return false;
	}

	public boolean canPartition(int[] nums, int target) {
		boolean[][] dp = new boolean[nums.length][target + 1];

		dp[0][0] = true;
		dp[0][nums[0]] = true;

		for (int i = 1; i < nums.length; i++) {
			for (int j = 0; j <= target; j++) {
				if (j == 0) {
					dp[i][j] = true;
				} else if (nums[i] <= j) {
					dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i]];
				} else {
					dp[i][j] = dp[i - 1][j];
				}
			}
		}

		return dp[nums.length - 1][target];
	}

	public boolean canPartitionI(int[] nums, int target, int idx, int sum) {
		if (sum == target)
			return true;

		for (int i = idx; i < nums.length; i++) {
			if (canPartitionI(nums, target, i + 1, sum + nums[i]))
				return true;
		}
		return false;
	}

	public static void main(String[] args) {
		PartitionEqualSubsetSum ob = new PartitionEqualSubsetSum();
		// int[] nums = { 1, 5, 11, 5 };
		int[] nums = { 1, 2, 3, 5 };
		// int[] nums = { 28, 63, 95, 30, 39, 16, 36, 44, 37, 100, 61, 73, 32,
		// 71, 100, 2, 37, 60, 23, 71, 53, 70, 69, 82,
		// 97, 43, 16, 33, 29, 5, 97, 32, 29, 78, 93, 59, 37, 88, 89, 79, 75, 9,
		// 74, 32, 81, 12, 34, 13, 16, 15,
		// 16, 40, 90, 70, 17, 78, 54, 81, 18, 92, 75, 74, 59, 18, 66, 62, 55,
		// 19, 2, 67, 30, 25, 64, 84, 25, 76,
		// 98, 59, 74, 87, 5, 93, 97, 68, 20, 58, 55, 73, 74, 97, 49, 71, 42,
		// 26, 8, 87, 99, 1, 16, 79 };
		System.out.println(ob.canPartition(nums));
	}

}
