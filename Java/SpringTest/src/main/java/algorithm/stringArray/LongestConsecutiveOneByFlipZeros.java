package algorithm.stringArray;

import java.util.Arrays;

public class LongestConsecutiveOneByFlipZeros {

	public int[] getMaxLen(int[] arr, int m) {
		int start = 0;
		int zeroCount = 0;
		int maxLen = 0;
		int[] maxIdx = new int[m];
		int[] resultIdx = new int[m];
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == 0 && zeroCount < m) {
				maxIdx[zeroCount] = i;
				zeroCount++;
			} else {
				while (zeroCount == m) {
					int len = i - start;
					if (maxLen < len) {
						maxLen = len;
						System.arraycopy(maxIdx, 0, resultIdx, 0, m);
					}
					start++;
					if (arr[start] == 0) {
						zeroCount = 0;
					}
				}
			}
		}
		return resultIdx;
	}

	// m is maximum of number zeroes allowed to flip
	// n is size of array
	void findZeroes(int arr[], int n, int m) {
		// Left and right indexes of current window
		int left = 0, right = 0;

		// Left index and size of the widest window
		int bestL = 0, bestWindow = 0;

		// Count of zeroes in current window
		int zeroCount = 0;

		// While right boundary of current window doesn't cross
		// right end
		while (right < n) {
			// If zero count of current window is less than m,
			// widen the window toward right
			if (zeroCount <= m) {
				if (arr[right] == 0)
					zeroCount++;
				right++;
			}

			// If zero count of current window is more than m,
			// reduce the window from left
			if (zeroCount > m) {
				if (arr[left] == 0)
					zeroCount--;
				left++;
			}

			System.out.println("right = " + right + ", zeroCount = " + zeroCount + ", left = " + left
					+ ", bestWindow = " + bestWindow);
			// Updqate widest window if this window size is more
			if (right - left > bestWindow) {
				bestWindow = right - left;
				bestL = left;
			}
		}

		// Print positions of zeroes in the widest window
		for (int i = 0; i < bestWindow; i++) {
			if (arr[bestL + i] == 0)
				System.out.print(bestL + i + "  ");
		}
		System.out.println("");
	}

	public static void main(String[] args) {
		LongestConsecutiveOneByFlipZeros ob = new LongestConsecutiveOneByFlipZeros();
		int[] arr = { 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1 };
		int[] r = ob.getMaxLen(arr, 2);
		System.out.println(Arrays.toString(r));

		ob.findZeroes(arr, arr.length, 2);

		int[] arr1 = { 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1 };
		int[] r1 = ob.getMaxLen(arr1, 1);
		System.out.println(Arrays.toString(r1));

		int arr2[] = { 0, 0, 0, 1 };
		int[] r2 = ob.getMaxLen(arr2, 4);
		System.out.println(Arrays.toString(r2));
		ob.findZeroes(arr2, arr2.length, 4);
	}
}
