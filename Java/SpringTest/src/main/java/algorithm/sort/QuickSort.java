package algorithm.sort;

public class QuickSort {

	// Sort an array using a simple but inefficient quicksort.
	public int[] quickSort(int[] data) {

		if (data.length < 2) {
			return data;
		}

		int pvtIdx = data.length / 2;
		int pvtVal = data[pvtIdx];
		int leftCount = 0;

		// Count how many are less than the pivot
		for (int i = 0; i < data.length; ++i) {
			if (data[i] < pvtVal)
				++leftCount;
		}

		// Allocate the arrays and create the subsets
		int[] left = new int[leftCount];
		int[] right = new int[data.length - leftCount - 1];

		int l = 0;
		int r = 0;

		for (int i = 0; i < data.length; ++i) {
			if (i == pvtIdx)
				continue;

			int val = data[i];

			if (val < pvtVal) {
				left[l++] = val;
			} else {
				right[r++] = val;
			}
		}

		// Sort the subsets
		left = quickSort(left);
		right = quickSort(right);

		// Combine the sorted arrays and the pivot back into the original array
		System.arraycopy(left, 0, data, 0, left.length);
		data[left.length] = pvtVal;
		System.arraycopy(right, 0, data, left.length + 1, right.length);

		return data;
	}
}
