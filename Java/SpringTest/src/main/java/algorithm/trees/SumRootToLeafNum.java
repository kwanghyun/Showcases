package algorithm.trees;

import java.util.ArrayList;

import algorithm.utils.TreeUtils;

/*Given a binary tree containing digits from 0-9 only, each root-to-leaf path could
 represent a number. Find the total sum of all root-to-leaf numbers.
 For example,
   1
  / \
 2  3
 The root-to-leaf path 1->2 represents the number 12. The root-to-leaf path 1->3
 represents the number 13. Return the sum = 12 + 13 = 25.

 This problem can be solved by a typical DFS approach. 	
 */
public class SumRootToLeafNum {

	public int sumNumbers(TreeNode root) {

		if (root == null)
			return 0;
		return dfs(root, 0);
	}

	public int dfs(TreeNode root, int num) {
		if (root == null)
			return 0;

		num = num * 10 + root.value;

		if (root.left == null && root.right == null) {
			return num;
		}
		return dfs(root.left, num) + dfs(root.right, num);
	}

	public int sumNumbersI(TreeNode root) {
		if (root == null)
			return 0;
		return dfsI(root, 0, 0);
	}

	public int sumNumbersII(TreeNode root) {

		if (root == null)
			return 0;
		return dfsII(root, 0, 0);
	}

	public int dfsI(TreeNode node, int num, int sum) {

		if (node == null)
			return 0;

		num = num * 10 + node.value;

		// leaf
		if (node.left == null && node.right == null) {
			sum += num;
			return sum;
		}

		// left subtree + right subtree
		sum = dfsI(node.left, num, sum) + dfsI(node.right, num, sum);
		return sum;
	}

	public int dfsII(TreeNode node, int num, int sum) {
		if (node == null)
			return 0;

		num = num * 10 + node.value;

		if (node.left == null && node.right == null) {
			sum += num;
			return sum;
		}

		return dfsII(node.left, num, sum) + dfsII(node.right, num, sum);
	}

	public static void main(String[] args) {
		SumRootToLeafNum ob = new SumRootToLeafNum();
		int[] inorder = { 2, 1, 3 };
		int[] preorder = { 1, 2, 3 };
		// TreeNode root = TreeUtils.buildInPreorderTree(inorder, preorder);
		TreeNode root = TreeUtils.buildBstFromRange(1, 7);
		TreeUtils.drawTree(root);
		System.out.println("---------------------sumNumbers------------------------");
		System.out.println(ob.sumNumbers(root));
		System.out.println("---------------------sumNumbersI------------------------");
		System.out.println(ob.sumNumbersI(root));
		System.out.println("---------------------sumNumbersII------------------------");
		System.out.println(ob.sumNumbersII(root));

	}
}
