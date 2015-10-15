package algorithm.stringArray;

import java.util.Arrays;

/*
 Given two sorted integer arrays A and B, merge B into A as one sorted array.
 Note: You may assume that A has enough space to hold additional elements from
 B. The number of elements initialized in A and B are m and n respectively.

 The key to solve this problem is moving element of A and B backwards. If B has some
 elements left after A is done, also need to handle that case.
 The takeaway message from this problem is that the loop condition. This kind of
 condition is also used for merging two sorted linked list.
 */
public class MergeSortedArray {

	public int[] merge(int arr1[], int idx1, int arr2[], int idx2) {

		while (idx1 > 0 && idx2 > 0) {

			if (arr1[idx1 - 1] > arr2[idx2 - 1]) {
				arr1[idx1 + idx2 - 1] = arr1[idx1 - 1];
				idx1--;
			} else {
				arr1[idx1 + idx2 - 1] = arr2[idx2 - 1];
				idx2--;
			}
		}

		/*
		 * Left over of arr1 is already sorted and smaller than arr2, no need to
		 * loop arr1
		 */
		while (idx2 > 0) {
			arr1[idx1 + idx2 - 1] = arr2[idx2 - 1];
			idx2--;
		}
		return arr1;
	}

	public int[] merge2(int arr1[], int idx1, int arr2[], int idx2) {

		while (idx1 >= 0 && idx2 >= 0) {

			if (arr1[idx1] > arr2[idx2]) {
				arr1[idx1 + idx2 + 1] = arr1[idx1];
				idx1--;
			} else {
				arr1[idx1 + idx2 + 1] = arr2[idx2];
				idx2--;
			}
		}

		while (idx2 >= 0) {
			arr1[idx1 + idx2] = arr2[idx2];
			idx2--;
		}
		return arr1;
	}

	public static void main(String[] args) {
		MergeSortedArray obj = new MergeSortedArray();
		int[] arr1 = new int[10];
		int[] temp = { 1, 2, 3, 4, 5 };
		System.arraycopy(temp, 0, arr1, 0, 5);
		System.out.println(Arrays.toString(arr1));

		int[] arr2 = { 6, 7, 8, 9, 10 };
		// System.out.println(Arrays.toString(obj
		// .merge(arr1, 5, arr2, arr2.length)));
		System.out.println(Arrays.toString(obj.merge2(arr1, 4, arr2,
				arr2.length - 1)));
	}

}
