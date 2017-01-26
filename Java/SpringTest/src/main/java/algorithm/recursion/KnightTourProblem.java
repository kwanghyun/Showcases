package algorithm.recursion;

/*
 * The knight is placed on the first block of an empty board and, moving
 * according to the rules of chess, must visit each square exactly once.
 * 
 * Backtracking is an algorithmic paradigm that tries different solutions until
 * finds a solution that “works”. Problems which are typically solved using
 * backtracking technique have following property in common. These problems can
 * only be solved by trying every possible configuration and each configuration
 * is tried only once. A Naive solution for these problems is to try all
 * configurations and output a configuration that follows given problem
 * constraints. Backtracking works in incremental way and is an optimization
 * over the Naive solution where all possible configurations are generated and
 * tried.
 * 
 */
public class KnightTourProblem {
	int N = 8;

	/*
	 * A utility function to check if i,j are valid indexes for N*N chessboard
	 */
	boolean isSafe(int x, int y, int sol[][]) {
		return (x >= 0 && x < N && y >= 0 && y < N && sol[x][y] == -1);
	}

	/*
	 * A utility function to print solution matrix sol[N][N]
	 */
	public void printSolution(int sol[][]) {
		for (int x = 0; x < N; x++) {
			for (int y = 0; y < N; y++)
				if (sol[x][y] == 63) {
					System.out.print(" [" + sol[x][y] + "] ");
				} else {
					System.out.print(sol[x][y] + " ");
				}

			System.out.println();
		}
	}

	public boolean solveKT() {
		int sol[][] = new int[8][8];

		/* Initialization of solution matrix */
		for (int r = 0; r < N; r++)
			for (int c = 0; c < N; c++)
				sol[r][c] = -1;

		// Since the Knight is initially at the first block
		sol[0][0] = 0;

		/*
		 * Start from 0,0 and explore all tours using solveKTUtil()
		 */
		if (!solveKTUtil(0, 0, 1, sol)) {
			System.out.println("Solution does not exist");
			return false;
		} else
			printSolution(sol);

		return true;
	}

	int rMove[] = { 2, 1, -1, -2, -2, -1, 1, 2 };
	int cMove[] = { 1, 2, 2, 1, -1, -2, -2, -1 };

	public boolean solveKTUtil(int x, int y, int movei, int sol[][]) {
		int k, next_r, next_c;
		if (movei == N * N)
			return true;

		for (k = 0; k < 8; k++) {
			next_r = x + rMove[k];
			next_c = y + cMove[k];
			if (isSafe(next_r, next_c, sol)) {
				sol[next_r][next_c] = movei;
				if (solveKTUtil(next_r, next_c, movei + 1, sol))
					return true;
				else
					sol[next_r][next_c] = -1;// backtracking
			}
		}
		return false;
	}

	/* Driver program to test above functions */
	public static void main(String args[]) {
		KnightTourProblem ob = new KnightTourProblem();
		ob.solveKT();
	}
}
