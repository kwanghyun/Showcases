package algorithm.trees;

import algorithm.utils.TreeUtils;

public class MaxPathSumBST {

	public int maxPathSum(TreeNode root) {
		if (root == null)
			return 0;

		if (root.left == null && root.right == null)
			return root.val;

		int left = getMaxSum(root.left, 0);
		int right = getMaxSum(root.right, 0);

		System.out.println("left = " + left + ", right = " + right);

		if (root.val >= 0 && left > 0 && right > 0) {
			return root.val + left + right;
		} else if (root.val >= 0 && left > 0) {
			return root.val + left;
		} else if (root.val >= 0 && right > 0) {
			return root.val + right;
		} else if (root.val >= 0) {
			return root.val;
		}
		// all minus
		int max = Math.max((root.left == null) ? Integer.MIN_VALUE : root.left.val,
				(root.right == null) ? Integer.MIN_VALUE : root.right.val);
		max = Math.max(max, root.val);
		return max;
	}

	public int getMaxSum(TreeNode root, int sum) {
		if (root == null)
			return 0;

		if (sum + root.val < sum)
			return root.val;

		return root.val + Math.max(getMaxSum(root.left, sum), getMaxSum(root.right, sum));
	}

	public static void main(String[] args) {
		// TreeNode root = TreeUtils.buildBstFromRange(1, 2);
		// root.val = -3;
		// root.right.val = -2;
		TreeNode root = TreeUtils.buildBstFromRange(1, 3);
		TreeUtils.drawTree(root);
		MaxPathSumBST ob = new MaxPathSumBST();

		System.out.println(ob.maxPathSum(root));
	}
}
