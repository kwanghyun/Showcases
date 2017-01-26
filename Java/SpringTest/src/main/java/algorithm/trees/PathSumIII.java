package algorithm.trees;

import algorithm.utils.TreeUtils;

/*
 * You are given a binary tree in which each node contains an integer value.
 * 
 * Find the number of paths that sum to a given value.
 * 
 * The path does not need to start or end at the root or a leaf, but it must
 * go downwards (traveling only from parent nodes to child nodes).
 * 
 * The tree has no more than 1,000 nodes and the values are in the range
 * -1,000,000 to 1,000,000.
 * 
	Example:
	
	root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8
	
	      10
	     /  \
	    5   -3
	   / \    \
	  3   2   11
	 / \   \
	3  -2   1
	
	Return 3. The paths that sum to 8 are:
	
	1.  5 -> 3
	2.  5 -> 2 -> 1
	3. -3 -> 11
 */

public class PathSumIII {

	public int pathSum(TreeNode root, int sum) {
		int count = 0;
		if (root == null)
			return count;

		if (root.val == sum)
			count++;
		System.out.println("[#######] root.val = " + root.val + ", count = " + count);
		count += findSum(root, 0, sum);
		return count + pathSum(root.left, sum) + pathSum(root.right, sum);
	}

	public int findSum(TreeNode root, int sum, int target) {
		int count = 0;
		if (root == null)
			return count;

		if (sum + root.val == target) {
			System.out.println("[FOUND] root.val = " + root.val + ", curr = " + sum);
			count++;
		}
		System.out.println("root.val = " + root.val + ", curr = " + sum);
		return count + findSum(root.left, sum + root.val, target) + findSum(root.right, sum + root.val, target);
	}

	public int pathSumI(TreeNode root, int sum) {
		int count = 0;
		if (root == null)
			return count;

		if (root.val == sum)
			count++;
		System.out.println("[#######] root.val = " + root.val + ", count = " + count);

		count += findSumI(root.left, root.val, sum) + findSumI(root.right, root.val, sum);
		return count + pathSumI(root.left, sum) + pathSumI(root.right, sum);
	}

	public int findSumI(TreeNode root, int curr, int target) {
		int count = 0;
		if (root == null)
			return count;

		if (curr + root.val == target) {
			System.out.println("[FOUND] root.val = " + root.val + ", curr = " + curr);
			count++;
		}

		System.out.println("root.val = " + root.val + ", curr = " + curr);
		return count + findSumI(root.left, curr + root.val, target) + findSumI(root.right, curr + root.val, target);
	}

	public static void main(String[] args) {
		TreeNode root = TreeUtils.buildLevelOrderBst("[10,5,-3,3,2,null,11,3,-2,null,1]");
		TreeUtils.drawTree(root);

		PathSumIII ob = new PathSumIII();
		System.out.println("ANSWER IS => " + ob.pathSum(root, 8));
		System.out.println("ANSWER IS => " + ob.pathSumI(root, 8));
	}
}
