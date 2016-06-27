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

		int rlen = matrix.length;
		int clen = matrix[0].length;

		int[][] dp = new int[rlen][clen];

		for (int r = 0; r < rlen; r++) {
			dp[r][0] = matrix[r][0];
		}

		for (int c = 0; c < clen; c++) {
			dp[0][c] = matrix[0][c];
		}

		for (int r = 1; r < rlen; r++) {
			for (int c = 1; c < clen; c++) {
				if (matrix[r][c] == 1) {
					int min = Math.min(dp[r - 1][c], dp[r - 1][c - 1]);
					min = Math.min(min, dp[r][c - 1]);
					dp[r][c] = min + 1;
				} else {
					dp[r][c] = 0;
				}
			}
		}

		int max = 0;

		// get maximal length
		for (int r = 0; r < rlen; r++) {
			for (int c = 0; c < clen; c++) {
				if (dp[r][c] > max) {
					max = dp[r][c];
				}
			}
		}

		return max * max;
	}

	public static void main(String[] args) {
		int matrix[][] = { { 0, 1, 1, 0, 1, 1 }, { 1, 1, 0, 1, 1, 1 }, { 0, 1, 1, 1, 0, 0 }, { 1, 1, 1, 1, 0, 0 },
				{ 1, 1, 1, 1, 1, 0 }, { 0, 1, 1, 1, 0, 1 } };
		MaximalSqaure ob = new MaximalSqaure();
		System.out.println(ob.maximalSquare(matrix));
	}
}
