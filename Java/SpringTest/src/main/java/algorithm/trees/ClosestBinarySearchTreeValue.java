package algorithm.trees;

import algorithm.utils.TreeUtils;

/*
 * Given a non-empty binary search tree and a target value, find the value
 * in the BST that is closest to the target.
 * 
 * Note: Given target value is a floating point. You are guaranteed to have
 * only one unique value in the BST that is closest to the target.
 */
public class ClosestBinarySearchTreeValue {
	double cloestVal;
	int closetNodeVal;

	int goal;
	double min = Double.MAX_VALUE;

	public int closestValueII(TreeNode root, double target) {
		helper(root, target);
		return goal;
	}

	public void helper(TreeNode root, double target) {
		if (root == null)
			return;

		if (Math.abs(root.val - target) < min) {
			min = Math.abs(root.val - target);
			goal = root.val;
		}

		if (target < root.val) {
			helper(root.left, target);
		} else {
			helper(root.right, target);
		}
	}

	public int closestValue(TreeNode root, double target) {
		cloestVal = Math.abs(target - root.val);
		closetNodeVal = root.val;
		dfs(root, target);
		return closetNodeVal;
	}

	public void dfs(TreeNode root, double target) {
		if (root == null)
			return;

		double diff = Math.abs(target - root.val);
		System.out.println("root.val = " + root.val + ", diff = " + diff);
		if (cloestVal > diff) {
			cloestVal = diff;
			closetNodeVal = root.val;
		}

		if (root.val == target) {
			closetNodeVal = root.val;
			return;
		} else if (Math.round(root.val) > target) {
			dfs(root.left, target);
		} else {
			dfs(root.right, target);
		}
	}

	public static void main(String[] args) {
		TreeNode root = TreeUtils.buildBstFromRange(1, 9);
		TreeUtils.drawTree(root);
		ClosestBinarySearchTreeValue ob = new ClosestBinarySearchTreeValue();
		System.out.println(ob.closestValue(root, 2.3));
		ob.cloestVal = Integer.MAX_VALUE;
		System.out.println(ob.closestValue(root, 9.3));
		System.out.println(ob.closestValue(root, 0.7));
		System.out.println(ob.closestValue(root, 5.1));
	}
}
