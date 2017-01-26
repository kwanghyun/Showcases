package algorithm.trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import algorithm.utils.TreeUtils;

/*
 * Given a binary tree, return the bottom-up level order traversal of its
 * nodes' values. (ie, from left to right, level by level from leaf to
 * root).
 * 
 * For example:
 * 
 * Given binary tree [3,9,20,null,null,15,7],
	    3
	   / \
	  9  20
	    /  \
	   15   7
 * 
 * return its bottom-up level order traversal as:
	[
	  [15,7],
	  [9,20],
	  [3]
	]
 */
public class BinaryTreeLevelOrderTraversalII {
	public List<List<Integer>> levelOrderBottom(TreeNode root) {
		
		List<List<Integer>> result = new ArrayList<>();

		if(root == null)
			return result;

		Queue<TreeNode> q = new LinkedList<>();

		int height = getMaxDepth(root) - 1;

		for (int i = 0; i <= height; i++) {
			result.add(new ArrayList<>());
		}
		System.out.println(result.size());

		q.offer(root);

		while (!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				TreeNode curr = q.poll();
				result.get(height).add(curr.val);
				if (curr.left != null) {
					q.offer(curr.left);
				}
				if (curr.right != null) {
					q.offer(curr.right);
				}

			}
			height--;
		}
		return result;
	}

	public int getMaxDepth(TreeNode root) {
		if (root == null)
			return 0;
		return 1 + Math.max(getMaxDepth(root.left), getMaxDepth(root.right));
	}

	public static void main(String[] args) {
		BinaryTreeLevelOrderTraversalII ob = new BinaryTreeLevelOrderTraversalII();
		TreeNode root = TreeUtils.buildBstFromRange(1, 9);
		TreeUtils.drawTree(root);
		System.out.println(ob.levelOrderBottom(root));
	}
}
