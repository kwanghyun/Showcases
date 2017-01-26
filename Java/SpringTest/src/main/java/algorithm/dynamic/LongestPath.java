package algorithm.dynamic;

import algorithm.Utils;

/*
 * Given an integer matrix, find the length of the longest increasing path.
 * 
 * From each cell, you can either move to four directions: left, right, up
 * or down. You may NOT move diagonally or move outside of the boundary
 */
public class LongestPath {
	int longest = 0;
	// down, right, up, left
	int[] cMoves = { 0, 1, 0, -1 };
	int[] rMoves = { 1, 0, -1, 0 };

	public int longestIncreasingPathII(int[][] matrix) {
		if (matrix == null || matrix.length == 0)
			return 0;

		int max = 0;
		for (int r = 0; r < matrix.length; r++) {
			for (int c = 0; c < matrix[0].length; c++) {
				max = Math.max(max, dfsII(matrix, r, c));
			}
		}
		return max + 1;
	}

	public int dfsII(int[][] m, int r, int c) {
		int max = 0;
		for (int i = 0; i < cMoves.length; i++) {
			int n_c = c + cMoves[i];
			int n_r = r + rMoves[i];
			if (isSafe(m, n_r, n_c) && m[r][c] < m[n_r][n_c]) {
				max = Math.max(max, 1 + dfsII(m, n_r, n_c));
			}
		}
		return max;
	}

	public int longestIncreasingPathIIDP(int[][] matrix) {
		
		if (matrix == null || matrix.length == 0)
			return 0;
		
		int[][] dp = new int[matrix.length][matrix[0].length];
		int largest = 0;
		for (int r = 0; r < matrix.length; r++) {
			for (int c = 0; c < matrix[0].length; c++) {
				largest = Math.max(largest, dfsIIDP(matrix, r, c, dp));
			}
		}
//		Utils.printMetrix(dp);
		return largest + 1;
	}

	public int dfsIIDP(int[][] m, int r, int c, int[][] dp) {

		if (dp[r][c] != 0) {
//			System.out.println("HIT : r = " + r + ", c = " + c + ", val = " + dp[r][c]);
			return dp[r][c];
		}

		for (int i = 0; i < cMoves.length; i++) {
			int n_r = r + rMoves[i];
			int n_c = c + cMoves[i];
			if (isSafe(m, n_r, n_c) && m[r][c] < m[n_r][n_c]) {
				dp[r][c] = Math.max(dp[r][c], 1 + dfsIIDP(m, n_r, n_c, dp));
			}
		}
//		Utils.printMetrix(dp);
		return dp[r][c];
	}

	public boolean isSafe(int[][] m, int r, int c) {
		if (c < 0 || r < 0 || m.length <= r || m[0].length <= c)
			return false;
		return true;
	}

	public int longestIncreasingPath(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
			return 0;

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				dfs(matrix, i, j, 1);
			}
		}

		return longest;
	}

	public int longestIncreasingPathICS(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
			return 0;

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				dfsCS(matrix, i, j, 1, 0);
			}
		}

		return longestI;
	}

	int longestI = 0;

	public void dfsCS(int[][] matrix, int r, int c, int len, int callstack) {

		for (int i = 0; i < cMoves.length; i++) {
			int x = c + cMoves[i];
			int y = r + rMoves[i];
			if (isSafe(matrix, y, x) && matrix[r][c] < matrix[y][x]) {
				longestI = Math.max(longest, len + 1);
				Utils.printCS(callstack, " (+) y = " + y + ", x = " + x + ", len = " + (len + 1));
				dfsCS(matrix, y, x, len + 1, callstack + 1);
				Utils.printCS(callstack, " (-) y = " + y + ", x = " + x + ", len = " + len);
			} else {
				Utils.printCS(callstack, " (invalid) y = " + y + ", x = " + x + ", len = " + len);
			}
		}
		Utils.printCsEOR(callstack);
	}

	public void dfs(int[][] matrix, int i, int j, int len) {

		if (i - 1 >= 0 && matrix[i - 1][j] > matrix[i][j]) {
			longest = Math.max(longest, len + 1);
			dfs(matrix, i - 1, j, len + 1);
		}

		if (i + 1 < matrix.length && matrix[i + 1][j] > matrix[i][j]) {
			longest = Math.max(longest, len + 1);
			dfs(matrix, i + 1, j, len + 1);
		}

		if (j - 1 >= 0 && matrix[i][j - 1] > matrix[i][j]) {
			longest = Math.max(longest, len + 1);
			dfs(matrix, i, j - 1, len + 1);
		}

		if (j + 1 < matrix[0].length && matrix[i][j + 1] > matrix[i][j]) {
			longest = Math.max(longest, len + 1);
			dfs(matrix, i, j + 1, len + 1);
		}
	}

	int[] dx = { -1, 1, 0, 0 };
	int[] dy = { 0, 0, -1, 1 };

	public int longestIncreasingPathDP(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
			return 0;

		int[][] dp = new int[matrix.length][matrix[0].length];
		int max = 0;

		for (int r = 0; r < matrix.length; r++) {
			for (int c = 0; c < matrix[0].length; c++) {
				max = Math.max(max, dfs(matrix, r, c, dp));
			}
		}

		return max;
	}

	public int dfs(int[][] matrix, int r, int c, int[][] dp) {
		if (dp[r][c] != 0) {
			System.out.println("HIT : r = " + r + ", c = " + c + ", val = " + dp[r][c]);
			return dp[r][c];
		}

		for (int i = 0; i < 4; i++) {
			int nr = r + dx[i];
			int nc = c + dy[i];

			if (nr >= 0 && nc >= 0 && nr < matrix.length && nc < matrix[0].length && matrix[nr][nc] > matrix[r][c]) {
				dp[r][c] = Math.max(dp[r][c], dfs(matrix, nr, nc, dp));
			}
		}

		return 1 + dp[r][c];
	}

	public static void main(String[] args) {
		// int[][] matrix = { { 1, 2 }, { 4, 3 } };
		// int[][] matrix = { { 1, 2, 9 }, { 5, 3, 8 }, { 4, 6, 7 } };
		// int[][] matrix = { { 1, 2, 9, 10 }, { 5, 3, 8, 11 }, { 4, 6, 7, 12 }
		// };

		int[][] matrix = { { 9, 9, 4 }, { 6, 6, 8 }, { 2, 1, 1 } };
		Utils.printMetrix(matrix);
		LongestPath ob = new LongestPath();
		System.out.println("-------------- longestIncreasingPath --------------");
		System.out.println(ob.longestIncreasingPath(matrix));
		// System.out.println("-------------- longestIncreasingPathICS
		// --------------");
		// System.out.println(ob.longestIncreasingPathICS(matrix));
		System.out.println("-------------- longestIncreasingPathDP --------------");
		System.out.println(ob.longestIncreasingPathDP(matrix));
		System.out.println("-------------- longestIncreasingPathII --------------");
		System.out.println(ob.longestIncreasingPathII(matrix));
		System.out.println("-------------- longestIncreasingPathIIDP --------------");
		System.out.println(ob.longestIncreasingPathIIDP(matrix));
		Utils.printMetrix(matrix);
	}
}
