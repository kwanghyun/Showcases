package algorithm.etc;

import java.util.ArrayList;

/*
 * Given a sorted array of integers, find the starting and ending position of a given
 target value. Your algorithm’s runtime complexity must be in the order of O(log n). If
 the target is not found in the array, return [-1, -1]. For example, given [5, 7, 7, 8, 8, 10]
 and target value 8, return [3, 4].
 */
public class SearchForRange {
	
	public int[] searchRange(int[] nums, int target) {
		
		if (nums == null || nums.length == 0) {
			return null;
		}
		
		ArrayList<Integer> result = new ArrayList<Integer>();

		for (int i = 0; i < nums.length; i++) {
			if (nums[i] == target) {
				result.add(i);
			}
		}
		
		int[] arr = new int[2];
		if (result.size() == 0) {
			arr[0] = -1;
			arr[1] = -1;
		} else {
			arr[0] = result.get(0);
			arr[1] = result.get(result.size() - 1);
		}
		return arr;
	}
}
