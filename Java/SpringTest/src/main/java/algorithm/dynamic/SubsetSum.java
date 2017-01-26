package algorithm.dynamic;

/*
 * Given a set of non-negative integers, and a value sum, determine if there
 * is a subset of the given set with sum equal to given sum.
 * 
 * Examples: set[] = {3, 34, 4, 12, 5, 2}, sum = 9 Output: True //There is a
 * subset (4, 5) with sum 9.
 */
public class SubsetSum {
	public boolean subsetSum(int arr[], int sum) {

		boolean T[][] = new boolean[arr.length + 1][sum + 1];
		for (int i = 0; i <= arr.length; i++) {
			T[i][0] = true;
		}

		for (int r = 1; r <= arr.length; r++) {
			for (int c = 1; c <= sum; c++) {
				if (c - arr[r - 1] >= 0) {
					T[r][c] = T[r - 1][c] || T[r - 1][c - arr[r - 1]];
				} else {
					T[r][c] = T[r - 1][c];
				}
			}
		}
		return T[arr.length][sum];
	}

	public static void main(String args[]) {
		SubsetSum ss = new SubsetSum();
		int arr[] = { 1, 3, 5, 5, 2, 1, 1, 6 };

		int arr1[] = { 2, 3, 7, 8 };
		System.out.print(ss.subsetSum(arr1, 11));

	}
}
