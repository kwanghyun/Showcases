package algorithm.stringArray;

import java.util.Arrays;

/*
 * Given an array A of integers, find the maximum of j-i subjected to the
 * constraint of A[i] < A[j].
 * 
 *   	Input: {34, 8, 10, 3, 2, 80, 30, 33, 1}
 *     Output: 6  (j = 7, i = 1)
 *   
 *     Input: {9, 2, 3, 4, 5, 6, 7, 8, 18, 0}
 *     Output: 8 ( j = 8, i = 0)
 *   
 *     Input:  {1, 2, 3, 4, 5, 6}
 *     Output: 5  (j = 5, i = 0)
 *   
 *     Input:  {6, 5, 4, 3, 2, 1}
 *     Output: -1 
 */
public class DistanceMaximizing {

	/* Method 1 (Simple but Inefficient) */
	int maxIndexDiff(int arr[], int n) {
		int maxDiff = -1;
		int i, j;

		for (i = 0; i < n; ++i) {
			for (j = n - 1; j > i; --j) {
				if (arr[j] > arr[i] && maxDiff < (j - i))
					maxDiff = j - i;
			}
		}
		return maxDiff;
	}

	/*
	 * To solve this problem, we need to get two optimum indexes of arr[]: left
	 * index i and right index j. For an element arr[i], we do not need to
	 * consider arr[i] for left index if there is an element smaller than arr[i]
	 * on left side of arr[i]. Similarly, if there is a greater element on right
	 * side of arr[j] then we do not need to consider this j for right index. So
	 * we construct two auxiliary arrays LMin[] and RMax[] such that LMin[i]
	 * holds the smallest element on left side of arr[i] including arr[i], and
	 * RMax[j] holds the greatest element on right side of arr[j] including
	 * arr[j]. After constructing these two auxiliary arrays, we traverse both
	 * of these arrays from left to right. While traversing LMin[] and RMa[] if
	 * we see that LMin[i] is greater than RMax[j], then we must move ahead in
	 * LMin[] (or do i++) because all elements on left of LMin[i] are greater
	 * than or equal to LMin[i]. Otherwise we must move ahead in RMax[j] to look
	 * for a greater j – i value.
	 */
	private static int maxIndexDiff(int[] arr) {
		int size = arr.length;
		int[] lmin = new int[size];
		int[] rmax = new int[size];
		lmin[0] = arr[0];

		for (int i = 1; i < size; i++) {
			lmin[i] = Math.min(arr[i], lmin[i - 1]);
		}
		/*[34, 8, 8, 3, 2, 2, 2, 2, 1]*/
		System.out.println(Arrays.toString(lmin));
		
		rmax[size - 1] = arr[size - 1];
		for (int j = size - 2; j >= 0; j--) {
			rmax[j] = Math.max(arr[j], rmax[j + 1]);
		}
		/*[80, 80, 80, 80, 80, 80, 33, 33, 1]*/
		System.out.println(Arrays.toString(rmax));

		int i = 0, j = 0, maxDiff = -1;
		while (j < size && i < size) {
			if (lmin[i] < rmax[j]) {
				maxDiff = Math.max(maxDiff, j - i);
				j = j + 1;
			} else
				i = i + 1;
		}

		return maxDiff;
	}

	public static void main(String[] args) {
		int arr[] = { 34, 8, 10, 3, 2, 80, 30, 33, 1 };
		int maxDiff = maxIndexDiff(arr);
		System.out.println(maxDiff);
	}
}