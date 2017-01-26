package algorithm.graph;

import java.util.HashMap;

import algorithm.Utils;

public class MST {

	public int getMinIdx(int[][] graph, int[] keys, boolean[] mstSet) {
		int min = Integer.MAX_VALUE;
		int minIdx = -1;

		for (int r = 0; r < graph.length; r++) {
			if (mstSet[r] == false && keys[r] < min) {
				min = keys[r];
				minIdx = r;
			}
		}
		return minIdx;
	}

	public void primMST(int[][] graph) {

		HashMap<Integer, Integer> resultPath = new HashMap<>();

		// Array to store constructed MST
		int parent[] = new int[graph.length];

		// To represent set of vertices not yet included in MST
		boolean[] mstSet = new boolean[graph.length];

		// Key values used to pick minimum weight edge in cut
		int keys[] = new int[graph.length];

		// Initialize all keys as INFINITE
		for (int i = 0; i < graph.length; i++) {
			keys[i] = Integer.MAX_VALUE;
			mstSet[i] = false;
		}

		// Always include first 1st vertex in MST.
		// Make key 0 so that this vertex is picked as first vertex
		keys[0] = 0;

		// First node is always root of MS
		parent[0] = -1;

		for (int count = 1; count < graph.length; count++) {
			// Pick the minimum key vertex from the set of vertices
			// not yet included in MST
			int u = getMinIdx(graph, keys, mstSet);

			// Add the picked vertex to the MST Set
			mstSet[u] = true;

			// Update key value and parent index of the adjacent
			// vertices of the picked vertex. Consider only those
			// vertices which are not yet included in MST
			for (int v = 0; v < graph.length; v++) {
				// Update the key only if graph[u][v] is smaller than key[v]
				if (graph[u][v] != 0 && mstSet[v] == false && graph[u][v] < keys[v]) {
					parent[v] = u;

					keys[v] = graph[u][v];
				}
			}
		}
		Utils.printArray(parent);
		printMST(parent, graph);
	}

	public void printMST(int[] parent, int[][] graph) {
		System.out.println("Edge  :  Weight");
		for (int i = 1; i < graph.length; i++) {
			System.out.println(parent[i] + " - " + i + "    " + graph[i][parent[i]]);
		}

	}

	public static void main(String[] args) {
		MST ob = new MST();
		int[][] connections = { { 0, 1, 2 }, { 0, 3, 6 }, { 1, 2, 3 }, { 1, 3, 8 }, { 1, 4, 5 }, { 2, 4, 7 },
				{ 3, 4, 9 } };

		int[][] graph = GraphUtils.buildMatrixGraph(5, connections);
		ob.primMST(graph);
	}
}
