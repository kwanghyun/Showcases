package algorithm.stringArray;

import algorithm.Utils;

/*Given two integer arrays of same size, “arr[]” and “index[]”, 
 * reorder elements in “arr[]” according to given index array. 
 * It is not allowed to given array arr’s length.

Example:

Input:  arr[]   = [10, 11, 12];
        index[] = [1, 0, 2];
Output: arr[]   = [11, 10, 12]
        index[] = [0,  1,  2] 

Input:  arr[]   = [50, 40, 70, 60, 90]
        index[] = [3,  0,  4,  1,  2]
Output: arr[]   = [40, 60, 90, 50, 70]
        index[] = [0,  1,  2,  3,   4] 
*/
public class ReorderArrayByIndexes {

	public void sort(int[] arr, int[] indexes) {
		for (int i = 0; i < arr.length; i++) {
			while (indexes[i] != i) {
				int curr_idx = indexes[i];
				arr[i] = arr[curr_idx];
				arr[curr_idx] = arr[i];

				indexes[i] = indexes[curr_idx];
				indexes[curr_idx] = curr_idx;
			}
		}
		Utils.printArray(arr);
		Utils.printArray(indexes);
	}

	public static void main(String[] args) {
		// int[] arr = { 50, 40, 70, 60, 90 };
		// int[] idxes = { 3, 0, 4, 1, 2 };
		// int[] arr = { 40, 60, 90, 50, 70 };
		// int[] idxes = { 0, 1, 2, 3, 4 };
		int[] arr = { 10, 11, 12 };
		int[] idxes = { 1, 0, 2 };
		ReorderArrayByIndexes ob = new ReorderArrayByIndexes();
		ob.sort(arr, idxes);
		Utils.printArray(arr);
	}
}
