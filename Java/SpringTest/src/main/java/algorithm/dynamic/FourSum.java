package algorithm.dynamic;

import java.util.*;

/*
 * Given an array S of n integers, are there elements a, b, c, and d in S
 * such that a + b + c + d = target? Find all unique quadruplets in the
 * array which gives the sum of target.
 * 
 * Note:
 * Elements in a quadruplet (a,b,c,d) must be in non-descending order. (ie,
 * a ≤ b ≤ c ≤ d) The solution set must not contain duplicate quadruplets.
 * 
 * For example, given array S = {1 0 -1 0 -2 2}, and target = 0.
 * 
 * A solution set is:
 * 
 * (-1, 0, 0, 1)
 * 
 * (-2, -1, 1, 2)
 * 
 * (-2, 0, 0, 2)
 */

public class FourSum {

	public List<List<Integer>> fourSum(int[] nums, int target) {
		Arrays.sort(nums);

		Set<ArrayList<Integer>> hashSet = new HashSet<>();
		List<List<Integer>> result = new ArrayList<>();

		for (int i = 0; i < nums.length; i++) {
			for (int j = i + 1; j < nums.length; j++) {
				int k = j + 1;
				int l = nums.length - 1;

				while (k < l) {
					int sum = nums[i] + nums[j] + nums[k] + nums[l];

					if (sum > target) {
						l--;
					} else if (sum < target) {
						k++;
					} else if (sum == target) {
						ArrayList<Integer> temp = new ArrayList<Integer>();
						temp.add(nums[i]);
						temp.add(nums[j]);
						temp.add(nums[k]);
						temp.add(nums[l]);

						if (!hashSet.contains(temp)) {
							hashSet.add(temp);
							result.add(temp);
						}

						k++;
						l--;
					}
				}
			}
		}
		return result;
	}

	public ArrayList<ArrayList<Integer>> fourSumI(int[] num, int target) {
		Arrays.sort(num);

		HashSet<ArrayList<Integer>> hashSet = new HashSet<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

		for (int i = 0; i < num.length; i++) {
			for (int j = i + 1; j < num.length; j++) {
				int k = j + 1;
				int l = num.length - 1;

				while (k < l) {
					int sum = num[i] + num[j] + num[k] + num[l];

					if (sum > target) {
						l--;
					} else if (sum < target) {
						k++;
					} else if (sum == target) {
						ArrayList<Integer> temp = new ArrayList<Integer>();
						temp.add(num[i]);
						temp.add(num[j]);
						temp.add(num[k]);
						temp.add(num[l]);

						if (!hashSet.contains(temp)) {
							hashSet.add(temp);
							result.add(temp);
						}

						k++;
						l--;
					}
				}
			}
		}
		return result;
	}
}
