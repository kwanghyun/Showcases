package algorithm.trees;

import algorithm.utils.TreeUtils;

/*
 * Two elements of a binary search tree (BST) are swapped by mistake.
 * Recover the tree without changing its structure.
 * 
 * Java Solution
 * 
 * Inorder traveral will return values in an increasing order. So if an
 * element is less than its previous element,the previous element is a
 * swapped node.
 */
public class RecoverBST {
	TreeNode first;
	TreeNode second;
	TreeNode pre;

	public void inorder(TreeNode root) {
		if (root == null)
			return;

		inorder(root.left);

		if (pre == null) {
			pre = root;
		} else {
			if (root.value < pre.value) {
				if (first == null) {
					first = pre;
				}

				second = root;
			}
			pre = root;
		}

		inorder(root.right);
	}

	public void recoverTree(TreeNode root) {
		if (root == null)
			return;

		inorder(root);
		if (second != null && first != null) {
			int val = second.value;
			second.value = first.value;
			first.value = val;
		}
	}

	public static void main(String[] args) {
		RecoverBST ob = new RecoverBST();
		TreeNode root = TreeUtils.buildInOrderAscendingTree2_element_missplaced();
		System.out.println("===Before Fix===");
		TreeUtils.printInorder(root);
		ob.recoverTree(root);
		System.out.println("\n===After Fix===");
		TreeUtils.printInorder(root);
	}
}
