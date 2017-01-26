package algorithm.etc;

import java.util.Arrays;

/*
 * Given an array of n integers where n > 1, nums, return an array output
 * such that output[i] is equal to the product of all the elements of nums
 * except nums[i].
 * 
 * Solve it without division and in O(n).
 * 
 * For example, given [1,2,3,4], return [24,12,8,6].
 * 
 * Follow up: Could you solve it with constant space complexity? (Note: The
 * output array does not count as extra space for the purpose of space
 * complexity analysis.)
 */
public class MitiplyFieldsExceptItsPosition {

	public int[] productExceptSelf(int[] nums) {
		int n = nums.length;
		int[] leftArr = new int[n];
		int left = 1;
		
		// Traverse from the left
		for (int i = 0; i < n; ++i) {
			leftArr[i] = left;
			left = left * nums[i];
		}

		// Traverse from the right
		int right = 1;
		int[] prodArray = leftArr;
		for (int i = n - 1; i >= 0; --i) {
			prodArray[i] = right * prodArray[i];
			right = right * nums[i];
		}
		return prodArray;
	}

	public static void prod(int[] input) {
		int n = input.length;
		int[] leftArr = new int[n];
		int left = 1;
		// Traverse from the left
		for (int i = 0; i < n; ++i) {
			leftArr[i] = left;
			left = left * input[i];
		}

		// Traverse from the right
		int right = 1;
		int[] prodArray = leftArr;
		for (int i = n - 1; i >= 0; --i) {
			prodArray[i] = right * prodArray[i];
			right = right * input[i];
		}
		System.out.println(Arrays.toString(prodArray));
	}

	public static void prodI(int[] input) {
		int len = input.length;
		int[] resultArr = new int[len];
		int total = 1;

		for (int num : input) {
			total *= num;
		}
		for (int i = 0; i < len; i++) {
			resultArr[i] = total / input[i];
		}

		System.out.println(Arrays.toString(resultArr));
	}

	public static void main(String args[]) {
		int a[] = { 1, 2, 3, 4 };
		prod(a);
		prodI(a);
	}
}
