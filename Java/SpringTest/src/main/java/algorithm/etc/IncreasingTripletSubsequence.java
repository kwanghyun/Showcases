package algorithm.etc;

import java.util.Stack;

/*
 * Given an unsorted array return whether an increasing subsequence of
 * length 3 exists or not in the array.
 * 
 * Formally the function should:
 * Return true if there exists i, j, k
 * such that arr[i] < arr[j] < arr[k] given 0 ≤ i < j < k ≤ n-1 else return
 * false.
 * 
 * Your algorithm should run in O(n) time complexity and O(1) space
 * complexity.
 * 
 * Examples:
 * Given [1, 2, 3, 4, 5],
 * return true.
 * Given [5, 4, 3, 2, 1],
 * return false.
 */

public class IncreasingTripletSubsequence {
	public boolean increasingTriplet(int[] nums) {
		if (nums.length < 3)
			return false;
		Stack<Integer> stack = new Stack<>();
		for (int i = 0; i < nums.length; i++) {
			while (!stack.isEmpty() && stack.peek() >= nums[i]) {
				stack.pop();
			}
			stack.push(nums[i]);
			if (stack.size() >= 3)
				return true;
		}
		return false;
	}

	public static void main(String[] args) {
		IncreasingTripletSubsequence ob = new IncreasingTripletSubsequence();
		int[] nums = { 1, 0, 0, 0, 10, 0, 0, 0, 100 };
		System.out.println(ob.increasingTriplet(nums));
	}
}
