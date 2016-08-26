package algorithm.dynamic;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * Given an unsorted array of integers, find the length of longest
 * increasing subsequence.
 * 
 * For example, given [10, 9, 2, 5, 3, 7, 101, 18], the longest increasing
 * subsequence is [2, 3, 7, 101]. Therefore the length is 4.
 */
public class LongestIncreasingSubsequence {
	/*
	 * Let max[i] represent the length of the longest increasing subsequence so
	 * far. If any element before i is smaller than nums[i], then max[i] =
	 * max(max[i], max[j]+1).
	 */
	public int lengthOfLIS(int[] nums) {
		if (nums == null || nums.length == 0)
			return 0;

		int[] max = new int[nums.length];
		Arrays.fill(max, 1);

		int result = 1;
		for (int r = 0; r < nums.length; r++) {
			for (int l = 0; l < r; l++) {
				if (nums[r] > nums[l]) {
					max[r] = Math.max(max[r], max[l] + 1);
				}
			}
			result = Math.max(max[r], result);
		}
		return result;
	}

	public int lengthOfLISI(int[] nums) {
		if (nums == null || nums.length == 0)
			return 0;

		ArrayList<Integer> list = new ArrayList<Integer>();

		for (int num : nums) {
			if (list.size() == 0 || num > list.get(list.size() - 1)) {
				list.add(num);
			} else {
				int i = 0;
				int j = list.size() - 1;

				while (i < j) {
					int mid = (i + j) / 2;
					if (list.get(mid) < num) {
						i = mid + 1;
					} else {
						j = mid;
					}
				}

				list.set(j, num);
			}
		}

		return list.size();
	}

	/**
	 * DP way of solving LIS
	 */
	public int longestSubsequenceWithActualSolution(int arr[]) {
		int T[] = new int[arr.length];
		int actualSolution[] = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			T[i] = 1;
			actualSolution[i] = i;
		}

		for (int i = 1; i < arr.length; i++) {
			for (int j = 0; j < i; j++) {
				if (arr[i] > arr[j]) {
					if (T[j] + 1 > T[i]) {
						T[i] = T[j] + 1;
						// set the actualSolution to point to guy before me
						actualSolution[i] = j;
					}
				}
			}
		}

		// find the index of max number in T
		int maxIndex = 0;
		for (int i = 0; i < T.length; i++) {
			if (T[i] > T[maxIndex]) {
				maxIndex = i;
			}
		}

		// lets print the actual solution
		int t = maxIndex;
		int newT = maxIndex;
		do {
			t = newT;
			System.out.print(arr[t] + " ");
			newT = actualSolution[t];
		} while (t != newT);
		System.out.println();

		return T[maxIndex];
	}
}
