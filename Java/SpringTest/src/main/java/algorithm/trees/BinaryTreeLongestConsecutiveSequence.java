package algorithm.trees;

import algorithm.utils.TreeUtils;

/*
 * Given a binary tree, find the length of the longest consecutive sequence
 * path.
 * 
 * The path refers to any sequence of nodes from some starting node to any
 * node in the tree along the parent-child connections. The longest
 * consecutive path need to be from parent to child (cannot be the reverse).
 * 
 * For example,
	   1
	    \
	     3
	    / \
	   2   4
	        \
	         5

 * Longest consecutive sequence path is 3-4-5, so return 3.
	   2
	    \
	     3
	    / 
	   2    
	  / 
	 1 
 * Longest consecutive sequence path is 2-3,not3-2-1, so return 2.
 */
public class BinaryTreeLongestConsecutiveSequence {
	int maxLen = 1;

	public int longestConsecutive(TreeNode root) {
		if(root == null)
			return 0;
		dfs(root, Integer.MAX_VALUE, 1);
		return maxLen;
	}

	private void dfs(TreeNode root, int parentVal, int count) {
		if (root == null)
			return;

		int add = 0;
		if (root.val - parentVal == 1)
			add = 1;
		else
			count = 1;

		maxLen = Math.max(maxLen, count + add);

		if (root.left != null) {
			dfs(root.left, root.val, count + add);
		}

		if (root.right != null) {
			dfs(root.right, root.val, count + add);
		}
	}

	public static void main(String[] args) {
		BinaryTreeLongestConsecutiveSequence ob = new BinaryTreeLongestConsecutiveSequence();
		// TreeNode root =
		// TreeUtils.buildLevelOrderBst("[1,null,3,2,4,null,null,null,5]");
		TreeNode root = TreeUtils.buildLevelOrderBst("[2,null,3,2,null,1]");
		TreeUtils.drawTree(root);
		TreeUtils.printInorder(root);
		System.out.println("RESULT : " + ob.longestConsecutive(root));
	}
}
