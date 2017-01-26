package algorithm.trees;

import java.util.ArrayList;

import algorithm.Utils;
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

	public int dfs(TreeNode root, int sum) {
		if (root == null)
			return 0;

		if (root.left == null && root.right == null) {
			return sum * 10 + root.val;
		}

		return dfs(root.left, sum * 10 + root.val) + dfs(root.right, sum * 10 + root.val);
	}

	public int sumNumbersCS(TreeNode root) {

		if (root == null)
			return 0;
		return dfsCS(root, root.val, 0, 0);
	}

	public int dfsCS(TreeNode root, int sum, int total, int callstack) {

		if (root.left == null && root.right == null) {
			total += sum;
			Utils.printCS(callstack, "root.val = " + root.val + ", sum = " + sum + ", total = " + total);
			Utils.printCsEOR(callstack);
			return total;
		}
		Utils.printCS(callstack, "root.val = " + root.val + ", sum = " + sum + ", total = " + total);
		total = dfsCS(root.left, sum * 10 + root.left.val, total, callstack + 1)
				+ dfsCS(root.right, sum * 10 + root.right.val, total, callstack + 1);
		Utils.printCS(callstack, "root.val = " + root.val + ", sum = " + sum + ", total = " + total);
		return total;
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

		num = num * 10 + node.val;

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

		num = num * 10 + node.val;

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
		TreeNode root = TreeUtils.buildInPreorderTree(inorder, preorder);
		// TreeNode root = TreeUtils.buildBstFromRange(1, 7);
		TreeUtils.drawTree(root);
		System.out.println("---------------------sumNumbers------------------------");
		System.out.println(ob.sumNumbers(root));
		System.out.println("---------------------sumNumbersCS------------------------");
		System.out.println(ob.sumNumbersCS(root));
		System.out.println("---------------------sumNumbersI------------------------");
		System.out.println(ob.sumNumbersI(root));
		System.out.println("---------------------sumNumbersII------------------------");
		System.out.println(ob.sumNumbersII(root));

	}
}
