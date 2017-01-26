package algorithm.stringArray;

import java.util.Arrays;

/*
Given arrival and departure times of all trains that reach a railway station, find the minimum number of platforms required for the railway station so that no train waits.
We are given two arrays which represent arrival and departure times of trains that stop

Examples:

Input:  arr[]  = {9:00,  9:40, 9:50,  11:00, 15:00, 18:00}
        dep[]  = {9:10, 12:00, 11:20, 11:30, 19:00, 20:00}
Output: 3
There are at-most three trains at a time (time between 11:00 to 11:20)
*/
public class MinimumNumberOfPlatformsRequiredForRailwayStation {
	int findPlatform(int arr[], int dep[], int n) {
		// Sort arrival and departure arrays
		Arrays.sort(arr);
		Arrays.sort(dep);

		// plat_needed indicates number of platforms needed at a time
		int plat_needed = 1, result = 1;
		int i = 1, j = 0;

		// Similar to merge in merge sort to process all events in sorted order
		while (i < n && j < n) {

			// If next event in sorted order is arrival, increment count of
			// platforms needed

			if (arr[i] < dep[j]) {
				plat_needed++;
				i++;

				if (plat_needed > result) // Update result if needed
					result = plat_needed;
			} else {
				// Else decrement count of platforms needed
				plat_needed--;
				j++;
			}
		}
		return result;
	}
}
