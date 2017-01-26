package algorithm.trees;

/*Given a binary tree, find the maximum path sum. The path may start and end at
 any node in the tree. For example, given the below binary tree
	            1
               / \
              /   \
             /     \
           2       3
          / \     /
        4   5   6
        /        / \
       7       8   9

 the result is 6.

 1) Recursively solve this problem 
 2) Get largest left sum and right sum 
 3) Compare to the stored maximum
 */
public class MaxPathSum {
	
	public int maxPathSum(TreeNode root) {
		int max[] = new int[1];
		max[0] = Integer.MIN_VALUE;
		calculateSum(root, max);
		return max[0];
	}

	public int calculateSum(TreeNode root, int[] max) {
		if (root == null)
			return 0;
		
		int left = calculateSum(root.left, max);
		int right = calculateSum(root.right, max);
		
		int current = Math.max(root.val,
				Math.max(root.val + left, root.val + right));
		
		max[0] = Math.max(max[0], Math.max(current, left + root.val + right));
		
		return current;
	}
}
