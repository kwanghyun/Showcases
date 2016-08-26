package algorithm.trees;

import java.util.Stack;

import algorithm.utils.TreeUtils;

/*
 * Given a binary tree, return the level order traversal of its nodes'
 * values. (ie, from left to right, level by level).
 * 
 * For example: Given binary tree {3,9,20,#,#,15,7},
 *
 *     3
 *    / \
 *   9  20
 *      /  \
 *    15   7
 * return its level order traversal as [[3], [20,9], [15,7]]
 * 
 */
public class ZIgZagLevelOrder {
	public void spiralOrZigzagLevelOrder(TreeNode root) {
		if (root == null)
			return;
		Stack<TreeNode> stack = new Stack<TreeNode>();
		stack.push(root);

		boolean directionflag = false;
		while (!stack.isEmpty()) {
			Stack<TreeNode> tempStack = new Stack<TreeNode>();

			while (!stack.isEmpty()) {
				TreeNode node = stack.pop();
				System.out.printf("%d ", node.value);
				if (!directionflag) {
					if (node.left != null)
						tempStack.push(node.left);
					if (node.right != null)
						tempStack.push(node.right);
				} else {
					if (node.right != null)
						tempStack.push(node.right);
					if (node.left != null)
						tempStack.push(node.left);
				}
			}
			// for changing direction
			directionflag = !directionflag;

			stack = tempStack;
		}
	}

	public static void main(String[] args) {
		ZIgZagLevelOrder ob = new ZIgZagLevelOrder();
		// Creating a binary tree
		TreeNode rootNode = ob.createBinaryTree();
		TreeUtils.drawTree(rootNode);
		System.out.println("Spiral/Zigzag traversal of binary tree :");
		ob.spiralOrZigzagLevelOrder(rootNode);
	}

	public TreeNode createBinaryTree() {

		TreeNode rootNode = new TreeNode(40);
		TreeNode node20 = new TreeNode(20);
		TreeNode node10 = new TreeNode(10);
		TreeNode node30 = new TreeNode(30);
		TreeNode node60 = new TreeNode(60);
		TreeNode node50 = new TreeNode(50);
		TreeNode node70 = new TreeNode(70);
		TreeNode node5 = new TreeNode(5);
		TreeNode node55 = new TreeNode(55);

		rootNode.left = node20;
		rootNode.right = node60;

		node20.left = node10;
		node20.right = node30;

		node60.left = node50;
		node60.right = node70;
		node10.left = node5;
		node50.right = node55;

		return rootNode;
	}
}
