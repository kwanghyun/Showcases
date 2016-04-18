package algorithm.stringArray;

import java.util.Arrays;

/*
 * Given an array of size n, find the majority element. The majority element
 * is the element that appears more than ⌊ n/2 ⌋ times. (assume that the
 * array is non-empty and the majority element always exist in the array.)
 * 
 * Java Solution 1 - Naive
 * 
 * We can sort the array first, which takes time of nlog(n). Then scan once
 * to find the longest consecutive substrings.
 */

public class MajorityElementI {

	public int majorityElementI(int[] num) {
		if (num.length == 1) {
			return num[0];
		}

		Arrays.sort(num);

		int prev = num[0];
		int count = 1;
		for (int i = 1; i < num.length; i++) {
			if (num[i] == prev) {
				count++;
				if (count > num.length / 2)
					return num[i];
			} else {
				count = 1;
				prev = num[i];
			}
		}

		return 0;
	}

	/*
	 * Since the majority always take more than a half space, the middle element
	 * is guaranteed to be the majority. Sorting array takes nlog(n). So the
	 * time complexity of this solution is nlog(n).
	 */
	public int majorityElementII(int[] num) {
		if (num.length == 1) {
			return num[0];
		}

		Arrays.sort(num);
		return num[num.length / 2];
	}

	// Linear Time Majority Vote Algorithm
	public int majorityElementIII(int[] nums) {
		int result = 0, count = 0;

		for (int i = 0; i < nums.length; i++) {
			if (count == 0) {
				result = nums[i];
				count = 1;
			} else if (result == nums[i]) {
				count++;
			} else {
				count--;
			}
		}

		return result;
	}
}
