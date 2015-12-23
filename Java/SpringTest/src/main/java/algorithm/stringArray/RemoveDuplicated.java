package algorithm.stringArray;

import java.util.Arrays;

/*Given a sorted array, remove the duplicates in place such that each element appear
 only once and return the new length. Do not allocate extra space for another array,
 you must do this in place with constant memory.
 For example, given input array A = [1,1,2], your function should return length = 2,
 and A is now [1,2].*/
public class RemoveDuplicated {

	public static int[] removeDuplicates(int[] arr) {
		if (arr.length < 2)
			return arr;

		int prev = 0;
		int curr = 1;

		while (curr < arr.length) {

			if (arr[prev] == arr[curr]) {
				curr++;
			} else {
				prev++;
				arr[prev] = arr[curr];
				curr++;
			}
		}

		int[] result = Arrays.copyOf(arr, prev + 1);
		return result;
	}

	public static int countUnique(int[] arr) {
		int count = 0;
		for (int i = 0; i < arr.length - 1; i++) {
			if (arr[i] == arr[i + 1]) {
				count++;
			}
		}
		return (arr.length - count);
	}

	/*
	 * Follow up for "Remove Duplicates": What if duplicates are allowed at most
	 * twice? For example, given sorted array A = [1,1,1,2,2,3], your function
	 * should return length = 5, and A is now [1,1,2,2,3].
	 */
	public static int[] removeDuplicates2(int[] arr) {
		if (arr.length <= 2)
			return arr;

		int prev = 0;
		int curr = 1;
		int dupCount = 1;
		while (curr < arr.length) {

			if (arr[prev] == arr[curr] && dupCount <= 2) {
				curr++;
				dupCount++;
			} else {
				prev++;
				arr[prev] = arr[curr];
				curr++;
				dupCount = 1;
			}
		}

		int[] result = Arrays.copyOf(arr, prev);
		return result;
	}

	public static void main(String[] args) {
		int[] arr = { 1, 2, 2, 2, 2, 3, 3, 3, 3, 3 };
		System.out.println(countUnique(arr));
		System.out.println(Arrays.toString(removeDuplicates(arr)));
		System.out.println(Arrays.toString(removeDuplicates2(arr)));
	}
}
