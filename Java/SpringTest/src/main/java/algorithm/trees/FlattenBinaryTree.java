package algorithm.trees;

import java.util.Stack;

/*Given a binary tree, flatten it to a linked list in-place.
		 1
		/ \
	   2   5
	  / \   \
      3 4   6
The flattened tree should look like:
	1
	 \
	  2
 		\	
		 3
		  \
		   4
			\
			 5
			  \
			   6

Go down through the left, when right is not null, push right to stack.*/
public class FlattenBinaryTree {
	public void flatten(TreeNode root) {
		Stack<TreeNode> stack = new Stack<TreeNode>();
		TreeNode p = root;

		while (p != null || !stack.empty()) {

			if (p.right != null) {
				stack.push(p.right);
			}

			if (p.left != null) {
				p.right = p.left;
				p.left = null;
			} else if (!stack.empty()) {
				TreeNode temp = stack.pop();
				p.right = temp;
			}

			p = p.right;
		}
	}
}
