package algorithm.trees;

/*
 * Given preorder and inorder traversal of a tree, construct the binary
 * tree.
 * 
 * in-order: 4 2 5 (1) 6 7 3 8
 * 
 * pre-order: (1) 2 4 5 3 7 6 8
 * 
 * From the pre-order array, we know that first element is the root. We can
 * find the root in in-order array. Then we can identify the left and right
 * sub-trees of the root from in-order array.
 * 
 * Using the length of left sub-tree, we can identify left and right
 * sub-trees in pre-order array. Recursively, we can build up the tree.
 */
public class ConstructBinaryTreeFromPreInOrder {
	public TreeNode construct(int[] preorder, int preStart, int preEnd, int[] inorder, int inStart, int inEnd) {
		if (preStart > preEnd || inStart > inEnd) {
			return null;
		}

		int val = preorder[preStart];
		TreeNode p = new TreeNode(val);

		// find parent element index from inorder
		int rootIdx = 0;
		for (int i = 0; i < inorder.length; i++) {
			if (val == inorder[i]) {
				rootIdx = i;
				break;
			}
		}

		p.left = construct(preorder, preStart + 1, preStart + (rootIdx - inStart), inorder, inStart, rootIdx - 1);
		p.right = construct(preorder, preStart + (rootIdx - inStart) + 1, preEnd, inorder, rootIdx + 1, inEnd);

		return p;
	}
	
	public TreeNode buildTree(int[] preorder, int[] inorder) {
	    int preStart = 0;
	    int preEnd = preorder.length-1;
	    int inStart = 0;
	    int inEnd = inorder.length-1;
	 
	    return construct(preorder, preStart, preEnd, inorder, inStart, inEnd);
	}
	
}
