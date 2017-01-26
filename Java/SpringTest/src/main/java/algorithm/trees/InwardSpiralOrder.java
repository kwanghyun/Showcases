package algorithm.trees;

import algorithm.utils.TreeUtils;

/*
 * Given a binary tree print it in inward spiral order i.e first print level
 * 1, then level n, then level 2, then n-1 and so on.
 * 
 * For Ex: 
 *               1 
 *       2               3 
 *   4     5        6       7 
 * 8 9 10 11 12 13 14 15
 * 
 * Should Output: 1 15 14 13 12 11 10 9 8 2 3 7 6 5 4
 */

public class InwardSpiralOrder {
	public void printLevel(TreeNode root, int level, boolean ltr) {

		if (root == null)
			return;
		if (level == 1)
			System.out.print(root.val + " ");

		else if (ltr == true) {

			printLevel(root.left, level - 1, ltr);
			printLevel(root.right, level - 1, ltr);
		} else {
			printLevel(root.right, level - 1, ltr);
			printLevel(root.left, level - 1, ltr);

		}

	}

	public int height(TreeNode root) {
		if (root == null)
			return 0;

		return 1 + Math.max(height(root.left), height(root.right));
	}

	public void inwardSpiral(TreeNode root) {

		int h = height(root);
		System.out.println("h = " + h);
		int firstLevel = 1;
		int lastLevel = h;

		while (firstLevel < lastLevel) {

			printLevel(root, firstLevel, true);
			printLevel(root, lastLevel, false);
			firstLevel++;
			lastLevel--;
		}
	}

	public static void main(String[] args) {
		TreeNode root = TreeUtils.buildBstFromRange(1, 15);
		TreeUtils.drawTree(root);

		InwardSpiralOrder ob = new InwardSpiralOrder();
		ob.inwardSpiral(root);

	}
}
