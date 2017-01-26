package algorithm.stringArray;

/*
 * Given an unsorted array return whether an increasing subsequence of
 * length 3 exists or not in the array.
 * 
 * Formally the function should: Return true if there exists i, j, k such
 * that arr[i] < arr[j] < arr[k] given 0 ≤ i < j < k ≤ n-1 else return
 * false. Your algorithm should run in O(n) time complexity and O(1) space
 * complexity.
 * 
 * Examples: 
 * Given [1, 2, 3, 4, 5], 
 * return true.
 * 
 * Given [5, 4, 3, 2, 1], 
 * return false.
 */
public class IncreasingTripletSubsequence {
	public boolean increasingTriplet(int[] nums) {
		/*
		 * Analysis
		 * 
		 * This problem can be converted to be finding if there is a sequence
		 * such that the_smallest_so_far < the_second_smallest_so_far < current.
		 * We use x, y and z to denote the 3 number respectively.
		 */
		int first = Integer.MAX_VALUE;
		int second = Integer.MAX_VALUE;

		for (int i = 0; i < nums.length; i++) {
			int curr = nums[i];

			if (first >= curr) {
				first = curr;// update x to be a smaller value
			} else if (second >= curr) {
				second = curr; // update y to be a smaller value
			} else {
				return true;
			}
		}

		return false;
	}
}
