package algorithm.trees;

import java.util.HashMap;
import java.util.Map;

/*
 * Consider lines drawn at an angle of 135 degrees(that is slope = -1) which
 * cut through the left branches of a given binary tree. A diagonal is
 * formed by nodes which lie between two such consecutive lines. If we are
 * able to draw 'n' lines then the complete tree is divided into 'n+1'
 * diagonals. Diagonal sum in a binary tree is sum of all node's values
 * lying between these lines. Given a binary tree, print all diagonal sums.
 * Please note that all right branches are drawn parallel to each other and
 * all left branches are also drawn parallel to each other.
 * 
 * [Check the image]
 * 
 * in the above tree there are total of three diagonals. Diagonal-0 has 3
 * nodes in it: node-0, node-2 and node-6. Therefore, sum of all nodes in
 * diagonal-0 is 8. Similarly, sum of nodes in diagonal-1 is 9(node-1,
 * node-5 and node-3). And sum of nodes in diagonal-2 is 11(node-4, node-7).
 */
public class DiagnalSumOfBT {
	TreeNode root;

	private void computeDiagSum(TreeNode node, int level, HashMap<Integer, Integer> map) {
		if (node == null) {
			return;
		}

		// compute diagonal sum for the tree rooted at the left child
		// left child would be placed in 'level + 1'
		computeDiagSum(node.left, level + 1, map);

		// now add current node's value to its diagonal sum
		int sum = (map.get(level) == null) ? 0 : map.get(level);
		map.put(level, sum + node.value);

		// compute diagonal sum for the tree rooted at the right child
		// right child would be placed in the same diagonal as that of current
		// node
		computeDiagSum(node.right, level, map);
	}

	public void computeDiagSum(HashMap<Integer, Integer> map) {
		computeDiagSum(root, 0, map);
	}

	public static void main(String[] args) {
		DiagnalSumOfBT solution = new DiagnalSumOfBT();

		solution.createTree();

		HashMap<Integer, Integer> diagSum = new HashMap();

		// this call populates diagSum HashMap with sum for all diagonals
		solution.computeDiagSum(diagSum);

		// print sum for each diagonal
		for (Map.Entry<Integer, Integer> entry : diagSum.entrySet()) {
			System.out.println("Diagonal Sum for level " + entry.getKey() + " :" + entry.getValue());
		}
	}

	public void createTree() {

		this.root = new TreeNode(0);

		TreeNode n1 = new TreeNode(1);
		TreeNode n2 = new TreeNode(2);
		TreeNode n3 = new TreeNode(3);
		TreeNode n4 = new TreeNode(4);
		TreeNode n5 = new TreeNode(5);
		TreeNode n6 = new TreeNode(6);
		TreeNode n7 = new TreeNode(7);

		root.left = n1;
		root.right = n2;

		n1.left = n4;
		n1.right = n5;

		n2.left = n3;
		n2.right = n6;

		n5.left = n7;
	}

}
