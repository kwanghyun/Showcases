package algorithm.trees;

/*
 * Problem:
 Given a binary tree, determine if it is a valid binary search tree (BST).
 Assume a BST is defined as follows:
 • The left subtree of a node contains only nodes with keys less than the node’s key.
 • The right subtree of a node contains only nodes with keys greater than the node’s
 key.
 • Both the left and right subtrees must also be binary search trees.
 
	 Example 1:
	    2
	   / \
	  1   3
	Binary tree [2,1,3], return true.

	Example 2:
	    1
	   / \
	  2   3
	Binary tree [1,2,3], return false.
 */
public class ValidateBinarySearchTree {
	public static boolean isValidBST(TreeNode root) {
		return validate(root, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
	}

	public static boolean validate(TreeNode root, double min, double max) {
		if (root == null) {
			return true;
		}

		// not in range
		if (root.val <= min || root.val >= max) {
			return false;
		}

		// left subtree must be < root.val && right subtree must be > root.val
		return validate(root.left, min, root.val)
				&& validate(root.right, root.val, max);
	}
}
