package algorithm.trees;

/*
 * Problem:
 Given a binary tree, determine if it is a valid binary search tree (BST).
 Assume a BST is defined as follows:
 • The left subtree of a node contains only nodes with keys less than the node’s key.
 • The right subtree of a node contains only nodes with keys greater than the node’s
 key.
 • Both the left and right subtrees must also be binary search trees.
 
 All values on the left sub tree must be less than root, and all values on the right sub
tree must be greater than root.
 */
public class ValidateBinarySearchTree {
	public static boolean isValidBST(TreeNode root) {
		return validate(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	public static boolean validate(TreeNode root, int min, int max) {
		if (root == null) {
			return true;
		}

		// not in range
		if (root.value <= min || root.value >= max) {
			return false;
		}

		// left subtree must be < root.val && right subtree must be > root.val
		return validate(root.left, min, root.value)
				&& validate(root.right, root.value, max);
	}
}
