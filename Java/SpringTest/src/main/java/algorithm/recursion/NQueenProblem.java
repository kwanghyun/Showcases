package algorithm.recursion;

/*
 * We have discussed Knight’s tour and Rat in a Maze problems in Set 1 and Set 2
 * respectively. Let us discuss N Queen as another example problem that can be
 * solved using Backtracking.
 * 
 * The N Queen is the problem of placing N chess queens on an N×N chessboard so
 * that no two queens attack each other. For example, following is a solution
 * for 4 Queen problem.
 *
 * Limitations: works for N <= 25, but slows down considerably for larger N.
 *
 *  % java Queens 4
 *  * Q * * 
 *  * * * Q 
 *  Q * * * 
 *  * * Q * 
 *
 *  * * Q * 
 *  Q * * * 
 *  * * * Q 
 *  * Q * * 
 *
 *  % java Queens 8
 *  Q * * * * * * * 
 *  * * * * Q * * * 
 *  * * * * * * * Q 
 *  * * * * * Q * * 
 *  * * Q * * * * * 
 *  * * * * * * Q * 
 *  * Q * * * * * * 
 *  * * * Q * * * * 
 *
 */
public class NQueenProblem {
	final int nQueens = 4;

	/*
	 * A recursive utility function to solve N Queen problem
	 */
	boolean placeQueens(int board[][], int idx) {
		/*
		 * base case: If all queens are placed then return true
		 */
		if (idx >= nQueens)
			return true;

		/*
		 * Consider this column and try placing this queen in all rows one by
		 * one
		 */
		for (int r = 0; r < nQueens; r++) {
			if (isSafe(board, r, idx)) {
				/* Place this queen in board[i][col] */
				board[r][idx] = 1;

				/* recur to place rest of the queens */
				if (placeQueens(board, idx + 1) == true)
					return true;

				/*
				 * If placing queen in board[i][col] doesn't lead to a solution
				 * then remove queen from board[i][col]
				 */
				board[r][idx] = 0; // BACKTRACK
			}
		}

		/*
		 * If queen can not be place in any row in this colum col, then return
		 * false
		 */
		return false;
	}

	/*
	 * A utility function to check if a queen can be placed on board[row][col].
	 * Note that this function is called when "col" queens are already placeed
	 * in columns from 0 to col -1. So we need to check only left side for
	 * attacking queens
	 */
	boolean isSafe(int board[][], int row, int col) {
		int i, j;

		/* Check this row on left side */
		for (i = 0; i < col; i++)
			if (board[row][i] == 1)
				return false;

		/* Check upper diagonal on left side */
		for (i = row, j = col; i >= 0 && j >= 0; i--, j--)
			if (board[i][j] == 1)
				return false;

		/* Check lower diagonal on left side */
		for (i = row, j = col; j >= 0 && i < nQueens; i++, j--)
			if (board[i][j] == 1)
				return false;

		return true;
	}

	/* A utility function to print solution */
	void printSolution(int board[][]) {
		for (int i = 0; i < nQueens; i++) {
			for (int j = 0; j < nQueens; j++)
				System.out.print(" " + board[i][j] + " ");
			System.out.println();
		}
	}

	/*
	 * This function solves the N Queen problem using Backtracking. It mainly
	 * uses solveNQUtil() to solve the problem. It returns false if queens
	 * cannot be placed, otherwise return true and prints placement of queens in
	 * the form of 1s. Please note that there may be more than one solutions,
	 * this function prints one of the feasible solutions.
	 */
	boolean solveNQ() {
		int board[][] = { { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };

		if (placeQueens(board, 0) == false) {
			System.out.print("Solution does not exist");
			return false;
		}

		printSolution(board);
		return true;
	}

	// driver program to test above function
	public static void main(String args[]) {
		NQueenProblem Queen = new NQueenProblem();
		Queen.solveNQ();
	}
}
