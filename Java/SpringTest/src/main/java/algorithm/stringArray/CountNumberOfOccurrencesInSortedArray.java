package algorithm.stringArray;

/**
 * Count the number of occurrences in a sorted array Given a sorted array arr[]
 * and a number x, write a function that counts the occurrences of x in arr[].
 * Expected time complexity is O(Logn)
 */
public class CountNumberOfOccurrencesInSortedArray {
	// * Modified binary search for counting number of occurrences of 'x' in a
	// sorted array
	public static int count(int[] array, int x) {

		int first = firstOccurrence(array, x);
		int last = lastOccurrence(array, x);

		if (first == -1) {
			return 0;
		} else {
			return (last - first + 1);
		}
	}

	// * Method for finding first occurrence
	public static int firstOccurrence(int[] array, int x) {

		int start = 0, end = array.length - 1, result = -1;
		while (start <= end) {
			int mid = (start + end) / 2;
			System.out.println("mid : " + mid);
			if (array[mid] > x) {
				end = mid - 1;

			} else if (array[mid] < x) {
				start = mid + 1;

			} else if (array[mid] == x) {
				result = mid;
				end = mid - 1;
			}
		}
		return result;
	}

	// * Method for finding last occurrence
	public static int lastOccurrence(int[] array, int x) {

		int start = 0, end = array.length - 1, result = -1;
		while (start <= end) {
			int mid = (start + end) / 2;

			if (array[mid] > x) {
				end = mid - 1;

			} else if (array[mid] < x) {
				start = mid + 1;

			} else if (array[mid] == x) {
				result = mid;
				start = mid + 1;
			}
		}
		return result;
	}

	public static void main(String[] args) {
		int[] array = { 1, 1, 1, 1, 2, 2, 2, 3, 3 };
		int[] array1 = { 1, 1, 2, 2, 2, 2, 3 }; // x = 2, ans = 4
		int[] array2 = { 1, 1, 2, 2, 2, 2, 3 }; // x = 3, ans = 1
		int[] array3 = { 1, 1, 2, 2, 2, 2, 3 }; // x = 1, ans = 2
		int[] array4 = { 1, 1, 2, 2, 2, 2, 3 }; // x = 4, ans = 0
		System.out.println(count(array1, 2));
		// System.out.println(count(array1, 2));
		// System.out.println(count(array2, 3));
		// System.out.println(count(array3, 1));
		// System.out.println(count(array4, 4));

	}
}
