package algorithm.trees;

//Given a directed graph, design an algorithm to find out whether there is a route between two nodes.

//NOTE : Directed graph almost similar with Tree.

import java.util.Stack;

public class CheckRoute {

	public boolean search(GraphNode start, GraphNode end) {

		Stack<GraphNode> visitList = new Stack<GraphNode>();
		visitList.push(start);

		while (!visitList.isEmpty()) {
			GraphNode current = visitList.pop();

			for (GraphNode child : current.getAdjacent()) {
				if (child.state != State.Visited) {
					if (child.value == end.value) {
						return true;
					}
					// put visiting list
					child.state = State.Visited;
					System.out.println("Visited Value : " + child.value);
					visitList.add(child);
				}
			}
		}
		return false;
	}
	
	public static void main(String args[]){
		//                     1 
        //                   / |  \
		//                 /   |     \
		//                2   3       4
		//               / \   |    / |  \  \
		//              5  6 7  8  9  10 11
		GraphNode node1 = new GraphNode(1);
		GraphNode node2 = new GraphNode(2);
		GraphNode node3 = new GraphNode(3);
		GraphNode node4 = new GraphNode(4);
		GraphNode node5 = new GraphNode(5);
		GraphNode node6 = new GraphNode(6);
		GraphNode node7 = new GraphNode(7);
		GraphNode node8 = new GraphNode(8);
		GraphNode node9 = new GraphNode(9);
		GraphNode node10 = new GraphNode(10);
		GraphNode node11 = new GraphNode(11);
		
		node1.addChild(node2);
		node1.addChild(node3);
		node1.addChild(node4);
		node2.addChild(node5);
		node2.addChild(node6);
		node3.addChild(node7);
		node4.addChild(node8);
		node4.addChild(node9);
		node4.addChild(node10);
		node4.addChild(node11);
		
		CheckRoute cr = new CheckRoute();
//		System.out.println(cr.search(node1, node10));
		System.out.println("--------------------------");
		System.out.println(cr.search(node4, node7));
	}
}
