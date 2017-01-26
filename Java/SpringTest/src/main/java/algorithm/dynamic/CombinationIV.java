package algorithm.dynamic;

import java.util.*;

import algorithm.Utils;

/*
 * Given an integer array with all positive numbers and no duplicates, find
 * the number of possible combinations that add up to a positive integer
 * target.
 * 
 * Example:
 * 
 * nums = [1, 2, 3]
 *  target = 4
 * 
 * The possible combination ways are: 
 * (1, 1, 1, 1) 
 * (1, 1, 2) 
 * (1, 2, 1) 
 * (1, 3) 
 * (2, 1, 1) 
 * (2, 2) 
 * (3, 1)
 * 
 * Note that different sequences are counted as different combinations.
 * 
 * Therefore the output is 7. 
 * 
 * Follow up: What if negative numbers are allowed in the given array? 
 * How does it change the problem? 
 * What limitation we need to add to the question to allow negative numbers?
 */
public class CombinationIV {
	int count = 0;

	public int combinationSum4I(int[] nums, int target) {
		if (nums.length == 0)
			return 0;

		Arrays.sort(nums);
		dfs(nums, target, 0);
		return count;
	}

	public void dfs(int[] nums, int target, int sum) {
		if (sum == target) {
			count++;
		}
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] + sum > target)
				break;
			dfs(nums, target, sum + nums[i]);
		}
	}

	public int combinationSum4(int[] nums, int target) {
		if (nums.length == 0)
			return 0;

		int[][] dp = new int[nums.length][target + 1];

		dp[0][0] = 1;

		for (int i = 1; i <= target; i++) {
			dp[0][i] = 1;
		}

		for (int r = 1; r < nums.length; r++) {
			for (int c = 0; c <= target; c++) {
				if (c < nums[r])
					dp[r][c] = dp[r - 1][c];
				else
					dp[r][c] = dp[r - 1][c] + dp[r][c - nums[r]] + 1;
			}
			Utils.printMetrix(dp);
		}
		return dp[nums.length - 1][target];
	}

	public int combinationSum4DP(int[] nums, int target) {
		if (nums == null || nums.length == 0)
			return 0;

		int[] dp = new int[target + 1];

		dp[0] = 1;

		for (int i = 0; i <= target; i++) {
			for (int num : nums) {
				if (i + num <= target) {
					dp[i + num] += dp[i];
				}

			}
			System.out.println(Arrays.toString(dp));
		}
		return dp[target];
	}

	public static void main(String[] args) {
		CombinationIV ob = new CombinationIV();
		int[] nums = { 1, 2, 3 };
		// int target = 32;
		int target = 4;
		System.out.println(ob.combinationSum4(nums, target));
		System.out.println(ob.combinationSum4DP(nums, target));
	}
}
