package algorithm.stringArray;

import java.util.*;

/*
 * Given a non-empty array of integers, return the third maximum number in
 * this array. If it does not exist, return the maximum number. The time
 * complexity must be in O(n).
 * 
 * Example 1: Input: [3, 2, 1]
 * Output: 1
 * 
 * Explanation: The third maximum is 1. 
 * 
 * Example 2: Input: [1, 2]
 * Output: 2
 * 
 * Explanation: The third maximum does not exist, so the maximum (2) is
 * returned instead. 
 * 
 * Example 3: Input: [2, 2, 3, 1]
 * Output: 1
 * 
 * Explanation: Note that the third maximum here means the third maximum
 * distinct number. Both numbers with value 2 are both considered as second
 * maximum.
 */
public class ThirdMaximumNumber {
	public int thirdMax(int[] nums) {
		if (nums == null || nums.length == 0)
			return 0;

		int len = nums.length;
		Set<Integer> set = new HashSet<>();
		Queue<Integer> q = new PriorityQueue<>();

		for (int i = 0; i < len; i++) {
			if (!set.contains(nums[i])) {
				set.add(nums[i]);
				q.add(nums[i]);
				if (q.size() > 3)
					q.poll();
			}
		}

		if (q.size() == 3)
			return q.peek();

		int result = 0;
		while (!q.isEmpty())
			result = q.poll();

		return result;
	}

	public static void main(String[] args) {
		ThirdMaximumNumber ob = new ThirdMaximumNumber();
		int[] nums = { 3, 2, 1 };
		System.out.println(ob.thirdMax(nums));
	}
}
