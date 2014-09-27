package algorithm.trees;

import java.util.Stack;

public class FlattenBinaryTree {
	public void flatten(TreeNode root) {
		Stack<TreeNode> stack = new Stack<TreeNode>();
		TreeNode pointer = root;

		while (pointer != null || !stack.empty()) {

			if (pointer.right != null) {
				stack.push(pointer.right);
			}

			if (pointer.left != null) {
				pointer.right = pointer.left;
				pointer.left = null;
			} else if (!stack.empty()) {
				TreeNode temp = stack.pop();
				pointer.right = temp;
			}

			pointer = pointer.right;
		}
	}
}
