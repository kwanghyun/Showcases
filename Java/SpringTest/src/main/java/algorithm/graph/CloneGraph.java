package algorithm.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/*
 * Clone an undirected graph. Each node in the graph contains a label and a
 * list of its neighbors.
 */
public class CloneGraph {
	public UGNode cloneGraph(UGNode node) {
		if (node == null)
			return null;

		LinkedList<UGNode> queue = new LinkedList<UGNode>();
		HashMap<UGNode, UGNode> map = new HashMap<UGNode, UGNode>();

		UGNode newHead = new UGNode(node.label);

		queue.add(node);
		map.put(node, newHead);

		while (!queue.isEmpty()) {
			UGNode curr = queue.pop();
			ArrayList<UGNode> currNeighbors = curr.neighbors;

			for (UGNode aNeighbor : currNeighbors) {
				if (!map.containsKey(aNeighbor)) {
					UGNode copy = new UGNode(aNeighbor.label);
					map.put(aNeighbor, copy);
					map.get(curr).neighbors.add(copy);
					queue.add(aNeighbor);
				} else {
					map.get(curr).neighbors.add(map.get(aNeighbor));
				}
			}

		}
		return newHead;
	}
}
