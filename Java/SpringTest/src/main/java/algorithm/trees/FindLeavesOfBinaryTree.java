package algorithm.trees;

import java.util.*;

import algorithm.utils.TreeUtils;

/*
 * Given a binary tree, collect a tree's nodes as if you were doing this:
 * Collect and remove all leaves, repeat until the tree is empty.
 * 
 * Example:
 * 
 * Given binary tree
          1
         / \
        2   3
       / \     
      4   5    
      
 * Returns [4, 5, 3], [2], [1].
 * 
 * Explanation:
 * 
 * 1. Removing the leaves [4, 5, 3] would result in this tree:
          1
         / 
        2 
 * 
 * 2. Now removing the leaf [2] would result in this tree:
          1  
 * 
 * 3. Now removing the leaf [1] would result in the empty tree:
          [] 
 * Returns [4, 5, 3], [2], [1].
 */
public class FindLeavesOfBinaryTree {

	public List<List<Integer>> findLeaves(TreeNode root) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		findLeaves(result, root);
		return result;
	}

	// traverse the tree bottom-up recursively
	private int findLeaves(List<List<Integer>> list, TreeNode root) {
		if (root == null)
			return -1;

		int left = findLeaves(list, root.left);
		int right = findLeaves(list, root.right);
		int curr = Math.max(left, right) + 1;

		// the first time this code is reached is when curr==0,
		// since the tree is bottom-up processed.
		if (list.size() <= curr) {
			list.add(new ArrayList<Integer>());
		}

		list.get(curr).add(root.val);

		return curr;
	}

	public List<List<Integer>> findLeavesI(TreeNode root) {
		if (root == null)
			return new ArrayList<>();
		List<List<Integer>> result = new ArrayList<>();
		Set<TreeNode> foundLeaves = new HashSet<>();
		while (!foundLeaves.contains(root)) {
			List<Integer> list = new ArrayList<>();
			findLeaves(root, list, foundLeaves);
			result.add(list);
		}
		return result;
	}

	private void findLeaves(TreeNode root, List<Integer> list, Set<TreeNode> foundLeaves) {
		if (root == null)
			return;

		if (foundLeaves.contains(root))
			return;

		if (root.left == null && root.right == null) {
			list.add(root.val);
			foundLeaves.add(root);
		} else if (root.left != null && root.right != null) {
			if (foundLeaves.contains(root.left) && foundLeaves.contains(root.right)) {
				list.add(root.val);
				foundLeaves.add(root);
			}
		} else if (root.left != null) {
			if (foundLeaves.contains(root.left)) {
				list.add(root.val);
				foundLeaves.add(root);
			}
		} else if (root.right != null) {
			if (foundLeaves.contains(root.right)) {
				list.add(root.val);
				foundLeaves.add(root);
			}
		}
		findLeaves(root.left, list, foundLeaves);
		findLeaves(root.right, list, foundLeaves);
	}

	public static void main(String[] args) {
		TreeNode root = TreeUtils.buildLevelOrderBst("[1,2,3,4,5]");
		TreeUtils.drawTree(root);
		FindLeavesOfBinaryTree ob = new FindLeavesOfBinaryTree();
		System.out.println(ob.findLeaves(root));
	}

}
