package algorithm.stringArray;

/*	There are two sorted arrays nums1 and nums2 of size m and n respectively.

Find the median of the two sorted arrays. 
The overall run time complexity should be O(log (m+n)).

Example 1:
nums1 = [1, 3]
nums2 = [2]

The median is 2.0
Example 2:
nums1 = [1, 2]
nums2 = [3, 4]

The median is (2 + 3)/2 = 2.5*/
public class MedianOfTwoSortedArrays {

	private static final int ERROR_INVALID_INPUT = -1; // change value of this
														// const as per your
														// specific requirement

	public int max(int a, int b) {
		if (a > b)
			return a;
		return b;
	}

	public int min(int a, int b) {
		if (a < b)
			return a;
		return b;
	}

	private double findMedian(int[] array, int startIndex, int endIndex) {
		int indexDiff = (endIndex - startIndex);
		if (indexDiff % 2 == 0) // we are looking at odd number of elements
		{
			return array[startIndex + (indexDiff / 2)];
		} else {
			return 1.0 * (array[startIndex + (indexDiff / 2)] + array[startIndex + (indexDiff / 2) + 1]) / 2;
		}
	}

	// a: 1,2,5,11,15 // b: 3 4 13 17 18
	public double findMedianSortedArrays(int[] a, int[] b, int startIndexA, int endIndexA, int startIndexB,
			int endIndexB) {

		if ((endIndexA - startIndexA < 0) || ((endIndexB - startIndexB < 0))) {
			System.out.println("Invalid Input.");
			return ERROR_INVALID_INPUT;
		}

		if ((endIndexA - startIndexA == 0) && ((endIndexB - startIndexB == 0))) {
			return (a[0] + b[0]) / 2;
		}

		if ((endIndexA - startIndexA == 1) && ((endIndexB - startIndexB == 1))) {
			return (1.0 * (max(a[startIndexA], b[startIndexB]) + min(a[endIndexA], b[endIndexB]))) / 2;
		}

		double m1 = findMedian(a, startIndexA, endIndexA);
		double m2 = findMedian(b, startIndexB, endIndexB);

		if (m2 == m1) {
			return m2;
		}

		// since m1 <= median <= m2 narrow down search by eliminating elements
		// less than m1 and elements greater than m2
		if (m1 < m2) {
			if ((endIndexA - startIndexA) % 2 == 0) // we are looking at odd
													// number of elements
			{
				startIndexA = startIndexA + (endIndexA - startIndexA) / 2;
				endIndexB = startIndexB + (endIndexB - startIndexB) / 2;
			} else {
				startIndexA = startIndexA + (endIndexA - startIndexA) / 2;
				endIndexB = startIndexB + (endIndexB - startIndexB) / 2 + 1;
			}
		}

		// since m2 <= median <= m1 narrow down search by eliminating elements
		// less than m2 and elements greater than m1
		else // m2 < m1
		{
			if ((endIndexB - startIndexB) % 2 == 0) // we are looking at odd
													// number of elements
			{
				startIndexB = startIndexB + (endIndexB - startIndexB) / 2;
				endIndexA = startIndexA + (endIndexA - startIndexA) / 2;
			} else {
				startIndexB = startIndexB + (endIndexB - startIndexB) / 2;
				endIndexA = startIndexA + (endIndexA - startIndexA) / 2 + 1;
			}
		}
		return findMedianSortedArrays(a, b, startIndexA, endIndexA, startIndexB, endIndexB);
	}

	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
		int total = nums1.length + nums2.length;
		if (total % 2 == 0) {
			return (findKth(total / 2 + 1, nums1, nums2, 0, 0) + findKth(total / 2, nums1, nums2, 0, 0)) / 2.0;
		} else {
			return findKth(total / 2 + 1, nums1, nums2, 0, 0);
		}
	}

	public int findKth(int k, int[] nums1, int[] nums2, int s1, int s2) {
		if (s1 >= nums1.length)
			return nums2[s2 + k - 1];

		if (s2 >= nums2.length)
			return nums1[s1 + k - 1];

		if (k == 1)
			return Math.min(nums1[s1], nums2[s2]);

		int m1 = s1 + k / 2 - 1;
		int m2 = s2 + k / 2 - 1;

		int mid1 = m1 < nums1.length ? nums1[m1] : Integer.MAX_VALUE;
		int mid2 = m2 < nums2.length ? nums2[m2] : Integer.MAX_VALUE;

		if (mid1 < mid2) {
			return findKth(k - k / 2, nums1, nums2, m1 + 1, s2);
		} else {
			return findKth(k - k / 2, nums1, nums2, s1, m2 + 1);
		}
	}

	public static void main(String[] args) {

		MedianOfTwoSortedArrays solution = new MedianOfTwoSortedArrays();

		System.out.println("Case 1: When arrays have odd number of elements in them.");
		int[] a = { 1, 2, 3, 4, 5 };
		int[] b = { 6, 7, 8, 9, 10 };

		System.out.println("Median: " + solution.findMedianSortedArrays(a, b, 0, a.length - 1, 0, b.length - 1));

		System.out.println("-----------------");

		System.out.println("Case 2: When arrays have even number of elements in them.");
		int[] c = { 1, 2, 99, 100 };
		int[] d = { 4, 5, 101, 102 };

		System.out.println("Median: " + solution.findMedianSortedArrays(c, d, 0, c.length - 1, 0, d.length - 1));
	}
}
