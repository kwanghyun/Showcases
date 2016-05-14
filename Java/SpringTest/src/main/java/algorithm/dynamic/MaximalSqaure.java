package algorithm.dynamic;

/*
 * Given a 2D binary matrix filled with 0's and 1's, find the largest square
 * containing all 1's and return its area.
 * 
 * For example, given the following matrix:
 * 
 * 1101 
 * 1101 
 * 1111 
 * 
 * Return 4.
 * 
 * Analysis
 * 
 * This problem can be solved by dynamic programming. The changing condition
 * is: t[i][j] = min(t[i][j-1], t[i-1][j], t[i-1][j-1]) + 1. It means the
 * square formed before this point.
 */
public class MaximalSqaure {
	public int maximalSquare(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
			return 0;
	 
		int r = matrix.length;
		int c = matrix[0].length;
	 
		int[][] t = new int[r][c];
	 
		//top row
		for (int rIdx = 0; rIdx < r; rIdx++) {
			t[rIdx][0] = matrix[rIdx][0];
		}
	 
		//left column
		for (int cIdx = 0; cIdx < c; cIdx++) {
			t[0][cIdx] = matrix[0][cIdx];
		}
	 
		//cells inside
		for (int rIdx = 1; rIdx < r; rIdx++) {
			for (int cIdx = 1; cIdx < c; cIdx++) {
				if (matrix[rIdx][cIdx] == 1) {
					int min = Math.min(t[rIdx - 1][cIdx], t[rIdx - 1][cIdx - 1]);
					min = Math.min(min,t[rIdx][cIdx - 1]);
					t[rIdx][cIdx] = min + 1;
				} else {
					t[rIdx][cIdx] = 0;
				}
			}
		}
	 
		int max = 0;
		
		//get maximal length
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < c; j++) {
				if (t[i][j] > max) {
					max = t[i][j];
				}
			}
		}
	 
		return max * max;
	}

	public static void main(String[] args) {
		int matrix[][] = { 
				{ 0, 1, 1, 0, 1, 1 }, 
				{ 1, 1, 0, 1, 1, 1 }, 
				{ 0, 1, 1, 1, 0, 0 }, 
				{ 1, 1, 1, 1, 0, 0 },
				{ 1, 1, 1, 1, 1, 0 }, 
				{ 0, 1, 1, 1, 0, 1 } };
		MaximalSqaure ob = new MaximalSqaure();
		System.out.println(ob.maximalSquare(matrix));
	}
}
