package algorithm.trees;

import algorithm.utils.TreeUtils;

/*
 * Given a binary search tree (BST), how can you serialize and deserialize
 * it in O(n) time?
 * 
 * For example, for the following BST, 5,2,1,3,4,7,6,8
 */
public class SerializeDeserialize {

	public String serialize(TreeNode root) {
		if (root == null)
			return "";

		String result = root.val + " ";
		result = result + serialize(root.left);
		result = result + serialize(root.right);
		return result;
	}

	public TreeNode buildBstFromPreorder(int[] preorder) {
		return buildBstFromPreorder(preorder, 0, preorder.length - 1);
	}

	private int findDivision(int[] preoder, int start, int end) {
		int idx;
		for (idx = start + 1; idx < end; idx++) {
			if (preoder[start] < preoder[idx]) {
				break;
			}
		}
		return idx;
	}

	private TreeNode buildBstFromPreorder(int[] preoder, int start, int end) {
		if (start > end)
			return null;

		int divisionIdx = findDivision(preoder, start, end);
		TreeNode node = new TreeNode(preoder[start]);
		node.left = buildBstFromPreorder(preoder, start + 1, divisionIdx - 1);
		node.right = buildBstFromPreorder(preoder, divisionIdx, end);
		return node;
	}

	private TreeNode deserializeArrayOptimized(int[] preorder, int[] currIndex, int min, int max) {
		if (currIndex[0] >= preorder.length)
			return null;

		TreeNode root = null;

		if ((preorder[currIndex[0]] > min) && (preorder[currIndex[0]] < max)) {
			root = new TreeNode(preorder[currIndex[0]]);
			currIndex[0] += 1;
			root.left = deserializeArrayOptimized(preorder, currIndex, min, root.val);
			root.right = deserializeArrayOptimized(preorder, currIndex, root.val, max);
		}

		return root;
	}

	private int findDivision(int[] preorder, int value, int low, int high) {
		int i;
		for (i = low; i <= high; i++) {
			if (value < preorder[i])
				break;
		}
		return i;
	}

	private TreeNode deserializeArray(int[] preorder, int low, int high) {
		if (low > high)
			return null;

		TreeNode root = new TreeNode(preorder[low]);

		int divIndex = findDivision(preorder, root.val, low + 1, high);

		root.left = deserializeArray(preorder, low + 1, divIndex - 1);
		root.right = deserializeArray(preorder, divIndex, high);

		return root;
	}

	public static void main(String[] args) {

		int[] preorder = { 5, 2, 1, 3, 4, 7, 6, 8 };

		SerializeDeserialize solution = new SerializeDeserialize();

		int[] currIndex = new int[1];
		currIndex[0] = 0;

		int min = Integer.MIN_VALUE;
		int max = Integer.MAX_VALUE;

		TreeNode rootO = solution.deserializeArrayOptimized(preorder, currIndex, min, max);
		TreeUtils.drawTree(rootO);

		System.out.print("Serialized Result : ");
		System.out.println(solution.serialize(rootO));

		TreeNode root = solution.buildBstFromPreorder(preorder, 0, preorder.length - 1);
		TreeUtils.drawTree(root);

		System.out.print("Inorder array for constructed BST is:");
		TreeUtils.printInorder(root);
		System.out.println("");
		TreeUtils.printInorder(rootO);

		System.out.println("");

		System.out.print("Preorder array for constructed BST is:");
		TreeUtils.printPreorder(root);

		System.out.println("");
		TreeUtils.printPreorder(rootO);
		System.out.println("\nPostorder");
		TreeUtils.printPostorder(root);
	}

}
