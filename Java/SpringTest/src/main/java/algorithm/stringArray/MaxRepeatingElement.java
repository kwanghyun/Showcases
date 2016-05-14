package algorithm.stringArray;

/*
 * A better approach is to create a count array of size k and initialize all
 * elements of count[] as 0. Iterate through all elements of input array,
 * and for every element arr[i], increment count[arr[i]]. Finally, iterate
 * through count[] and return the index with maximum value. This approach
 * takes O(n) time, but requires O(k) space.
 * 
 * Following is the O(n) time and O(1) extra space approach. Let us
 * understand the approach with a simple example where arr[] = {2, 3, 3, 5,
 * 3, 4, 1, 7}, k = 8, n = 8 (number of elements in arr[]).
 * 
 * 1) Iterate though input array arr[], for every element arr[i], increment
 * arr[arr[i]%k] by k (arr[] becomes {2, 11, 11, 29, 11, 12, 1, 15 })
 * 
 * 2) Find the maximum value in the modified array (maximum value is 29).
 * Index of the maximum value is the maximum repeating element (index of 29
 * is 3).
 * 
 * 3) If we want to get the original array back, we can iterate through the
 * array one more time and do arr[i] = arr[i] % k where i varies from 0 to
 * n-1.
 * 
 * How does the above algorithm work? Since we use arr[i]%k as index and add
 * value k at the index arr[i]%k, the index which is equal to maximum
 * repeating element will have the maximum value in the end. Note that k is
 * added maximum number of times at the index equal to maximum repeating
 * element and all array elements are smaller than k.
 */
public class MaxRepeatingElement {

	// Returns maximum repeating element in arr[0..n-1].
	// The array elements are in range from 0 to k-1
	static int maxRepeating(int arr[], int n, int k) {
		// Iterate though input array, for every element
		// arr[i], increment arr[arr[i]%k] by k
		for (int i = 0; i < n; i++)
			arr[(arr[i] % k)] += k;

		// Find index of the maximum repeating element
		int max = arr[0], result = 0;
		for (int i = 1; i < n; i++) {
			if (arr[i] > max) {
				max = arr[i];
				result = i;
			}
		}

		/*
		 * Uncomment this code to get the original array back 
		 * for (int i = 0; i<n; i++) 
		 * 		arr[i] = arr[i]%k;
		 */

		// Return index of the maximum element
		return result;
	}

	public static void main(String[] args) {
		int arr[] = { 2, 3, 3, 5, 3, 2, 2, 7 };
		int n = arr.length;
		int k = 8;
		System.out.println("Maximum repeating element is: " + maxRepeating(arr, n, k));
	}

}