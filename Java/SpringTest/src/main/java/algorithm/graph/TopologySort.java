package algorithm.graph;

/*
 * Given a directed acyclic graph, do a topological sort on this graph.
 *
 * Do DFS by keeping visited. Put the vertex which are completely explored into
 * a stack. Pop from stack to get sorted order.
 * 
 * Time Complexity: The above algorithm is simply DFS with an extra stack. So time complexity is same as DFS which is O(V+E).
 */
import java.io.*;
import java.util.*;

class TopologySort {
	HashMap<Integer, GNode> allVertexs = new HashMap<>();

	void addEdge(int v, int w) {
		GNode nv = null;
		if (!allVertexs.containsKey(v)) {
			nv = new GNode(v);
			allVertexs.put(v, nv);
		} else {
			nv = allVertexs.get(v);
		}

		GNode nw = null;
		if (!allVertexs.containsKey(w)) {
			nw = new GNode(w);
			allVertexs.put(w, nw);
		} else {
			nw = allVertexs.get(w);
		}

		nv.adjs.add(nw);
	}

	void topologicalSort() {
		Stack<Integer> stack = new Stack<>();
		HashSet<Integer> visited = new HashSet<>();

		for (int vertex : allVertexs.keySet()) {
			if (!visited.contains(vertex)) {
				dfs(vertex, visited, stack);
			}
		}

		while (!stack.isEmpty())
			System.out.print(stack.pop() + " ");
	}

	void dfs(int v, HashSet<Integer> visited, Stack<Integer> stack) {
		visited.add(v);
		for (GNode adj : allVertexs.get(v).adjs) {
			if (!visited.contains(adj.val)) {
				dfs(adj.val, visited, stack);
			}
		}

		stack.push(v);
	}

	// Driver method
	public static void main(String args[]) {
		// Create a graph given in the above diagram
		TopologySort g = new TopologySort();
		g.addEdge(5, 2);
		g.addEdge(5, 0);
		g.addEdge(4, 0);
		g.addEdge(4, 1);
		g.addEdge(2, 3);
		g.addEdge(3, 1);
		System.out.println(g.allVertexs.values());
		System.out.println("Following is a Topological " + "sort of the given graph");
		g.topologicalSort();
	}
}