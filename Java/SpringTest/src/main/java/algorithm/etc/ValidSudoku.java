package algorithm.etc;

import java.util.ArrayList;

/*
 * Determine if a Sudoku is valid. The Sudoku board could be partially
 * filled, where empty cells are filled with the character '.'. 
 * [Check the image]
 */
public class ValidSudoku {

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
		ArrayList<Integer> list = new ArrayList<>(10);
		System.out.println(list.get(4));
	}
}
