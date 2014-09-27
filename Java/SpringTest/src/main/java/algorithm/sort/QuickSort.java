package algorithm.sort;

public class QuickSort {

	// Sort an array using a simple but inefficient quicksort.
	public int[] quicksortSimple(int[] data) {

		if (data.length < 2) {
			return data;
		}

		int pivotIndex = data.length / 2;
		int pivotValue = data[pivotIndex];

		int leftCount = 0;

		// Count how many are less than the pivot

		for (int i = 0; i < data.length; ++i) {
			if (data[i] < pivotValue)
				++leftCount;
		}

		// Allocate the arrays and create the subsets

		int[] left = new int[leftCount];
		int[] right = new int[data.length - leftCount - 1];

		int l = 0;
		int r = 0;

		for (int i = 0; i < data.length; ++i) {
			if (i == pivotIndex)
				continue;

			int val = data[i];

			if (val < pivotValue) {
				left[l++] = val;
			} else {
				right[r++] = val;
			}
		}

		// Sort the subsets

		left = quicksortSimple(left);
		right = quicksortSimple(right);

		// Combine the sorted arrays and the pivot back into the original array

		System.arraycopy(left, 0, data, 0, left.length);
		data[left.length] = pivotValue;
		System.arraycopy(right, 0, data, left.length + 1, right.length);

		return data;
	}
}
