package algorithm.stringArray;

/*
 * Suppose a sorted array is rotated at some pivot unknown to you
 * beforehand.
 * 
 * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
 * 
 * You are given a target value to search. If found in the array return its
 * index, otherwise return -1.
 * 
 * You may assume no duplicate exists in the array.
 */
public class FindElementInRotatedArray {

	// Find an element in a sorted rotated array without finding pivot
	public static int findElementUsingBinarySearch(int[] array, int num) {
		if (array == null || array.length == 0) {
			return -1;
		}

		int start = 0;
		int end = array.length - 1;

		while (start <= end) {

			int mid = (start + end) / 2;
			if (num == array[mid]) {
				return mid;
			}

			if (array[start] <= array[mid]) { // array[start...mid] is sorted

				if (array[start] <= num && num <= array[mid]) { // num lies
																// between
																// array[start...mid]
					end = mid - 1;
				} else {
					start = mid + 1;
				}
			} else { // array[mid...end] is sorted

				if (array[mid] <= num && num <= array[end]) { // num lies
																// between
																// array[mid...end]
					start = mid + 1;
				} else {
					end = mid - 1;
				}
			}
		}

		return -1;
	}

    public int search(int[] nums, int target) {
		if (nums == null || nums.length == 0) {
			return -1;
		}

		int start = 0;
		int end = nums.length - 1;

		while (start <= end) {

			int mid = (start + end) / 2;
			if (target == nums[mid]) {
				return mid;
			}

			if (nums[start] <= nums[mid]) { // array[start...mid] is sorted

				if (nums[start] <= target && target <= nums[mid]) { 
					end = mid - 1;
				} else {
					start = mid + 1;
				}
			} else { // array[mid...end] is sorted
				if (nums[mid] <= target && target <= nums[end]) { 
					start = mid + 1;
				} else {
					end = mid - 1;
				}
			}
		}

		return -1;
    }
    
	public static void main(String[] args) {
		{
			int array[] = { 56, 58, 67, 76, 21, 32, 37, 40, 45, 49 };
			findElementUsingBinarySearch(array, 45);
		}
	}
}
