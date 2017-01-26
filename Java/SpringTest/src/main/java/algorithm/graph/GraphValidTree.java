package algorithm.graph;

import java.util.*;

/*
 * Given n nodes labeled from 0 to n - 1 and a list of undirected edges
 * (each edge is a pair of nodes), write a function to check whether these
 * edges make up a valid tree.
 * 
 * For example:
 * 
 * Given n = 5 and edges = [[0, 1], [0, 2], [0, 3], [1, 4]], return true.
 * 
 * Given n = 5 and edges = [[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]], return
 * false.
 */
public class GraphValidTree {
	/*
	 * This problem can be converted to finding a cycle in a graph. It can be
	 * solved by using DFS (Recursion) or BFS (Queue).
	 */
	public boolean validTree(int n, int[][] edges) {

		Map<Integer, List<Integer>> graph = addEdges(edges, false);
		for (int i = 0; i < n; i++) {
			if (!graph.containsKey(i))
				graph.put(i, new ArrayList<>());
		}

		// System.out.println("graph = " + graph);
		HashSet<Integer> visited = new HashSet<>();

		for (int vertex : graph.keySet()) {

			if (!visited.contains(vertex)) {
				visited.add(vertex);
				if (hasCycleDFS(graph, vertex, visited, -1))
					return false;
			}
			// one cycle done, didn't visit all node, then there are
			// disconnected nodes
			System.out.println("[EOL] visited.size() = " + visited.size() + ", graph.size() = " + graph.size());
			if (visited.size() != graph.size())
				return false;
		}
		return true;
	}

	private boolean hasCycleDFS(Map<Integer, List<Integer>> graph, int vertex, HashSet<Integer> visited, int parent) {

		for (int adj : graph.get(vertex)) {
			// System.out.println("adj = " + adj + ", parent = " + parent + ",
			// adjs =" + graph.get(vertex) + ", visited = "
			// + visited);
			if (!visited.contains(adj)) {
				visited.add(adj);
				if (hasCycleDFS(graph, adj, visited, vertex))
					return true;
			} else if (adj != parent) {
				return true;
			}
		}
		return false;
	}

	public void addEdge(Map<Integer, List<Integer>> graph, int u, int v, boolean isDirected) {
		List<Integer> adjs4u = graph.getOrDefault(u, new ArrayList<>());
		adjs4u.add(v);
		graph.put(u, adjs4u);
		if (!isDirected) {
			List<Integer> adjs4v = graph.getOrDefault(v, new ArrayList<>());
			adjs4v.add(u);
			graph.put(v, adjs4v);
		}
	}

	public Map<Integer, List<Integer>> addEdges(int[][] edges, boolean isDirected) {
		Map<Integer, List<Integer>> graph = new HashMap<>();
		for (int i = 0; i < edges.length; i++) {
			addEdge(graph, edges[i][0], edges[i][1], isDirected);
		}
		return graph;
	}

	public static void main(String[] args) {
		GraphValidTree ob = new GraphValidTree();
		System.out.println("[[0,1],[0,2],[2,3],[2,4]]".replaceAll("\\[", "{").replaceAll("\\]", "}"));
		System.out.println("[[0, 1], [1, 2], [2, 3], [1, 3], [1, 4]]".replaceAll("\\[", "{").replaceAll("\\]", "}"));

		int[][] edges = {};
		// int[][] edges = { { 0, 1 }, { 0, 2 }, { 0, 3 }, { 1, 4 } };
		// int[][] edges = { { 0, 1 }, { 1, 2 }, { 2, 3 }, { 1, 3 }, { 1, 4 } };
		// int[][] edges = { { 0, 1 }, { 0, 2 }, { 2, 3 }, { 2, 4 } };

		System.out.println(ob.validTree(1, edges));

	}
}
