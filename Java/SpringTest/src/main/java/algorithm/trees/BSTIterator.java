package algorithm.trees;

import java.util.Stack;

/*Implement an iterator over a binary search tree (BST). Your iterator will be initialized
 with the root node of a BST. Calling next() will return the next smallest number in
 the BST. Note: next() and hasNext() should run in average O(1) time and uses O(h)
 memory, where h is the height of the tree.
		 8
		/  \
	   3   10
	  / \     \
	1   6    14
	    / \    /
       4  7  13
 The key to solve this problem is understanding what is BST.*/
public class BSTIterator {

	Stack<TreeNode> stack;

	public BSTIterator(TreeNode root) {
		
		stack = new Stack<TreeNode>();
		
		while (root != null) {
			stack.push(root);
			root = root.left;
		}
	}

	public boolean hasNext() {
		return !stack.isEmpty();
	}

	public int next() {
		TreeNode node = stack.pop();
		int result = node.value;
		
		if (node.right != null) {
			node = node.right;
			
			while (node != null) {
				stack.push(node);
				node = node.left;
			}
		}
		return result;
	}
}
