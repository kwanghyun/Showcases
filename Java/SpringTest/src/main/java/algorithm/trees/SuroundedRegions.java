package algorithm.trees;

import java.util.LinkedList;
import java.util.Queue;

/*
 * Given a 2D board containing 'X' and 'O', capture all regions surrounded
 * by 'X'. A region is captured by flipping all 'O's into 'X's in that
 * surrounded region.
 * 
 * For example,
 * X X X X
 * X O O X
 * X X O X
 * X O X X
 * 
 * After running your function, the board should be:
 * X X X X
 * X X X X
 * X X X X
 * X O X X
 * 
 * Analysis
 * 
 * This problem is similar to Number of Islands. In this problem, only the
 * cells on the boarders can not be surrounded. So we can first merge those
 * O's on the boarders like in Number of Islands and replace O's with '#',
 * and then scan the board and replace all O's left (if any).
 */
public class SuroundedRegions {

	/* Depth-first Search */
	public void solve(char[][] board) {
		if (board == null || board.length == 0)
			return;

		int m = board.length;
		int n = board[0].length;

		// merge O's on left & right boarder
		for (int i = 0; i < m; i++) {
			if (board[i][0] == 'O') {
				merge(board, i, 0);
			}

			if (board[i][n - 1] == 'O') {
				merge(board, i, n - 1);
			}
		}

		// merge O's on top & bottom boarder
		for (int j = 0; j < n; j++) {
			if (board[0][j] == 'O') {
				merge(board, 0, j);
			}

			if (board[m - 1][j] == 'O') {
				merge(board, m - 1, j);
			}
		}

		// process the board
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (board[i][j] == 'O') {
					board[i][j] = 'X';
				} else if (board[i][j] == '#') {
					board[i][j] = 'O';
				}
			}
		}
	}

	public void merge(char[][] board, int i, int j) {
		if (i < 0 || i >= board.length || j < 0 || j >= board[0].length)
			return;

		if (board[i][j] != 'O')
			return;

		board[i][j] = '#';

		merge(board, i - 1, j);
		merge(board, i + 1, j);
		merge(board, i, j - 1);
		merge(board, i, j + 1);
	}

	/*
	 * This solution causes java.lang.StackOverflowError, because for a large
	 * board, too many method calls are pushed to the stack and causes the
	 * overflow.
	 * 
	 * 2. Breath-first Search
	 * 
	 * Instead we use a queue to do breath-first search.
	 */
	public class Solution {
		// use a queue to do BFS
		private Queue<Integer> queue = new LinkedList<>();

		public void solve(char[][] board) {
			if (board == null || board.length == 0)
				return;

			int m = board.length;
			int n = board[0].length;

			// merge O's on left & right boarder
			for (int i = 0; i < m; i++) {
				if (board[i][0] == 'O') {
					bfs(board, i, 0);
				}

				if (board[i][n - 1] == 'O') {
					bfs(board, i, n - 1);
				}
			}

			// merge O's on top & bottom boarder
			for (int j = 0; j < n; j++) {
				if (board[0][j] == 'O') {
					bfs(board, 0, j);
				}

				if (board[m - 1][j] == 'O') {
					bfs(board, m - 1, j);
				}
			}

			// process the board
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					if (board[i][j] == 'O') {
						board[i][j] = 'X';
					} else if (board[i][j] == '#') {
						board[i][j] = 'O';
					}
				}
			}
		}

		private void bfs(char[][] board, int i, int j) {
			int n = board[0].length;

			// fill current first and then its neighbors
			fillCell(board, i, j);

			while (!queue.isEmpty()) {
				int cur = queue.poll();
				int x = cur / n;
				int y = cur % n;

				fillCell(board, x - 1, y);
				fillCell(board, x + 1, y);
				fillCell(board, x, y - 1);
				fillCell(board, x, y + 1);
			}
		}

		private void fillCell(char[][] board, int i, int j) {
			int m = board.length;
			int n = board[0].length;
			if (i < 0 || i >= m || j < 0 || j >= n || board[i][j] != 'O')
				return;

			// add current cell is queue & then process its neighbors in bfs
			queue.offer(i * n + j);
			board[i][j] = '#';
		}
	}
}
