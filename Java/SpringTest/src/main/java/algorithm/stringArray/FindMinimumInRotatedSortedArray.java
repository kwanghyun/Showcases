package algorithm.stringArray;

/*
 * Suppose a sorted array is rotated at some pivot unknown to you
 * beforehand.
 * 
 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
 * 
 * Find the minimum element.
 * 
 * You may assume no duplicate exists in the array.
 */

public class FindMinimumInRotatedSortedArray {
	public int findMin(int[] nums) {
		if (nums == null || nums.length == 0)
			return 0;
		if (nums.length == 1)
			return nums[0];

		int start = 0;
		int end = nums.length - 1;

		if (nums[start] < nums[end])
			return nums[0];

		while (start < end) {

			int mid = (start + end) / 2;

			if (end - start == 1) {
				start = nums[start] < nums[end] ? start : end;
				break;
			} else if (nums[start] < nums[mid]) {
				// start mid is sorted
				start = mid + 1;
			} else {
				end = mid;
			}
		}
		return nums[start];
	}

	public static void main(String[] args) {
		FindMinimumInRotatedSortedArray ob = new FindMinimumInRotatedSortedArray();
		// int[] arr = { 4, 5, 6, 7, 0, 1, 2 };
		int[] arr = { 4, 5, 6, 7, 8, 9, 0, 1, 2 };
		System.out.println(ob.findMin(arr));

		int[] arr1 = { 9, 0, 1, 2, 4, 5, 6, 7, 8, };
		System.out.println(ob.findMin(arr1));
	}
}
