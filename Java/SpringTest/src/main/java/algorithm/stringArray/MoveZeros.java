package algorithm.stringArray;

import algorithm.Utils;

/*
 * Given an array of 0s and 1s, find the position of 0 to be replaced with 1
 * to get longest continuous sequence of 1s. Expected time complexity is
 * O(n) and auxiliary space is O(1).
 * 
 * Input: 
	   arr[] =  {1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1}
	Output:
	  Index 9
	Assuming array index starts from 0, replacing 0 with 1 at index 9 causes
	the maximum continuous sequence of 1s.
	
	Input: 
	   arr[] =  {1, 1, 1, 1, 0}
	Output:
	  Index 4
 */
public class MoveZeros {
	
	public void moveZeros(int[] arr) {
		int start = 0;
		int end = arr.length - 1;

		while (start < end) {
			while (arr[start] != 0 && start < end) {
				start++;
			}

			while (arr[end] == 0 && start < end) {
				end--;
			}
			swap(arr, start, end);
		}
	}

	public void moveZerosWithOrder(int[] arr) {
		int start = 1;
		for (int i = 0; i < arr.length; i++) {
			start = i + 1;
			while (arr[i] == 0 && start < arr.length) {
				if (arr[start] != 0) {
					swap(arr, i, start);
				} else {
					start++;
				}
			}
		}
	}

	public void pushZerosToEnd(int arr[]) {
		int count = 0; // Count of non-zero elements

		// Traverse the array. If element encountered is
		// non-zero, then replace the element at index 'count'
		// with this element
		for (int i = 0; i < arr.length; i++)
			if (arr[i] != 0)
				arr[count++] = arr[i]; // here count is
										// incremented

		// Now all non-zero elements have been shifted to
		// front and 'count' is set as index of first 0.
		// Make all elements 0 from count to end.
		while (count < arr.length)
			arr[count++] = 0;
	}

	public void swap(int arr[], int start, int end) {
		int tmp = arr[start];
		arr[start] = arr[end];
		arr[end] = tmp;
	}

	public static void main(String[] args) {
		MoveZeros ob = new MoveZeros();
		int[] arr = { 1, 9, 8, 4, 0, 0, 2, 7, 0, 6, 0 };
		int[] arr1 = { 1, 9, 8, 4, 0, 0, 2, 7, 0, 6, 0 };
		int[] arr2 = { 1, 9, 8, 4, 0, 0, 2, 7, 0, 6, 0 };
		ob.moveZeros(arr);
		ob.moveZerosWithOrder(arr1);
		ob.pushZerosToEnd(arr2);
		Utils.printArray(arr);
		Utils.printArray(arr1);
		Utils.printArray(arr2);
	}
}
