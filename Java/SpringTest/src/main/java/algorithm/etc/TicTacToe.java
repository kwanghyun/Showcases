package algorithm.etc;

public class TicTacToe {
	/** Initialize your data structure here. */
	int[][] board;

	public TicTacToe(int n) {
		board = new int[n][n];
	}

	/**
	 * Player {player} makes a move at ({row}, {col}).
	 * 
	 * @param row
	 *            The row of the board.
	 * @param col
	 *            The column of the board.
	 * @param player
	 *            The player, can be either 1 or 2.
	 * @return The current winning condition, can be either: 0: No one wins. 1:
	 *         Player 1 wins. 2: Player 2 wins.
	 */
	public int move(int row, int col, int player) {
		board[row][col] = player;
		return checkWinnerCondition(row, col);
	}

	public int checkWinnerCondition(int r, int c) {
		int p1Count = 0;
		int p2Count = 0;
		for (int i = 0; i < board.length; i++) {
			if (board[r][i] == 1) {
				p1Count++;
			}
			if (board[r][i] == 2)
				p2Count++;
		}
		if (p1Count == board.length)
			return 1;
		else if (p2Count == board.length)
			return 2;

		p1Count = 0;
		p2Count = 0;
		for (int i = 0; i < board.length; i++) {
			if (board[i][c] == 1) {
				p1Count++;
			}
			if (board[i][c] == 2)
				p2Count++;
		}

		p1Count = 0;
		p2Count = 0;
		if (p1Count == board.length)
			return 1;
		else if (p2Count == board.length)
			return 2;

		for (int i = board.length - 1, j = board.length - 1; i >= 0 && j >= 0; i--, j--) {
			if (board[i][j] == 1) {
				p1Count++;
			}
			if (board[i][j] == 2)
				p2Count++;
		}
		if (p1Count == board.length)
			return 1;
		else if (p2Count == board.length)
			return 2;

		for (int i = 0, j = 0; i < board.length && j < board.length; i++, j++) {
			if (board[i][j] == 1) {
				p1Count++;
			}
			if (board[i][j] == 2)
				p2Count++;
		}
		if (p1Count == board.length)
			return 1;
		else if (p2Count == board.length)
			return 2;
		return 0;
	}
}

/**
 * Your TicTacToe object will be instantiated and called as such:
 * 
 * TicTacToe obj = new TicTacToe(n);
 * 
 * int param_1 = obj.move(row,col,player);
 */
