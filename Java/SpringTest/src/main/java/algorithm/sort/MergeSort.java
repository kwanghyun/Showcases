package algorithm.sort;

public class MergeSort {

	// Sort an array using a simple but inefficient merge sort.
	public int[] mergeSort(int[] data) {

		if (data.length < 2) {
			return data;
		}

		// Split the array into two subarrays of approx equal size.

		int mid = data.length / 2;
		int[] left = new int[mid];
		int[] right = new int[data.length - mid];

		System.arraycopy(data, 0, left, 0, left.length);
		System.arraycopy(data, mid, right, 0, right.length);

		// Sort each subarray, then merge the result.

		mergeSort(left);
		mergeSort(right);

		return merge(data, left, right);
	}

	// Merge two smaller arrays into a larger array.
	private int[] merge(int[] result, int[] left, int[] right) {
		int idx = 0;
		int lIdx = 0;
		int rIdx = 0;

		// Merge arrays while there are elements in both
		while (lIdx < left.length && rIdx < right.length) {
			if (left[lIdx] <= right[rIdx]) {
				result[idx++] = left[lIdx++];
			} else {
				result[idx++] = right[rIdx++];
			}
		}

		// Copy rest of whichever array remains
		while (lIdx < left.length)
			result[idx++] = left[lIdx++];

		while (rIdx < right.length)
			result[idx++] = right[rIdx++];

		return result;
	}
	
	public static void main(String[] args) {
		MergeSort ob = new MergeSort();
		int[] data = {4,3,5,6,2,7,1,8};
		int[] result = ob.mergeSort(data);
		for(int i : result)
			System.out.print(i);
		System.out.println("--------------------------");
		for(int i : data)
			System.out.print(i);	
		
	}
}
