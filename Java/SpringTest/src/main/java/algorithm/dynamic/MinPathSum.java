package algorithm.dynamic;

/*
 * Given a m x n grid filled with non-negative numbers, find a path from top
 * left to bottom right which minimizes the sum of all numbers along its
 * path.
 * 
 * Java Solution 1: Depth-First Search 
 * Java Solution 2: DP
 * 
 * A native solution would be depth-first search. It's time is too expensive
 * and fails the online judgement.
 * 
 * {
 * 		{ 1, 7, 9, 2 }, 
 * 		{ 8, 6, 3, 2 }, 
 * 		{ 1, 6, 7, 8 }, 
 * 		{ 2, 9, 8, 2 }
 * } 
 */
public class MinPathSum {

	public int minPathSum(int[][] grid) {
		return dfs(0, 0, grid);
	}

	public int minPathSumI(int[][] grid) {
		return dfsI(0, 0, grid);
	}

	private int dfsI(int r, int c, int[][] grid) {
		if (r == grid.length - 1 && c == grid[0].length - 1) {
			return grid[r][c];
		}

		if (r < grid.length - 1 && c < grid[0].length - 1) {
			return Math.min(grid[r][c] + dfsI(r + 1, c, grid), grid[r][c] + dfsI(r, c + 1, grid));
		}

		if (r < grid.length - 1) {
			return grid[r][c] + dfsI(r + 1, c, grid);
		}

		if (c < grid[0].length - 1) {
			return grid[r][c] + dfsI(r, c + 1, grid);
		}

		return 0;
	}

	private int dfs(int r, int c, int[][] grid) {
		if (r == grid.length - 1 && c == grid[0].length - 1) {
			return grid[r][c];
		}

		if (r < grid.length - 1 && c < grid[0].length - 1) {
			int r1 = grid[r][c] + dfs(r + 1, c, grid);
			int r2 = grid[r][c] + dfs(r, c + 1, grid);
			return Math.min(r1, r2);
		}

		if (r < grid.length - 1) {
			return grid[r][c] + dfs(r + 1, c, grid);
		}

		if (c < grid[0].length - 1) {
			return grid[r][c] + dfs(r, c + 1, grid);
		}

		return 0;
	}

	public int minPathSumDP(int[][] grid) {
		if (grid == null || grid.length == 0)
			return 0;

		int row = grid.length;
		int col = grid[0].length;

		int[][] dp = new int[row][col];
		dp[0][0] = grid[0][0];

		// initialize top row
		for (int c = 1; c < col; c++) {
			dp[0][c] = dp[0][c - 1] + grid[0][c];
		}

		// initialize left column
		for (int r = 1; r < row; r++) {
			dp[r][0] = dp[r - 1][0] + grid[r][0];
		}

		// fill up the dp table
		for (int r = 1; r < row; r++) {
			for (int c = 1; c < col; c++) {
				dp[r][c] = Math.min(dp[r][c - 1], dp[r - 1][c]) + grid[r][c];
			}
		}

		return dp[row - 1][col - 1];
	}

	public static void main(String[] args) {
		int[][] grid = { { 1, 7, 9, 2 }, { 8, 6, 3, 2 }, { 1, 6, 7, 8 }, { 2, 9, 8, 2 } };
		MinPathSum ob = new MinPathSum();
		System.out.println(ob.minPathSum(grid));
		System.out.println(ob.minPathSumI(grid));
		System.out.println(ob.minPathSumDP(grid));
	}
}
