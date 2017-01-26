package algorithm.trees;

import algorithm.utils.TreeUtils;

/*
 * Invert a binary tree.
	
	     4
	   /   \
	  2     7
	 / \    / \
	1   3 6   9
	to
	     4
	   /   \
	  7     2
	 / \    / \
	9   6 3   1
 */

public class MirrorBinaryTree {
	public TreeNode invertTree(TreeNode root) {
		if (root == null)
			return null;

		TreeNode tmp = root.left;
		root.left = invertTree(root.right);
		root.right = invertTree(tmp);
		return root;
	}

	public void invertTreeI(TreeNode root) {
		if (root == null)
			return;

		TreeNode temp = root.left;
		root.left = root.right;
		root.right = temp;

		invertTreeI(root.left);
		invertTreeI(root.right);
	}

	public static void main(String args[]) {
		MirrorBinaryTree mbt = new MirrorBinaryTree();
		System.out.println("-------------BEFORE-------------------");
		TreeNode header = TreeUtils.buildBstFromRange(1, 7);
		TreeUtils.drawTree(header);

		System.out.println("\n-------------AFTER-------------------");
		TreeNode mirrored = mbt.invertTree(header);
		TreeUtils.drawTree(mirrored);
		System.out.println();

		TreeNode header2 = TreeUtils.buildBstFromRange(1, 7);
		System.out.println("-------------BEFORE-------------------");
		TreeUtils.drawTree(header2);

		mbt.invertTreeI(header2);
		System.out.println("\n-------------AFTER-------------------");
		TreeUtils.drawTree(header2);
	}

}
