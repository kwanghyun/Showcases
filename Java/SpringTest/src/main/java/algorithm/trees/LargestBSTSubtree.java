package algorithm.trees;

import algorithm.utils.TreeUtils;

/*
 * Given a binary tree, find the largest subtree which is a Binary Search
 * Tree (BST), where largest means subtree with largest number of nodes in
 * it.
 * 
 * Note: A subtree must include all of its descendants. Here's an example:
 * 
	    10
	    / \
	   5  15
	  / \   \ 
	 1   8   7
 * 
 * The Largest BST Subtree in this case is the highlighted one. The return
 * value is the subtree's size, which is 3.
 * 
 * Follow up:
 * Can you figure out ways to solve it with O(n) time complexity?
 */

/*
 * How can I check valid BST in the below case from bottom up approach
             3 
             /  \ 
         2          4 
                    / 
               1      
*/

public class LargestBSTSubtree {
	int maxCount = 0;

	public int largestBSTSubtree(TreeNode root) {
		int max = 0;
		if (root == null)
			return max;

		maxCount = Math.max(maxCount, dfs(root, Integer.MIN_VALUE, Integer.MAX_VALUE));
		largestBSTSubtree(root.left);
		largestBSTSubtree(root.right);
		return maxCount;
	}

	public int dfs(TreeNode root, int min, int max) {
		if (root == null)
			return 0;

		if (root.val < min || root.val > max) {
			return -1;
		}
		int left = dfs(root.left, min, root.val);
		int right = dfs(root.right, root.val, max);

		if (left == -1 || right == -1)
			return -1;

		return 1 + left + right;
	}

	public int largestBSTSubtreeI(TreeNode root) {
		int count = largestBSTSubtreeUtil(root);
		System.out.println("count = " + count);
		return maxCount;
	}

	public int largestBSTSubtreeUtil(TreeNode root) {
		if (root == null)
			return 0;
		int left = largestBSTSubtreeUtil(root.left);
		int right = largestBSTSubtreeUtil(root.right);
		System.out.println("## root = " + root.val + ", left= " + left + ", right = " + right);

		if (left == -1 || right == -1) {
			left = 0;
			right = 0;

		}

		boolean isInvalid = false;

		if (root.left != null && root.right != null) {
			if (root.val > root.left.val && root.val < root.right.val) {
			} else {
				isInvalid = true;
			}

		} else if (root.left != null) {
			if (root.val > root.left.val) {
			} else {
				isInvalid = true;
			}

		} else if (root.right != null) {
			if (root.val < root.right.val) {
			} else {
				isInvalid = true;
			}
		}

		if (isInvalid) {
			return -1;
		}
		maxCount = Math.max(maxCount, 1 + left + right);
		return 1 + left + right;
	}

	public static void main(String[] args) {
		LargestBSTSubtree ob = new LargestBSTSubtree();
//		 TreeNode root = TreeUtils.buildLevelOrderBst("[2,3,null,1]");
//		TreeNode root = TreeUtils.buildLevelOrderBst("[10,5,15,1,8,null,7]");
		 TreeNode root = TreeUtils.buildLevelOrderBst("[3,2,4,null,null,1]");

		// TreeNode root = TreeUtils.buildLevelOrderBst("[1]");

		TreeUtils.drawTree(root);

		System.out.println(ob.largestBSTSubtree(root));
	}
}
