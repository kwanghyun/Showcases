package algorithm.graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

/*
 * Given a graph with no negative distance between any two nodes, find the
 * shortest distance between initial node '0' and all nodes. For example,
 * for the following graph
 * 
 * Shortest distance of node '0' from node '0' is 0 
 * Shortest distance of node '1' from node '0' is 2 
 * Shortest distance of node '2' from node '0'
 * is 1 
 * Shortest distance of node '3' from node '0' is 6 
 * Shortest distance of node '5' from node '0' is 12 
 * Shortest distance of node '6' from node '0' is 12 
 * and node '4' is unreachable from node '0'
 */
public class DijkstraShortestPath {
	final private static int QUEUE_INITIAL_CAPACITY = 10;

	public class QueueNode {
		int nodeId;
		int distFromSrc;

		public QueueNode(int id, int dist) {
			nodeId = id;
			distFromSrc = dist;
		}
	}

	public class queueNodeComparator implements Comparator<QueueNode> {
		@Override
		public int compare(QueueNode x, QueueNode y) {
			if (x.distFromSrc < y.distFromSrc) {
				return -1;
			}
			if (x.distFromSrc > y.distFromSrc) {
				return 1;
			}
			return 0;
		}
	}

	static private class GraphNode {
		int nodeId;
		GraphNode next;
		int parentDist;

		GraphNode(int id) {
			nodeId = id;
			next = null;
		}

		GraphNode(int id, int dist) {
			nodeId = id;
			next = null;
			parentDist = dist;
		}
	}

	ArrayList<GraphNode> nodeList;

	public DijkstraShortestPath()
    {
        nodeList = new ArrayList<GraphNode>();
    }

	public void addNode(int id) {
		GraphNode node = new GraphNode(id);
		nodeList.add(node);
	}

	public void addEdge(int id1, int id2, int dist) {
		int i = 0;

		for (i = 0; i < nodeList.size(); i++) {
			if (nodeList.get(i).nodeId == id1) {
				break;
			}
		}
		if (i == nodeList.size()) {
			return;
		}

		GraphNode node1 = nodeList.get(i);
		GraphNode node2 = new GraphNode(id2, dist);

		node2.next = node1.next;
		node1.next = node2;
	}

	private GraphNode findGraphNode(int currQueueNodeId) {
		for (int i = 0; i < nodeList.size(); i++) {
			if (nodeList.get(i).nodeId == currQueueNodeId) {
				return nodeList.get(i);
			}
		}

		return null;
	}

	public void printGraph() {
		for (int i = 0; i < nodeList.size(); i++) {
			GraphNode curr = nodeList.get(i);

			while (curr != null) {
				System.out.print(curr.nodeId + "(" + curr.parentDist + ")" + "->");
				curr = curr.next;
			}
			System.out.print("Null");
			System.out.println();
		}
	}

	private void updateQueue(PriorityQueue<QueueNode> queue, int nodeId, int oldDist, int newDist) {
		// this step removes the old node with non-optimum distance.
		// This is the first step for updating new shortest possible distance
		// queue.remove(new QueueNode(nodeId, oldDist));
		Iterator<QueueNode> queueItr = queue.iterator();
		boolean queueUpdated = false;

		while (queueItr.hasNext()) {
			QueueNode tempNode = queueItr.next();
			if (tempNode.nodeId == nodeId) {
				queueUpdated = true;
				tempNode.distFromSrc = newDist;
				break;
			}
		}

		// if queue is not updated then that means entry for this node does not
		// exist
		// and has to be added now
		if (!queueUpdated) {
			queue.add(new QueueNode(nodeId, newDist));
		}
	}

	public int[] findShortestDijkstra(int srcId) {
		Comparator<QueueNode> comparator = new queueNodeComparator();
		PriorityQueue<QueueNode> queue = new PriorityQueue<QueueNode>(QUEUE_INITIAL_CAPACITY, comparator);

		GraphNode temp = null;
		boolean[] unvisited = new boolean[nodeList.size()];

		int[] parent = new int[nodeList.size()];
		int[] distance = new int[nodeList.size()];

		for (int i = 0; i < nodeList.size(); i++) {
			unvisited[i] = true;
			parent[i] = -1;
			distance[i] = Integer.MAX_VALUE;
		}

		// add source vertex to the queue with distance as 0
		queue.add(new QueueNode(srcId, 0));

		// essentially a breadth first logic
		while (!queue.isEmpty()) {
			// greedy approach -
			// remove the first node which would have least distance from source
			// among all the nodes in queue
			QueueNode currQueueNode = queue.remove();
			unvisited[currQueueNode.nodeId] = false;

			distance[currQueueNode.nodeId] = currQueueNode.distFromSrc;

			GraphNode currGraphNode = findGraphNode(currQueueNode.nodeId);

			GraphNode neighborNode = (currGraphNode == null) ? null : currGraphNode.next;

			// for all the neighbors of current graph node, update their
			// distance from source node if applicable
			while (neighborNode != null) {
				if (unvisited[neighborNode.nodeId]) {
					// and distance from source node through the current node is
					// less than the previous distance
					if ((distance[currQueueNode.nodeId] + neighborNode.parentDist) < distance[neighborNode.nodeId]) {
						int oldDistance = distance[neighborNode.nodeId];
						int newDistance = distance[currQueueNode.nodeId] + neighborNode.parentDist;
						distance[neighborNode.nodeId] = newDistance;

						parent[neighborNode.nodeId] = currQueueNode.nodeId;
						updateQueue(queue, neighborNode.nodeId, oldDistance, newDistance);
					}
				}
				neighborNode = neighborNode.next;
			}
		}
		return distance;
	}

	public static void main(String[] args) {
		DijkstraShortestPath graphObj = new DijkstraShortestPath();

		graphObj.addNode(0);
		graphObj.addNode(1);
		graphObj.addNode(2);
		graphObj.addNode(3);
		graphObj.addNode(4);
		graphObj.addNode(5);
		graphObj.addNode(6);

		graphObj.addEdge(0, 2, 1);
		graphObj.addEdge(0, 1, 2);

		graphObj.addEdge(1, 2, 3);

		graphObj.addEdge(2, 3, 5);
		graphObj.addEdge(2, 6, 13);

		graphObj.addEdge(3, 5, 6);
		graphObj.addEdge(3, 1, 6);
		graphObj.addEdge(3, 6, 6);

		graphObj.addEdge(5, 3, 7);

		// find the shortest distance of all nodes from node '0'
		int[] distance = graphObj.findShortestDijkstra(0);

		for (int i = 0; i < distance.length; i++) {
			if (distance[i] == Integer.MAX_VALUE) {
				System.out.println("vertex \'" + i + "\' is unreachable from vertex '0'");
			} else {
				System.out.println("distance of vertex \'" + i + "\' from vertex '0' is " + distance[i]);
			}
		}
	}
}
