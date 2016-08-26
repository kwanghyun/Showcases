package algorithm.sort;

import algorithm.Utils;

/*
 * Given an array with n objects colored red, white or blue, sort them so
 * that objects of the same color are adjacent, with the colors in the order
 * red, white and blue.
 * 
 * Here, we will use the integers 0, 1, and 2 to represent the color red,
 * white, and blue respectively.
 */
public class SortColors {
	
	public void sortColors(int[] nums) {
		if (nums == null || nums.length < 2) {
			return;
		}

		int[] countArray = new int[3];
		for (int i = 0; i < nums.length; i++) {
			countArray[nums[i]]++;
		}

		int start = 0;
		for (int i = 0; i < nums.length; i++) {
			int count = countArray[start];
			if (count == 0) {
				start++;
			}
			countArray[start]--;
			nums[i] = start;
		}
	}

	public void sortColorsI(int[] nums) {
		if (nums == null || nums.length < 2) {
			return;
		}

		int[] countArray = new int[3];
		for (int i = 0; i < nums.length; i++) {
			countArray[nums[i]]++;
		}

		for (int i = 1; i <= 2; i++) {
			countArray[i] = countArray[i - 1] + countArray[i];
		}

		int[] sorted = new int[nums.length];
		for (int i = 0; i < nums.length; i++) {
			int index = countArray[nums[i]] - 1;
			countArray[nums[i]] = countArray[nums[i]] - 1;
			sorted[index] = nums[i];
		}

		System.arraycopy(sorted, 0, nums, 0, nums.length);
	}


	public static void main(String[] args) {
		SortColors ob = new SortColors();
		int[] nums = { 1, 2, 0, 1, 1, 1, 2, 0, 0, 1 };
		int[] nums2 = { 1, 2, 0, 1, 1, 1, 2, 0, 0, 1 };

		Utils.printArray(nums);
		System.out.println("----------------sortColors---------------");
		ob.sortColors(nums);
		Utils.printArray(nums);
		System.out.println("----------------sortColorsI---------------");
		ob.sortColorsI(nums2);
		Utils.printArray(nums2);
	}
}
