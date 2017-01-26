package algorithm.trees;

import algorithm.utils.TreeUtils;

/*
 * Given a binary tree, count the number of uni-value subtrees.
 * 
 * A Uni-value subtree means all nodes of the subtree have the same value.
 * 
 * For example:
 * Given binary tree,
	
	              5
	             / \
	            1   5
	           / \   \
	          5   5   5
	          
	return 4.
 */
public class CountUnivalueSubtrees {
	int count = 0;

	public int countUnivalSubtrees(TreeNode root) {
		if (root == null)
			return 0;

		countUnivalSubtreesUtil(root);
		return count;
	}

	private int countUnivalSubtreesUtil(TreeNode root) {
		if (root == null)
			return Integer.MAX_VALUE; // Mark if the root is null.

		int left = countUnivalSubtreesUtil(root.left);
		int right = countUnivalSubtreesUtil(root.right);

		if ((left == Integer.MAX_VALUE && right == Integer.MAX_VALUE)) {
			count++;
			return root.val;
		} else if (left != Integer.MAX_VALUE && right != Integer.MAX_VALUE) {
			if (root.val == left && root.val == right) {
				count++;
				return root.left.val;
			}
			return Integer.MIN_VALUE; // Mark the subtree is not valid.
		} else if (left != Integer.MAX_VALUE) {
			if (root.val == left) {
				count++;
				return root.left.val;
			}
			return Integer.MIN_VALUE;
		} else {
			if (root.val == right) {
				count++;
				return root.right.val;
			}
			return Integer.MIN_VALUE;
		}
	}

	public static void main(String[] args) {
		 int[] levelOrder = { 5, 5, 5, 5, 5, -1, 5 };
//		int[] levelOrder = { 9, 96, 96, 94, 58, 58, 94, -1, -75, -89, -1, -10 };
		TreeNode root = TreeUtils.buildLevelOrderBst(levelOrder);
		TreeUtils.drawTree(root);
		CountUnivalueSubtrees ob = new CountUnivalueSubtrees();
		System.out.println(ob.countUnivalSubtrees(root));
	}

}
