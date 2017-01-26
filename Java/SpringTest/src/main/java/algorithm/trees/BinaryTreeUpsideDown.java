package algorithm.trees;

/*
 * Given a binary tree where all the right nodes are either leaf nodes with
 * a sibling (a left node that shares the same parent node) or empty, flip
 * it upside down and turn it into a tree where the original right nodes
 * turned into left leaf nodes. Return the new root.
 * 
 * For example:
 * 
 * Given a binary tree {1,2,3,4,5},

	    1
	   / \
	  2   3
	 / \
	4   5
	return the root of the binary tree [4,5,2,#,#,3,1].
	   4
	  / \
	 5   2
	    / \
	   3   1  
 */
public class BinaryTreeUpsideDown {

	public TreeNode upsideDownBinaryTree(TreeNode root) {
		if(root == null)
			return null;
		return upsideDownBinaryTree(root, null);
	}

	public TreeNode upsideDownBinaryTree(TreeNode root, TreeNode parent) {
		if (root == null)
			return parent;

		TreeNode newRoot = upsideDownBinaryTree(root.left, root);
		root.left = (parent == null) ? null : parent.right;
		root.right = parent;

		return newRoot;
	}

}
