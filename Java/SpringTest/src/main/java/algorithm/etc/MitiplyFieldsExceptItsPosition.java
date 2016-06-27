package algorithm.etc;

import java.util.Arrays;

/*
 * Given an array of integers, modify each position to contin multiplication
 * of values in all other locations
 * 
 * EX)
 * input  { 1, 2, 3, 4 }
 * output [24, 12, 8, 6] 
 * 
 * Analysis
 * Basically you are making (a * b * c * d) / a, (a * b * c * d)b, .... 
 */
public class MitiplyFieldsExceptItsPosition {

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
			resultArr[i] = total/input[i];
		}

		System.out.println(Arrays.toString(resultArr));
	}

	public static void main(String args[]) {
		int a[] = { 1, 2, 3, 4 };
		prod(a);
		prodI(a);
	}
}
