package algorithm.trees;

public class BinarySearch {

	int iterBinarySearch(int[] array, int target) throws Exception {
		int lower = 0, upper = array.length - 1;
		int center, range;

		if (lower > upper) {
			throw new Exception("Limits reversed");
		}
		while (true) {
			range = upper - lower;
			if (range == 0 && array[lower] != target) {
				throw new Exception("Element not in array");
			}
			if (array[lower] > array[upper]) {
				throw new Exception("Array not sorted");
			}
			center = ((range) / 2) + lower;
			if (target == array[center]) {
				return center;
			} else if (target < array[center]) {
				upper = center - 1;
			} else {
				lower = center + 1;
			}
		}
	}
}
