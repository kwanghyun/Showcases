package algorithm.trees;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * Given a 2D board and a list of words from the dictionary, find all words
 * in the board.
 * 
 * Each word must be constructed from letters of sequentially adjacent cell,
 * where "adjacent" cells are those horizontally or vertically neighboring.
 * The same letter cell may not be used more than once in a word.
 * 
 * For example, given words = ["oath","pea","eat","rain"] and board =
 * 
 * [
 * ['o','a','a','n'],
 * ['e','t','a','e'],
 * ['i','h','k','r'],
 * ['i','f','l','v']
 * ]
 * 
 * Return ["eat","oath"].
 * 
 * Java Solution 1
 * Similar to Word Search, this problem can be solved by DFS. However, this
 * solution exceeds time limit.
 */
public class WordSearchII {

	public List<String> findWordsI(char[][] board, String[] words) {
		ArrayList<String> result = new ArrayList<String>();

		int m = board.length;
		int n = board[0].length;

		for (String word : words) {
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					if (dfsI(board, word, i, j, 0)) {
						result.add(word);
					}
				}
			}
			printBoard(board);
		}

		return result;
	}

	public boolean dfsI(char[][] board, String word, int r, int c, int i) {
		int m = board.length;
		int n = board[0].length;

		if (i == word.length() - 1) {
			return true;
		}

		if (r < 0 || c < 0 || r >= m || c >= n || i > word.length() - 1) {
			return false;
		}

		if (board[r][c] == word.charAt(i)) {
			char temp = board[r][c];
			board[r][c] = '#';

			boolean found = dfsI(board, word, r - 1, c, i + 1) 
					|| dfsI(board, word, r + 1, c, i + 1)
					|| dfsI(board, word, r, c - 1, i + 1) 
					|| dfsI(board, word, r, c + 1, i + 1);

			board[r][c] = temp;
			return found;
		}

		return false;
	}

	public List<String> findWords(char[][] board, String[] words) {
		ArrayList<String> result = new ArrayList<String>();

		int m = board.length;
		int n = board[0].length;

		for (String word : words) {
			boolean flag = false;
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					char[][] newBoard = new char[m][n];
					for (int x = 0; x < m; x++)
						for (int y = 0; y < n; y++)
							newBoard[x][y] = board[x][y];

					if (dfs(newBoard, word, i, j, 0)) {
						flag = true;
					}
				}
			}
			printBoard(board);
			if (flag) {
				result.add(word);
			}
		}

		return result;
	}

	public boolean dfs(char[][] board, String word, int r, int c, int i) {
		int m = board.length;
		int n = board[0].length;

		if (r < 0 || c < 0 || r >= m || c >= n || i > word.length() - 1) {
			return false;
		}

		if (board[r][c] == word.charAt(i)) {
			char temp = board[r][c];
			board[r][c] = '#';

			if (i == word.length() - 1) {
				return true;
			} else if (dfs(board, word, r - 1, c, i + 1) || dfs(board, word, r + 1, c, i + 1)
					|| dfs(board, word, r, c - 1, i + 1) || dfs(board, word, r, c + 1, i + 1)) {
				board[r][c] = temp;
				return true;
			}

		} else {
			return false;
		}
		return false;
	}

	private void printBoard(char[][] board) {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				System.out.print("[" + board[r][c] + "]");
			}
			System.out.println("");
		}
	}

	public static void main(String[] args) {
		char[][] board = { { 'o', 'a', 'a', 'n' }, { 'e', 't', 'a', 'e' }, { 'i', 'h', 'k', 'r' },
				{ 'i', 'f', 'l', 'v' } };
		String[] words = { "oath", "pea", "eat", "rain" };
		WordSearchII ob = new WordSearchII();
		System.out.println(ob.findWords(board, words));
		System.out.println("-------------------------------");
		System.out.println(ob.findWordsI(board, words));
	}

	public class Solution {
		Set<String> result = new HashSet<String>();

		public List<String> findWords(char[][] board, String[] words) {
			// HashSet<String> result = new HashSet<String>();

			Trie trie = new Trie();
			for (String word : words) {
				trie.insert(word);
			}

			int m = board.length;
			int n = board[0].length;

			boolean[][] visited = new boolean[m][n];

			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					dfs(board, visited, "", i, j, trie);
				}
			}

			return new ArrayList<String>(result);
		}

		public void dfs(char[][] board, boolean[][] visited, String str, int r, int c, Trie trie) {
			int m = board.length;
			int n = board[0].length;

			if (r < 0 || c < 0 || r >= m || c >= n) {
				return;
			}

			if (visited[r][c])
				return;

			str = str + board[r][c];

			if (!trie.startsWith(str))
				return;

			if (trie.search(str)) {
				result.add(str);
			}

			visited[r][c] = true;
			dfs(board, visited, str, r - 1, c, trie);
			dfs(board, visited, str, r + 1, c, trie);
			dfs(board, visited, str, r, c - 1, trie);
			dfs(board, visited, str, r, c + 1, trie);
			visited[r][c] = false;
		}
	}
}
