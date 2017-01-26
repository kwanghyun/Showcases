package algorithm.trees;

/*
 * Given a binary search tree (BST), find the lowest common ancestor (LCA)
 * of two given nodes in the BST.
 * 
 * According to the definition of LCA on Wikipedia: “The lowest common
 * ancestor is defined between two nodes v and w as the lowest node in T
 * that has both v and w as descendants (where we allow a node to be a
 * descendant of itself).”
 * 
	        _______6______
	       /                      \
	    ___2__          ___8__
	   /      \               /      \
	   0      4            7       9
	         /  \
	         3   5
 * 
 * For example, the lowest common ancestor (LCA) of nodes 2 and 8 is 6.
 * Another example is LCA of nodes 2 and 4 is 2, since a node can be a
 * descendant of itself according to the LCA definition.
 */

public class LowestCommonAncesterInBinarySearhTree {
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
}
