package algorithm.dynamic;

import java.util.Arrays;

/*
 * The demons had captured the princess (P) and imprisoned her in the
 * bottom-right corner of a dungeon. The dungeon consists of M x N rooms
 * laid out in a 2D grid. Our valiant knight (K) was initially positioned in
 * the top-left room and must fight his way through the dungeon to rescue
 * the princess.
 * 
 * The knight has an initial health point represented by a positive integer.
 * If at any point his health point drops to 0 or below, he dies
 * immediately.
 * 
 * Some of the rooms are guarded by demons, so the knight loses health
 * (negative integers) upon entering these rooms; other rooms are either
 * empty (0's) or contain magic orbs that increase the knight's health
 * (positive integers).
 * 
 * In order to reach the princess as quickly as possible, the knight decides
 * to move only rightward or downward in each step.
 * 
 * 
 * Write a function to determine the knight's minimum initial health so that
 * he is able to rescue the princess.
 * 
 * For example, given the dungeon below, the initial health of the knight
 * must be at least 7 if he follows the optimal path RIGHT-> RIGHT -> DOWN
 * -> DOWN.
 */

/*	
-2 (K)	-3			3
-5			-10		1
10			30			-5 (P)
*/

/*
 * Java Solution
 * 
 * This problem can be solved by using dynamic programming. We maintain a
 * 2-D table. h[i][j] is the minimum health value before he enters (i,j).
 * h[0][0] is the value of the answer. The left part is filling in numbers
 * to the table.
 */
public class DungeonGame {
	//WHy return 6, let's chekc video tutorial
	public int calculateMinimumHP(int[][] dungeon) {
		int row = dungeon.length;
		int col = dungeon[0].length;

		// init dp table
		int[][] dp = new int[row][col];

		dp[row - 1][col - 1] = Math.max(1 - dungeon[row - 1][col - 1], 1);

		// init last row
		for (int r = row - 2; r >= 0; r--) {
			dp[r][col - 1] = Math.max(dp[r + 1][col - 1] - dungeon[r][col - 1], 1);
		}

		// init last column
		for (int c = col - 2; c >= 0; c--) {
			dp[row - 1][c] = Math.max(dp[row - 1][c + 1] - dungeon[row - 1][c], 1);
		}

		// calculate dp table
		for (int r = row - 2; r >= 0; r--) {
			for (int c = col - 2; c >= 0; c--) {
				int down = Math.max(dp[r + 1][c] - dungeon[r][c], 1);
				int right = Math.max(dp[r][c + 1] - dungeon[r][c], 1);
				dp[r][c] = Math.min(right, down);
			}
		}

//		for (int r = row - 1; r >= 0; r--) {
//			for (int c = col - 1; c >= 0; c--) {
//				System.out.print(dp[r][c] + "  ");
//			}
//			System.out.println();
//		}
		return dp[0][0];

	}

	public static void main(String[] args) {
		// int[][] dungeon = { { -2, -3, 3 }, { -5, -10, 1 }, { 10, 30, -5 } };
		int[][] dungeon = { { -5, 30, 10 }, { 1, -10, -5 }, { 3, -3, -2 } };
		DungeonGame ob = new DungeonGame();

		System.out.println(ob.calculateMinimumHP(dungeon));
	}
}
