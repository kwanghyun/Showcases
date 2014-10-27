package algorithm.trees;

import java.util.ArrayList;
import java.util.List;

public class DepthFirstSearch {

	public GraphNode dfs(GraphNode root, int value){	
		if(root == null)
			return null;

		if (root.value == value) {
			return root;
		} else {
			List<GraphNode> list = root.getAdjacent();
			GraphNode found;
			for (GraphNode node : list) {
				if (node.state == State.Unvisited) {
					found = dfs(node, value);
					if (found != null){
						return found;
					}
				}
			}
		}
		System.out.println("2. " + root.value);
		return null;
	}
	
	public static void main(String args[]){
		DepthFirstSearch dfs = new DepthFirstSearch();
		/*
		 * 5 -> 6 -> 2 -> 7 -> 3 -> 8 -> 9 -> 10
		 */
		System.out.println(dfs.dfs(dfs.generateGraphTree(), 10).value);
	}
	
	public GraphNode generateGraphTree(){
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
		return node1;
	}
}
