package algorithm.stringArray;

import algorithm.Utils;

/*
 * Search in a row wise and column wise sorted matrix Given an n x n matrix,
 * where every row and column is sorted in increasing order. Given a number
 * x, how to decide whether this x is in the matrix. The designed algorithm
 * should have linear time complexity.
	  2,  6,  7, 11,
	  3,  8, 10, 12,
	  4,  9, 11, 13,
	  5, 15, 16, 18,
 */
public class SearchElementInSortedMatrix {
	public static boolean stairSearch(int matrix[][], int n, int element) {
		if (element < matrix[0][0] || element > matrix[n - 1][n - 1])
			return false;
		int r = 0; // row
		int c = n - 1;// column
		while (r <= n - 1 && c >= 0) {
			if (matrix[r][c] < element)
				r++;
			else if (matrix[r][c] > element)
				c--;
			else
				return true;
		}
		return false;
	}

	public static void main(String args[]) {
		int[][] mat = { { 2, 6, 7, 11 }, { 3, 8, 10, 12 }, { 4, 9, 11, 13 }, { 5, 15, 16, 18 } };
		Utils.printMetrix(mat);
		System.out.println(stairSearch(mat, 4, 9));

	}

}
