package algorithm.graph;

/*
 * Given an undirected graph and a number m, determine if the graph can be
 * colored with at most m colors such that no two adjacent vertices of the
 * graph are colored with same color. Here coloring of a graph means
 * assignment of colors to all vertices.
 * 
 * Input: 1) A 2D array graph[V][V] where V is the number of vertices in
 * graph and graph[V][V] is adjacency matrix representation of the graph. A
 * value graph[i][j] is 1 if there is a direct edge from i to j, otherwise
 * graph[i][j] is 0. 2) An integer m which is maximum number of colors that
 * can be used.
 * 
 * Output: An array color[V] that should have numbers from 1 to m. color[i]
 * should represent the color assigned to the ith vertex. The code should
 * also return false if the graph cannot be colored with m colors.
 * 
 * Following is an example graph (from Wiki page ) that can be colored with
 * 3 colors.
    Create following graph and test whether it is
       3 colorable
      (3)---(2)
       |     / |
       |   /   |
       | /     |
      (0)---(1)
 */

public class MColoringProblem {
	final int noOfVerticles = 4;
	int color[];

	/*
	 * A utility function to check if the current color assignment is safe for
	 * vertex v
	 */
	boolean isSafe(int vertexIdx, int graph[][], int colors[], int colorIdx) {
		for (int i = 0; i < noOfVerticles; i++)
			if (graph[vertexIdx][i] == 1 && colorIdx == colors[i])
				return false;
		return true;
	}

	/*
	 * A recursive utility function to solve m coloring problem
	 */
	boolean graphColoringUtil(int graph[][], int m, int colors[], int vertexIdx) {
		/*
		 * base case: If all vertices are assigned a color then return true
		 */
		if (vertexIdx == noOfVerticles)
			return true;

		/*
		 * Consider this vertex v and try different colors
		 */
		for (int colorIdx = 1; colorIdx <= m; colorIdx++) {
			/*
			 * Check if assignment of color c to v is fine
			 */
			if (isSafe(vertexIdx, graph, colors, colorIdx)) {
				colors[vertexIdx] = colorIdx;

				/*
				 * recur to assign colors to rest of the vertices
				 */
				if (graphColoringUtil(graph, m, colors, vertexIdx + 1))
					return true;

				/*
				 * If assigning color c doesn't lead to a solution then remove
				 * it
				 */
				colors[vertexIdx] = 0;
			}
		}

		/*
		 * If no color can be assigned to this vertex then return false
		 */
		return false;
	}

	/*
	 * This function solves the m Coloring problem using Backtracking. It mainly
	 * uses graphColoringUtil() to solve the problem. It returns false if the m
	 * colors cannot be assigned, otherwise return true and prints assignments
	 * of colors to all vertices. Please note that there may be more than one
	 * solutions, this function prints one of the feasible solutions.
	 */
	boolean graphColoring(int graph[][], int m) {
		// Initialize all color values as 0. This
		// initialization is needed correct functioning
		// of isSafe()
		color = new int[noOfVerticles];
		for (int i = 0; i < noOfVerticles; i++)
			color[i] = 0;

		// Call graphColoringUtil() for vertex 0
		if (!graphColoringUtil(graph, m, color, 0)) {
			System.out.println("Solution does not exist");
			return false;
		}

		// Print the solution
		printSolution(color);
		return true;
	}

	/* A utility function to print solution */
	void printSolution(int color[]) {
		System.out.println("Solution Exists: Following" + " are the assigned colors");
		for (int i = 0; i < noOfVerticles; i++)
			System.out.print(" " + color[i] + " ");
		System.out.println();
	}

	// driver program to test above function
	public static void main(String args[]) {
		MColoringProblem Coloring = new MColoringProblem();

		int graph[][] = { { 0, 1, 1, 1 }, { 1, 0, 1, 0 }, { 1, 1, 0, 1 }, { 1, 0, 1, 0 }, };
		int m = 3; // Number of colors
		Coloring.graphColoring(graph, m);
	}
}
