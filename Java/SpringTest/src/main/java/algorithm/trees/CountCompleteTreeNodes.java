package algorithm.trees;

/*
 * Given a complete binary tree, count the number of nodes.
 * 
 * Analysis
 * 
 * Steps to solve this problem: 
 * 1) get the height of left-most part 
 * 2) get the height of right-most part 
 * 3) when they are equal, the # of nodes = 2^h -1 4) 
 * when they are not equal, recursively get # of nodes from left&right sub-trees
 * 
 * Time complexity is O(h^2).
 */
public class CountCompleteTreeNodes {
	public int countNodes(TreeNode root) {
		if (root == null)
			return 0;

		int left = getLeftHeight(root) + 1;
		int right = getRightHeight(root) + 1;

		if (left == right) {
			return (2 << (left - 1)) - 1;
		} else {
			return countNodes(root.left) + countNodes(root.right) + 1;
		}
	}

	public int getLeftHeight(TreeNode n) {
		if (n == null)
			return 0;

		int height = 0;
		while (n.left != null) {
			height++;
			n = n.left;
		}
		return height;
	}

	public int getRightHeight(TreeNode n) {
		if (n == null)
			return 0;

		int height = 0;
		while (n.right != null) {
			height++;
			n = n.right;
		}
		return height;
	}
}
