package algorithm.trees;

public class Utils {

	static public void printPreOrder(TreeNode root) {
		if (root == null)
			return;
		System.out.print("[" + root.value + "] ");
		printPreOrder(root.left);
		printPreOrder(root.right);
	}

	static public void printInOrder(TreeNode root) {
		if (root == null)
			return;
		printInOrder(root.left);
		System.out.print("[" + root.value + "] ");
		printInOrder(root.right);
	}

	static public TreeNode buildInOrderAscendingTree() {
		int[] inorder = { 1, 2, 3, 4, 5, 6, 7 };
		int[] preorder = { 4, 3, 1, 2, 6, 5, 7 };
		return buildInPreorderTree(inorder, preorder);
	}

	static public TreeNode buildInOrderAscendingTree2_element_missplaced() {
		int[] inorder = { 1, 2, 6, 4, 5, 3, 7 };
		int[] preorder = { 4, 6, 1, 2, 3, 5, 7 };
		return buildInPreorderTree(inorder, preorder);
	}

	static public TreeNode buildInPreorderTree(int[] inorder, int[] preorder) {
		if (inorder == null || preorder == null || inorder.length != preorder.length)
			return null;

		ConstructBinaryTreeFromPreInOrder inPostConst = new ConstructBinaryTreeFromPreInOrder();
		return inPostConst.buildTree(preorder, inorder);
	}

	static public TreeNode buildInPostorderTree(int[] inorder, int[] postorder) {
		if (inorder == null || postorder == null || inorder.length != postorder.length)
			return null;

		ConstructBinaryTreeFromInPostOrder inPostConst = new ConstructBinaryTreeFromInPostOrder();
		return inPostConst.buildTreeI(inorder, postorder);
	}

	public static void main(String[] args) {
		printInOrder(buildInOrderAscendingTree());
	}
}
