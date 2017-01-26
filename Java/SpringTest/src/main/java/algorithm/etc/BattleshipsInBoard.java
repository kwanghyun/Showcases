package algorithm.etc;

import java.util.*;

import algorithm.Utils;
import algorithm.linkedlist.Stack;

/*
 * Given an 2D board, count how many different battleships are in it. The
 * battleships are represented with 'X's, empty slots are represented with
 * '.'s. You may assume the following rules:
 * 
 * You receive a valid board, made of only battleships or empty slots.
 * Battleships can only be placed horizontally or vertically. In other
 * words, they can only be made of the shape 1xN (1 row, N columns) or Nx1
 * (N rows, 1 column), where N can be of any size. At least one horizontal
 * or vertical cell separates between two battleships - there are no
 * adjacent battleships.
 * 
 * Example: 
	X..X
	...X
	...X
 * 
 * In the above board there are 2 battleships. 
 * 
 * Invalid Example:
 * 
	...X
	XXXX
	...X
 * 
 * This is not a valid board - as battleships will always have a cell
 * separating between them.
 */

public class BattleshipsInBoard {

	class QNode {
		int r = 0;
		int c = 0;

		public QNode(int r, int c) {
			this.r = r;
			this.c = c;
		}

		@Override
		public String toString() {
			return "QNode [r=" + r + ", c=" + c + "]";
		}
	}

	public int countBattleships(char[][] board) {
		int count = 0;
		boolean[][] visited = new boolean[board.length][board[0].length];

		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				if (board[r][c] == 'X' && visited[r][c] != true) {
					count++;
					bfs(board, r, c, visited);
					for (int i = 0; i < visited.length; i++) {
						System.out.println(Arrays.toString(visited[i]));
					}
					System.out.println("");
				}
			}
		}

		return count;
	}

	int[][] moves = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };

	public void bfs(char[][] board, int row, int col, boolean visited[][]) {
		Queue<QNode> q = new LinkedList<>();
		q.offer(new QNode(row, col));

		while (!q.isEmpty()) {
			QNode pos = q.poll();
			int or = pos.r;
			int oc = pos.c;
			System.out.println("pos = " + pos + ", or = " + or + ", oc = " + oc);
			visited[or][oc] = true;

			for (int i = 0; i < moves.length; i++) {
				int r = or + moves[i][0];
				int c = oc + moves[i][1];
				if (isSafe(board, r, c) && board[r][c] == 'X' && !visited[r][c]) {
					System.out.println("r = " + r + ", c = " + c);
//					visited[row][col] = true;
					q.offer(new QNode(r, c));
				}
			}
		}
	}

	private boolean isSafe(char[][] board, int r, int c) {
		if (r < 0 || c < 0 || r >= board.length || c >= board[0].length) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		char[][] board = { "X..X".toCharArray(), "...X".toCharArray(), "...X".toCharArray() };
		for (int i = 0; i < board.length; i++) {
			System.out.println(Arrays.toString(board[i]));
		}
		BattleshipsInBoard ob = new BattleshipsInBoard();
		System.out.println(ob.countBattleships(board));
	}
}
