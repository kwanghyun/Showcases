package algorithm.dynamic;

import algorithm.Utils;

/*
 * Given a grid of m*n size. Each block in grid has some amount of gold.
 * 
 * We start from first column of the grid(any row) and we can move in 3
 * direction - right, right-up and right-down.
 * 
 * What is the maximum amount of gold we can collect from the grid.
 */
public class MaximumCoins {

	public int getMaxCoins(int[][] grid) {
		int maxCoins = 0;
		for (int r = 0; r < grid.length; r++) {
			maxCoins = Math.max(maxCoins, getMaxCoins(grid, r, 0));
		}
		return maxCoins;
	}

	public int getMaxCoins(int[][] grid, int r, int c) {

		if (r < grid.length && c < grid[0].length && r >= 0) {
			int ru = grid[r][c] + getMaxCoins(grid, r + 1, c + 1);
			int ri = grid[r][c] + getMaxCoins(grid, r, c + 1);
			int rd = grid[r][c] + getMaxCoins(grid, r - 1, c + 1);
			int tmp = Math.max(ru, ri);
			return Math.max(tmp, rd);
		}

		return 0;
	}

	public int getMaxCoinsDP(int[][] grid) {
		int[][] dp = new int[grid.length][grid.length];

		dp[0][0] = grid[0][0];

		for (int c = 1; c < dp[0].length; c++) {
			dp[0][c] = grid[0][c] + dp[0][c - 1];
		}

		for (int r = 1; r < dp.length; r++) {
			dp[r][0] = grid[r][0];
		}
		Utils.printMetrix(dp);

		for (int r = 1; r < dp.length; r++) {
			for (int c = 1; c < dp[0].length; c++) {
				dp[r][c] = grid[r][c] + Math.max(dp[r - 1][c - 1], dp[r][c - 1]);
			}
		}
		Utils.printMetrix(dp);

		return dp[grid.length - 1][grid[0].length - 1];
	}

	public static void main(String[] args) {
		MaximumCoins ob = new MaximumCoins();
		// int[][] grid = { { 1, 1, 0, 1 }, { 1, 0, 1, 0 }, { 0, 1, 1, 0 }, { 1,
		// 1, 1, 1 } };
		int[][] grid = { { 1, 3, 0, 2 }, { 2, 0, 1, 0 }, { 0, 1, 2, 0 }, { 4, 1, 3, 2 } };
		Utils.printMetrix(grid);
		System.out.println(" ----------- getMaxCoins() -----------");
		System.out.println(ob.getMaxCoins(grid));
		System.out.println(" ----------- getMaxCoinsDP() -----------");
		System.out.println(ob.getMaxCoinsDP(grid));
	}
}
