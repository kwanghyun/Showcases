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
		int lo = 0;
		int hi = nums.length - 1;
		int mid = 0;

		while (mid <= hi) {

			switch (nums[mid]) {
			case 0:
				swap(nums, lo, mid);
				lo++;
				mid++;
				break;

			case 1:
				mid++;
				break;

			case 2:
				swap(nums, mid, hi);
				hi--;
				break;

			}
		}
    }
    
	// Sort the input array, the array is assumed to
	// have values in {0, 1, 2}
	public void sort012(int a[]) {
		int lo = 0;
		int hi = a.length - 1;
		int mid = 0;

		while (mid <= hi) {

			switch (a[mid]) {
			case 0:
				swap(a, lo, mid);
				lo++;
				mid++;
				break;

			case 1:
				mid++;
				break;

			case 2:
				swap(a, mid, hi);
				hi--;
				break;

			}
		}
	}

	public void swap(int[] arr, int a, int b) {
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
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
		int[] nums1 = { 1, 2, 0, 1, 1, 1, 2, 0, 0, 1 };
		int[] nums2 = { 1, 2, 0, 1, 1, 1, 2, 0, 0, 1 };

		Utils.printArray(nums);
		System.out.println("----------------sortColors---------------");
		ob.sortColors(nums);
		Utils.printArray(nums);
		System.out.println("----------------sort012---------------");
		ob.sort012(nums1);
		Utils.printArray(nums1);
		System.out.println("----------------sortColorsI---------------");
		ob.sortColorsI(nums2);
		Utils.printArray(nums2);
	}
}
