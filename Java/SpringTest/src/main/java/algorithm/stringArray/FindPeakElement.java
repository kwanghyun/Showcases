package algorithm.stringArray;

import java.util.Arrays;

import algorithm.Utils;

/*
 * A peak element is an element that is greater than its neighbors. Given an
 * input array where num[i] ≠ num[i+1], find a peak element and return its
 * index. The array may contain multiple peaks, in that case return the
 * index to any one of the peaks is fine.
 * 
 * You may imagine that num[-1] = num[n] = -∞. For example, in array [1, 2,
 * 3, 1], 3 is a peak element and your function should return the index
 * number 2.
 * 
 * Thoughts
 * 
 * This is a very simple problem. We can scan the array and find any element
 * that is greater can its previous and next. The first and last element are
 * handled separately.
 */
public class FindPeakElement {

	public int findPeakElement(int[] nums) {
		int max = nums[0];
		int index = 0;
		for (int i = 1; i <= nums.length - 2; i++) {
			int prev = nums[i - 1];
			int curr = nums[i];
			int next = nums[i + 1];

			if (curr > prev && curr > next && curr > max) {
				index = i;
				max = curr;
			}
		}

		if (nums[nums.length - 1] > max) {
			return nums.length - 1;
		}

		return index;
	}

	public static void main(String[] args) {
		FindPeakElement ob = new FindPeakElement();
		int[] nums = Utils.shuffleArray(Utils.createIntArrayFromRange(1, 8));
		System.out.println(Arrays.toString(nums));

		System.out.println(ob.findPeakElement(nums));
	}
}
