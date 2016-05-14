package algorithm.trees;

/*
 * Given inorder and postorder traversal of a tree, construct the binary
 * tree.
 * 
 * Analysis
 * 
 * This problem can be illustrated by using a simple example.
 * 
 * in-order: 4 2 5 (1) 6 7 3 8
 * 
 * post-order: 4 5 2 6 7 8 3 (1)
 * 
 * From the post-order array, we know that last element is the root. We can
 * find the root in in-order array. Then we can identify the left and right
 * sub-trees of the root from in-order array.
 * 
 * Using the length of left sub-tree, we can identify left and right
 * sub-trees in post-order array. Recursively, we can build up the tree.
 *
 */
public class ConstructBinaryTreeFromInPostOrder {

	public TreeNode buildTree(int[] inorder, int[] postorder) {
		int inStart = 0;
		int inEnd = inorder.length - 1;
		int postStart = 0;
		int postEnd = postorder.length - 1;

		return buildTree(inorder, inStart, inEnd, postorder, postStart, postEnd);
	}

	public TreeNode buildTree(int[] inorder, int inStart, int inEnd, int[] postorder, int postStart, int postEnd) {
		if (inStart > inEnd || postStart > postEnd)
			return null;

		int rootValue = postorder[postEnd];
		TreeNode root = new TreeNode(rootValue);

		int rootIdx = 0;
		for (int i = 0; i < inorder.length; i++) {
			if (inorder[i] == rootValue) {
				rootIdx = i;
				break;
			}
		}

		root.left = buildTree(inorder, inStart, rootIdx - 1, postorder, postStart, postStart + rootIdx - (inStart + 1));
		// Becuase k is not the length, it it need to -(inStart+1) to get the
		// length
		root.right = buildTree(inorder, rootIdx + 1, inEnd, postorder, postStart + rootIdx - inStart, postEnd - 1);
		// postStart+k-inStart = postStart+k-(inStart+1) +1

		return root;
	}
}
