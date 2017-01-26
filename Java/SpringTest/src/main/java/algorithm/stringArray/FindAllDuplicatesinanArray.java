package algorithm.stringArray;

import java.util.*;

public class FindAllDuplicatesinanArray {
	/*
	 * Given an array of integers, 1 ≤ a[i] ≤ n (n = size of array), some
	 * elements appear twice and others appear once.
	 * 
	 * Find all the elements that appear twice in this array.
	 * 
	 * Could you do it without extra space and in O(n) runtime?
	 * 
	 * Example: Input: [4,3,2,7,8,2,3,1]
	 * 
	 * Output: [2,3]
	 */
	public List<Integer> findDuplicates(int[] nums) {
		Map<Integer, Integer> map = new HashMap<>();
		List<Integer> result = new ArrayList<>();

		for (int i = 0; i < nums.length; i++) {
			int n = nums[i];
			if (map.containsKey(n)) {
				map.put(n, map.get(n) + 1);
				result.add(n);
			} else {
				map.put(n, 1);
			}
		}

		return result;
	}
}
