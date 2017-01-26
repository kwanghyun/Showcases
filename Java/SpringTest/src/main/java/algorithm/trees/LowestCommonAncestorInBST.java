package algorithm.trees;

/*
 * Given values of two TreeNodes in a Binary Tree, find the Lowest Common
 * Ancestor (LCA) (Using Parent Pointer). It may be assumed that both TreeNodes
 * exist in the tree.
 */

import java.util.HashMap;
import java.util.Map;

import algorithm.utils.TreeUtils;

class LowestCommonAncestorInBST {
	TreeNode root, n1, n2, lca;

	/*
	 * A utility function to insert a new TreeNode with given key in Binary
	 * Search Tree
	 */
	public TreeNode insert(TreeNode TreeNode, int key) {
		/* If the tree is empty, return a new TreeNode */
		if (TreeNode == null)
			return new TreeNode(key);

		/* Otherwise, recur down the tree */
		if (key < TreeNode.val) {
			TreeNode.left = insert(TreeNode.left, key);
			TreeNode.left.parent = TreeNode;
		} else if (key > TreeNode.val) {
			TreeNode.right = insert(TreeNode.right, key);
			TreeNode.right.parent = TreeNode;
		}

		/* return the (unchanged) TreeNode pointer */
		return TreeNode;
	}

	// To find LCA of TreeNodes n1 and n2 in Binary Tree
	public TreeNode findLCA(TreeNode n1, TreeNode n2) {
		// Creata a map to store ancestors of n1
		Map<TreeNode, Boolean> map = new HashMap<TreeNode, Boolean>();

		// Insert n1 and all its ancestors in map
		while (n1 != null) {
			map.put(n1, Boolean.TRUE);
			n1 = n1.parent;
		}

		// Check if n2 or any of its ancestors is in
		// map.
		while (n2 != null) {
			if (map.containsKey(n2) != map.isEmpty())
				return n2;
			n2 = n2.parent;
		}

		return null;
	}

	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

		if (root == null)
			return null;

		TreeNode left = null;
		TreeNode right = null;

		if (root.val > p.val && root.val > q.val) {
			left = lowestCommonAncestor(root.left, p, q);
		} else if (root.val < p.val && root.val < q.val) {
			right = lowestCommonAncestor(root.right, p, q);
		} else {
			return root;
		}

		return left == null ? right : left;
	}

	// Driver method to test above functions
	public static void main(String[] args) {
		LowestCommonAncestorInBST tree = new LowestCommonAncestorInBST();
		tree.root = tree.insert(tree.root, 20);
		tree.root = tree.insert(tree.root, 8);
		tree.root = tree.insert(tree.root, 22);
		tree.root = tree.insert(tree.root, 4);
		tree.root = tree.insert(tree.root, 12);
		tree.root = tree.insert(tree.root, 10);
		tree.root = tree.insert(tree.root, 14);

		TreeUtils.drawTree(tree.root);

		tree.n1 = tree.root.left.right.left;
		tree.n2 = tree.root.right;
		tree.lca = tree.findLCA(tree.n1, tree.n2);

		System.out.println("LCA of " + tree.n1.val + " and " + tree.n2.val + " is " + tree.lca.val);
		System.out.println("LCA without parent of " + tree.lowestCommonAncestor(tree.root, tree.n1, tree.n2));
	}
}
