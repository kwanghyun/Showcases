package algorithm.trees;

import java.util.ArrayList;
import java.util.Stack;

public class InOrder {
	public ArrayList<Integer> inorderTraversal(TreeNode root) {
		// IMPORTANT: Please reset any member data you declared, as
		// the same Solution instance will be reused for each test case.
		ArrayList<Integer> list = new ArrayList<Integer>();

		if (root == null)
			return list;

		Stack<TreeNode> stack = new Stack<TreeNode>();
		// define a pointer to track nodes
		TreeNode pointer = root;

		while (!stack.empty() || pointer != null) {

			// if it is not null, push to stack
			// and go down the tree to left
			if (pointer != null) {
				stack.push(pointer);
				pointer = pointer.left;

				// if no left child
				// pop stack, process the node
				// then let p point to the right
			} else {
				TreeNode temp = stack.pop();
				list.add(temp.val);
				pointer = temp.right;
			}
		}

		return list;
	}
}
