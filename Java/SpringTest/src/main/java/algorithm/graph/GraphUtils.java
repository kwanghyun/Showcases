package algorithm.graph;

import algorithm.Utils;

public class GraphUtils {

	public static void addDirectedEdge(int[][] graph, int u, int v) {
		graph[u][v] = 1;
	}

	public static void addWeightedDirectedEdge(int[][] graph, int u, int v, int weight) {
		graph[u][v] = weight;
	}

	public static void addUndirectedEdge(int[][] graph, int u, int v) {
		addDirectedEdge(graph, u, v);
		addDirectedEdge(graph, v, u);
	}

	public static void addWeightedUndirectedEdge(int[][] graph, int u, int v, int weight) {
		addWeightedDirectedEdge(graph, u, v, weight);
		addWeightedDirectedEdge(graph, v, u, weight);
	}

	public static int[][] buildMatrixGraph(int noOfVertexes, int[][] connections) {
		int[][] graph = new int[noOfVertexes][noOfVertexes];
		for (int r = 0; r < connections.length; r++) {
			addWeightedUndirectedEdge(graph, connections[r][0], connections[r][1], connections[r][2]);
		}
		Utils.printMetrix(graph);

		return graph;
	}

}
