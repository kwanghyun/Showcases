package algorithm.trees;

import algorithm.utils.TreeUtils;

/*
 * Given preorder and inorder traversal of a tree, construct the binary
 * tree.
 * 
 * pre-order: 4, 2, 1, 3, 6, 5, 7
 * in-order: 1, 2, 3, 4, 5, 6, 7
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
		int divIdx = 0;
		for (int i = 0; i < inorder.length; i++) {
			if (val == inorder[i]) {
				divIdx = i;
				break;
			}
		}
		
		p.left = construct(preorder, preStart + 1, preStart + (divIdx - inStart), inorder, inStart, divIdx - 1);
		p.right = construct(preorder, preStart + (divIdx - inStart) + 1, preEnd, inorder, divIdx + 1, inEnd);

		return p;
	}


	public TreeNode buildTree(int[] preorder, int[] inorder) {
		int preStart = 0;
		int preEnd = preorder.length - 1;
		int inStart = 0;
		int inEnd = inorder.length - 1;

		return construct(preorder, preStart, preEnd, inorder, inStart, inEnd);
	}

	public static void main(String[] args) {
		ConstructBinaryTreeFromPreInOrder ob = new ConstructBinaryTreeFromPreInOrder();

		int[] preorder = { 4, 2, 1, 3, 6, 5, 7 };
		int[] inorder = { 1, 2, 3, 4, 5, 6, 7 };
		TreeNode root = ob.buildTree(preorder, inorder);
		TreeUtils.printPreorder(root);
		System.out.println();
		TreeUtils.drawTree(root);
		System.out.println();
		TreeUtils.printInorder(root);
	}

}
