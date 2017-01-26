package algorithm.stringArray;

import java.util.Arrays;

/*
 * Given an unsorted array nums, reorder it in-place such that nums[0] <=
 * nums[1] >= nums[2] <= nums[3]....
 * 
 * For example, given nums = [3, 5, 2, 1, 6, 4], one possible answer is [1,
 * 6, 2, 5, 3, 4].
 */
public class WiggleSort {
	/*
	 * Understand the problem:
	 * 
	 * A[0] <= A[1] >= A[2] <= A[3] >= A[4] <= A[5]
	 * 
	 * So we could actually observe that there is pattern that
	 * 
	 * A[even] <= A[odd],
	 * 
	 * A[odd] >= A[even].
	 */

	public void wiggleSort(int[] nums) {
		if (nums == null || nums.length <= 1) {
			return;
		}

		for (int i = 0; i < nums.length - 1; i++) {
			if (i % 2 == 0) {
				if (nums[i] > nums[i + 1]) {
					swap(nums, i, i + 1);
				}
			} else {
				if (nums[i] < nums[i + 1]) {
					swap(nums, i, i + 1);
				}
			}
		}
	}

	public void wiggleSortI(int[] nums) {
		for (int i = 0; i < nums.length; i++) {
			int idx;
			if (i % 2 == 0)
				idx = getMin(nums, i);
			else
				idx = getMax(nums, i);
			if (i != idx)
				swap(nums, i, idx);
		}
		System.out.println(Arrays.toString(nums));
	}

	public int getMin(int[] nums, int start) {
		int minIdx = start;
		for (int i = start + 1; i < nums.length; i++) {
			if (nums[i] < nums[minIdx]) {
				minIdx = i;
			}
		}
		return minIdx;
	}

	public int getMax(int[] nums, int start) {
		int maxIdx = start;
		for (int i = start + 1; i < nums.length; i++) {
			if (nums[i] > nums[maxIdx]) {
				maxIdx = i;
			}
		}
		return maxIdx;
	}

	public void swap(int[] nums, int a, int b) {
		int tmp = nums[a];
		nums[a] = nums[b];
		nums[b] = tmp;
	}

	public static void main(String[] args) {
		WiggleSort ob = new WiggleSort();
		int nums[] = { 3, 5, 2, 1, 6, 4 };
		ob.wiggleSort(nums);
	}

}
