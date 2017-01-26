package algorithm.etc;

import java.util.Arrays;

/*
 * Radix Sort 
 * The lower bound for Comparison based sorting algorithm (Merge
 * Sort, Heap Sort, Quick-Sort .. etc) is Ω(nLogn), i.e., they cannot do
 * better than nLogn.
 * 
 * Counting sort is a linear time sorting algorithm that sort in O(n+k) time
 * when elements are in range from 1 to k.
 * 
 * What if the elements are in range from 1 to n2? We can’t use counting
 * sort because counting sort will take O(n2) which is worse than comparison
 * based sorting algorithms. Can we sort such an array in linear time? Radix
 * Sort is the answer. The idea of Radix Sort is to do digit by digit sort
 * starting from least significant digit to most significant digit. Radix
 * sort uses counting sort as a subroutine to sort.
 * 
 * The Radix Sort Algorithm 1) Do following for each digit i where i varies
 * from least significant digit to the most significant digit. ………….a) Sort
 * input array using counting sort (or any stable sort) according to the
 * i’th digit.
 */
public class RadixSort {
	// A utility function to get maximum value in arr[]
	static int getMax(int arr[], int n) {
		int mx = arr[0];
		for (int i = 1; i < n; i++)
			if (arr[i] > mx)
				mx = arr[i];
		return mx;
	}

	// A function to do counting sort of arr[] according to
	// the digit represented by exp.
	static void countSort(int arr[], int n, int curDigit) {
		int output[] = new int[n]; // output array
		int i;
		int count[] = new int[10];
		Arrays.fill(count, 0);

		// Store count of occurrences in count[]
		for (i = 0; i < n; i++)
			count[(arr[i] / curDigit) % 10]++;

		// Change count[i] so that count[i] now contains
		// actual position of this digit in output[]
		for (i = 1; i < 10; i++)
			count[i] += count[i - 1];

		// Build the output array
		for (i = n - 1; i >= 0; i--) {
			output[count[(arr[i] / curDigit) % 10] - 1] = arr[i];
			count[(arr[i] / curDigit) % 10]--;
		}

		// Copy the output array to arr[], so that arr[] now
		// contains sorted numbers according to curent digit
		for (i = 0; i < n; i++)
			arr[i] = output[i];
	}

	// The main function to that sorts arr[] of size n using
	// Radix Sort
	static void radixsort(int arr[], int n) {
		// Find the maximum number to know number of digits
		int m = getMax(arr, n);

		// Do counting sort for every digit. Note that instead
		// of passing digit number, exp is passed. exp is 10^i
		// where i is current digit number
		for (int currDigit = 1; m / currDigit > 0; currDigit *= 10)
			countSort(arr, n, currDigit);
	}

	// A utility function to print an array
	static void print(int arr[], int n) {
		for (int i = 0; i < n; i++)
			System.out.print(arr[i] + " ");
	}

	/* Driver function to check for above function */
	public static void main(String[] args) {
		int arr[] = { 170, 45, 75, 90, 802, 24, 2, 66 };
		int n = arr.length;
		radixsort(arr, n);
		print(arr, n);
	}
}
