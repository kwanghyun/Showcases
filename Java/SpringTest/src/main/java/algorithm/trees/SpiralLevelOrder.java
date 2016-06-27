package algorithm.trees;

import java.util.Stack;

/*
 * Given a binary tree, write a program to print nodes of the tree in spiral
 * order. For example, for the following tree
 * 			 1
 * 			/  \
 * 		  2     3
 *      /  \   /  \
 *    7   6  5   4 
 * output should be 1, 2, 3, 4, 5, 6, 7.
 */
public class SpiralLevelOrder {
	private void spiralTraversal(TreeNode root) {
		if (root == null) {
			return;
		}

		// for storing and printing nodes at even level
		Stack<TreeNode> stackEven = new Stack<>();

		// for storing and printing nodes at odd level
		Stack<TreeNode> stackOdd = new Stack<>();

		// root node is considered at level 0.
		stackEven.push(root);

		boolean evenLevel = true;

		// traverse while there are nodes to visit in the current-level stack
		// empty current-level stack indicates that all nodes of the tree are
		// visited
		while ((evenLevel && !stackEven.isEmpty()) || (!stackOdd.isEmpty())) {
			// if current level to be visited is even
			if (evenLevel) {
				while (!stackEven.isEmpty()) {
					TreeNode currentNode = stackEven.pop();

					System.out.print(" " + currentNode.value);

					// first push the right child
					if (currentNode.right != null) {
						stackOdd.push(currentNode.right);
					}

					// then push the left child
					if (currentNode.left != null) {
						stackOdd.push(currentNode.left);
					}
				}
			} else {
				while (!stackOdd.isEmpty()) {
					TreeNode currentNode = stackOdd.pop();

					System.out.print(" " + currentNode.value);

					// first push the left child
					if (currentNode.left != null) {
						stackEven.push(currentNode.left);
					}

					// then push the right child
					if (currentNode.right != null) {
						stackEven.push(currentNode.right);
					}
				}
			}

			// if current level is even switch to odd and vice versa
			evenLevel = !evenLevel;
			System.out.println();
		}
	}
}
