package algorithm.trees;

import java.util.ArrayList;
import java.util.List;

import algorithm.utils.TreeUtils;

/*
 * Given a binary tree, return all root-to-leaf paths.
 * 
 * For example, given the following binary tree:

     1
   /   \
  2     3
   \
    5
 * All root-to-leaf paths are:
 * 
 * ["1->2->5", "1->3"]
 */
public class BinaryTreePaths {
	public List<String> binaryTreePaths(TreeNode root) {
		List<String> result = new ArrayList<>();
		if(root == null)
			return result;
		
		StringBuilder sb = new StringBuilder();
		sb.append(root.val);
		dfs(root, sb, result);
		return result;
	}

	public void dfs(TreeNode root, StringBuilder sb, List<String> result) {
		if (root.left == null && root.right == null) {
			result.add(sb.toString());
			return;
		}

		if (root.left != null) {
			String nodeVal = Integer.toString(root.left.val);
			sb.append("->" + nodeVal);
			dfs(root.left, sb, result);
			sb.setLength(sb.length() - (nodeVal.length() + 2));
		}

		if (root.right != null) {
			String nodeVal = Integer.toString(root.right.val);
			sb.append("->" + nodeVal);
			dfs(root.right, sb, result);
			sb.setLength(sb.length() - (nodeVal.length() + 2));
		}

	}

	public static void main(String[] args) {
		BinaryTreePaths ob = new BinaryTreePaths();
		int[] levelOrder = { 1, 2, 3, -1, 5 };
		TreeNode root = TreeUtils.buildLevelOrderBst(levelOrder);
		TreeUtils.drawTree(root);
		System.out.println(ob.binaryTreePaths(root));
	}

}
