package algorithm.trees;

import java.util.LinkedList;

import algorithm.utils.TreeUtils;

/*Given a binary tree, find its minimum depth.
 The minimum depth is the number of nodes along the shortest path from the root
 node down to the nearest leaf node.
                1
               / \
              /   \
             /     \
           2       3
          / \     /
        4   5   6
        /        / \
       7       8   9
       
 Need to know LinkedList is a queue. add() and remove() are the two methods to
 manipulate the queue.
 
 */

public class MinimumDepth {
	public int minDepth(TreeNode root) {
		if (root == null) {
			return 0;
		}

		LinkedList<TreeNode> nodes = new LinkedList<TreeNode>();
		LinkedList<Integer> counts = new LinkedList<Integer>();
		nodes.add(root);
		counts.add(1);

		while (!nodes.isEmpty()) {
			TreeNode curr = nodes.remove();
			int count = counts.remove();
			if (curr.left != null) {
				nodes.add(curr.left);
				counts.add(count + 1);
			}
			if (curr.right != null) {
				nodes.add(curr.right);
				counts.add(count + 1);
			}

			if (curr.left == null && curr.right == null) {
				return count;
			}
		}
		return 0;
	}

	/*
	 * @@Result1 : 3
	 * 
	 * @@Result2 : 2 -> Recursive will give you wrong answer
	 */
	public int minDepth2(TreeNode root) {
		if (root == null)
			return 0;

		return 1 + Math.min(minDepth2(root.left), minDepth2(root.right));
	}

	public static void main(String args[]) {
		MinimumDepth hot = new MinimumDepth();

		int[] preorder = { 5, 3, 1, 4, 7, 6, 8 };
		TreeNode root = TreeUtils.buildBstFromPreorder(preorder);
		// TreeNode root = TreeUtils.buildBstFromPreorder(10);
		TreeUtils.drawTree(root);

		System.out.println("@@Result1 : " + hot.minDepth(root));
		System.out.println("@@Result2 : " + hot.minDepth2(root));
	}
}
