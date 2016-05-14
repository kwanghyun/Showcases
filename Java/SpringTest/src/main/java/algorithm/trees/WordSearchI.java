package algorithm.trees;

/*
 * Word Search (Java)

Given a 2D board and a word, find if the word exists in the grid.

The word can be constructed from letters of sequentially adjacent cell, 
where "adjacent" cells are those horizontally or vertically neighboring. 
The same letter cell may not be used more than once.

For example, given board =

[
  ["ABCE"],
  ["SFCS"],
  ["ADEE"]
]
word = "ABCCED", -> returns true,
word = "SEE", -> returns true,
word = "ABCB", -> returns false.

Analysis

This problem can be solve by using a typical DFS method.
*/
public class WordSearchI {

	public boolean exist(char[][] board, String word) {

		boolean result = false;
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				if (dfs(board, word, r, c, 0)) {
					result = true;
				}
			}
		}
		return result;
	}

	public boolean dfs(char[][] board, String word, int r, int c, int i) {

		if (r < 0 || c < 0 || r >= board.length || c >= board[0].length) {
			return false;
		}

		if (board[r][c] == word.charAt(i)) {
			char temp = board[r][c];
			//Change it temporary
			board[r][c] = '#';
			if (i == word.length() - 1) {
				return true;
			} else if (dfs(board, word, r - 1, c, i + 1) 
					|| dfs(board, word, r + 1, c, i + 1)
					|| dfs(board, word, r, c - 1, i + 1) 
					|| dfs(board, word, r, c + 1, i + 1)) {
				return true;
			}
			board[r][c] = temp;
		}
		return false;
	}
}
