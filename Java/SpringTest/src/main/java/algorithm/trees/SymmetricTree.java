package algorithm.trees;

/*
 * Symmetric Tree

Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its
center).
For example, this binary tree is symmetric:
		1
	   / \
	  2   2
     / \ / \
    3 4 4 3
But the following is not:
		1
       / \
	  2  2
	   \   \
	    3   3

This problem can be solve by using a simple recursion. The key is finding the conditions
that return false, such as value is not equal, only one node(left or right) has
value.
*/
public class SymmetricTree {
	public boolean isSymmetric(TreeNode root) {
		if (root == null)
			return true;

		return isSymmetric(root.left, root.right);
	}

	public boolean isSymmetric(TreeNode node1, TreeNode node2) {
		if (node1 == null && node2 == null) {
			return true;
		} else if (node2 == null || node1 == null) {
			return false;
		} else if (node1.value != node2.value) {
			return false;
		}
		return isSymmetric(node1.left, node2.right) && isSymmetric(node1.right, node2.left);
	}
}
