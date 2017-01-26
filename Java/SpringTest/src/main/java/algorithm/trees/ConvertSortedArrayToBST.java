package algorithm.trees;

import algorithm.utils.TreeUtils;

/*
 * Given an array where elements are sorted in ascending order, convert it
 * to a height balanced BST.
 */
public class ConvertSortedArrayToBST {
	public TreeNode sortedArrayToBST(int[] nums) {
		if (nums == null || nums.length == 0)
			return null;

		TreeNode root = sortedArrayToBST(nums, 0, nums.length - 1);
		return root;
	}

	private TreeNode sortedArrayToBST(int[] nums, int start, int end) {

		if (start > end)
			return null;

		int mid = (start + end) / 2;

		TreeNode left = sortedArrayToBST(nums, start, mid - 1);
		TreeNode root = new TreeNode(nums[mid]);
		TreeNode right = sortedArrayToBST(nums, mid + 1, end);

		root.left = left;
		root.right = right;

		return root;
	}

	public static void main(String[] args) {
		ConvertSortedArrayToBST ob = new ConvertSortedArrayToBST();
		int[] nums = { 1, 2, 3, 4, 5, 6 };
		TreeNode root = ob.sortedArrayToBST(nums);
		System.out.println(root);
		TreeUtils.drawTree(root);
	}

}
