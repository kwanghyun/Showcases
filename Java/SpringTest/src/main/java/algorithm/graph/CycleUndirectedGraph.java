package algorithm.graph;

import java.util.Iterator;
import java.util.LinkedList;

/*
 * Given an undirected graph find cycle in this graph.
 *
 * Solution This can be solved in many ways. Below is the code to solve it
 * using disjoint sets and DFS.
 *
 * Runtime and space complexity for both the techniques is O(v) where v is
 * total number of vertices in the graph.
 */
public class CycleUndirectedGraph {

	private int noOfVs; // No. of vertices
	private LinkedList<Integer> adj[]; // Adjacency List Represntation

	// Constructor
	public CycleUndirectedGraph(int v) {
		noOfVs = v;
		adj = new LinkedList[v];
		for (int i = 0; i < v; ++i)
			adj[i] = new LinkedList();
	}

	// Function to add an edge into the graph
	public void addEdge(int v, int w) {
		adj[v].add(w);
		adj[w].add(v);
	}

	public Boolean hasCycleDFS(int v, Boolean visited[], int parent) {
		visited[v] = true;
		Integer i;

		// Recur for all the vertices adjacent to this vertex
		Iterator<Integer> it = adj[v].iterator();
		while (it.hasNext()) {
			i = it.next();

			if (!visited[i]) {
				if (hasCycleDFS(i, visited, v))
					return true;
			} else if (i != parent) {
				// If an adjacent is visited and not parent of current
				// vertex, then there is a cycle.
				return true;
			}
		}
		return false;
	}

	// Returns true if the graph contains a cycle, else false.
	public Boolean isCyclic() {
		// Mark all the vertices as not visited and not part of
		// recursion stack
		Boolean visited[] = new Boolean[noOfVs];
		for (int i = 0; i < noOfVs; i++)
			visited[i] = false;

		for (int u = 0; u < noOfVs; u++)
			if (!visited[u])
				if (hasCycleDFS(u, visited, -1))
					return true;

		return false;
	}

	// Driver method to test above methods
	public static void main(String args[]) {
		// Create a graph given in the above diagram
		CycleUndirectedGraph g1 = new CycleUndirectedGraph(5);
		g1.addEdge(1, 0);
		g1.addEdge(0, 2);
		g1.addEdge(2, 0);
		g1.addEdge(0, 3);
		g1.addEdge(3, 4);
		if (g1.isCyclic())
			System.out.println("Graph contains cycle");
		else
			System.out.println("Graph doesn't contains cycle");

		CycleUndirectedGraph g2 = new CycleUndirectedGraph(3);
		g2.addEdge(0, 1);
		g2.addEdge(1, 2);
		if (g2.isCyclic())
			System.out.println("Graph contains cycle");
		else
			System.out.println("Graph doesn't contains cycle");
	}

}