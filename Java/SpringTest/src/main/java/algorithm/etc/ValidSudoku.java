package algorithm.etc;

import java.util.ArrayList;

/*
 * Determine if a Sudoku is valid. The Sudoku board could be partially
 * filled, where empty cells are filled with the character '.'. 
 * [Check the image]
 */
public class ValidSudoku {

	public boolean isValidSudoku(int[][] board) {
		if (board == null || board.length != 9 || board[0].length != 9)
			return false;
		// check each column
		for (int r = 0; r < 9; r++) {
			boolean[] checker = new boolean[9];
			for (int c = 0; c < 9; c++) {
				if (board[r][c] != '.') {
					if (checker[board[r][c] - 1]) {
						return false;
					}
					checker[board[r][c] - 1] = true;
				}
			}
		}

		// check each row
		for (int c = 0; c < 9; c++) {
			boolean[] checker = new boolean[9];
			for (int r = 0; r < 9; r++) {
				if (board[r][c] != '.') {
					if (checker[board[r][c] - 1]) {
						return false;
					}
					checker[board[r][c] - 1] = true;
				}
			}
		}

		// check each 3*3 matrix
		for (int block = 0; block < 9; block++) {
			boolean[] checker = new boolean[9];
			for (int r = block / 3 * 3; r < block / 3 * 3 + 3; r++) {
				for (int c = block % 3 * 3; c < block % 3 * 3 + 3; c++) {
					if (board[r][c] != '.') {
						System.out.println(r + ", " + c);
						if (checker[board[r][c] - 1]) {
							return false;
						}
						checker[board[r][c] - 1] = true;
					}
				}
			}
		}
		return true;
	}

	public boolean isValidSudoku(char[][] board) {
		if (board == null || board.length != 9 || board[0].length != 9)
			return false;
		// check each column
		for (int r = 0; r < 9; r++) {
			boolean[] checker = new boolean[9];
			for (int c = 0; c < 9; c++) {
				if (board[r][c] != '.') {
					if (checker[(int) (board[r][c] - '1')]) {
						return false;
					}
					checker[(int) (board[r][c] - '1')] = true;
				}
			}
		}

		// check each row
		for (int c = 0; c < 9; c++) {
			boolean[] checker = new boolean[9];
			for (int r = 0; r < 9; r++) {
				if (board[r][c] != '.') {
					if (checker[(int) (board[r][c] - '1')]) {
						return false;
					}
					checker[(int) (board[r][c] - '1')] = true;
				}
			}
		}

		// check each 3*3 matrix
		for (int block = 0; block < 9; block++) {
			boolean[] checker = new boolean[9];
			for (int r = block / 3 * 3; r < block / 3 * 3 + 3; r++) {
				for (int c = block % 3 * 3; c < block % 3 * 3 + 3; c++) {
					if (board[r][c] != '.') {
						if (checker[(int) (board[r][c] - '1')]) {
							return false;
						}
						checker[(int) (board[r][c] - '1')] = true;
					}
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		// The example from Wikipedia
		int[][] board = { { 5, 3, 4, 6, 7, 8, 9, 1, 2 }, { 6, 7, 2, 1, 9, 5, 3, 4, 8 }, { 1, 9, 8, 3, 4, 2, 5, 6, 7 },

				{ 8, 5, 9, 7, 6, 1, 4, 2, 3 }, { 4, 2, 6, 8, 5, 3, 7, 9, 1 }, { 7, 1, 3, 9, 2, 4, 8, 5, 6 },

				{ 9, 6, 1, 5, 3, 7, 2, 8, 4 }, { 2, 8, 7, 4, 1, 9, 6, 3, 5 }, { 3, 4, 5, 2, 8, 6, 1, 7, 9 } };

		ValidSudoku ob = new ValidSudoku();
		System.out.println(ob.isValidSudoku(board));

	}
}
