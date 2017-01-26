package algorithm.dynamic;

import java.util.*;

import algorithm.Utils;

/*
 * Given a 2D grid, each cell is either a wall 'W', an enemy 'E' or empty
 * '0' (the number zero), return the maximum enemies you can kill using one
 * bomb. The bomb kills all the enemies in the same row and column from the
 * planted point until it hits the wall since the wall is too strong to be
 * destroyed. Note that you can only put the bomb at an empty cell.
 * 
 * Example:
 * 
	For the given grid
	
	0 E 0 0
	E 0 W E
	0 E 0 0
	
	return 3. (Placing a bomb at (1,1) kills 3 enemies)
 */
public class BombEnemy {

	int[][] moves = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };

	public int maxKilledEnemies(char[][] grid) {
		int max = 0;
		// for (int r = 0; r < grid.length; r++) {
		// for (int c = 0; c < grid[0].length; c++) {

		// if (grid[r][c] == '0') {
		int r = 1, c = 1;
		int count = 0;
		Utils.printMetrix(grid);
		dfs(grid, r, c, count);
		Utils.printMetrix(grid);
		// System.out.println("count = " + count);
		// max = Math.max(max, count);
		// }
		// }
		// }
		return max;
	}

	private void dfs(char[][] grid, int r, int c, int count) {
		if (isSafe(grid, r, c)) {
			if (grid[r][c] != 'W' && grid[r][c] != '#') {
				// int add = 0;
				if (grid[r][c] == 'E')
					count++;

				char temp = grid[r][c];
				grid[r][c] = '#';

				for (int i = 0; i < moves.length; i++) {
					dfs(grid, r + moves[i][0], c + moves[i][1], count);
				}
				grid[r][c] = temp;
			}
		}

		// return false;
	}

	private boolean isSafe(char[][] grid, int r, int c) {
		if (r < 0 || c < 0 || r >= grid.length || c >= grid[0].length)
			return false;
		return true;
	}

	public static void main(String[] args) {
		System.out.println(Arrays.toString("0E00".toCharArray()));
		System.out.println(Arrays.toString("E0WE".toCharArray()));
		System.out.println(Arrays.toString("0E00".toCharArray()));
		char[][] grid = { "0E00".toCharArray(), "E0WE".toCharArray(), "0E00".toCharArray() };
		BombEnemy ob = new BombEnemy();
		ob.maxKilledEnemies(grid);
	}
}
