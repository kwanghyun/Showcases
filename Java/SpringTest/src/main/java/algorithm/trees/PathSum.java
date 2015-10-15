package algorithm.trees;

import java.util.LinkedList;

/*Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that
 adding up all the values along the path equals the given sum.
 For example: Given the below binary tree and sum = 22,
 5
 / \
 4   8
 /   / \
 11 13 4
 / \   \
 7  2   1
 return true, as there exist a root-to-leaf path 5->4->11->2 which sum is 22.

 Add all node to a queue and store sum value of each node to another queue. When it
 is a leaf node, check the stored sum value.
 For the tree above, the queue would be: 5 - 4 - 8 - 11 - 13 - 4 - 7 - 2 - 1. It will check
 node 13, 7, 2 and 1. This is a typical breadth first search(BFS) problem.
 */
public class PathSum {

	public boolean hasPathSum(TreeNode root, int sum) {

		if (root == null)
			return false;

		LinkedList<TreeNode> nodes = new LinkedList<TreeNode>();
		LinkedList<Integer> values = new LinkedList<Integer>();

		nodes.add(root);
		values.add(root.value);

		while (!nodes.isEmpty()) {
			TreeNode curr = nodes.remove();
			int sumValue = values.remove();

			if (curr.left == null && curr.right == null && sumValue == sum) {
				return true;
			}

			if (curr.left != null) {
				nodes.add(curr.left);
				values.add(sumValue + curr.left.value);
			}

			if (curr.right != null) {
				nodes.add(curr.right);
				values.add(sumValue + curr.right.value);
			}
		}
		return false;
	}

	public static void main(String[] args) {
		PathSum obj = new PathSum();
		TreeNode node = obj.generateEntireTree();
		System.out.println(obj.hasPathSum(node, 22));
		System.out.println(obj.hasPathSumRe(node, 22));
	}
	
	
	public TreeNode generateEntireTree() {

		TreeNode one = new TreeNode(1);
		TreeNode two = new TreeNode(2);
		TreeNode three = new TreeNode(4);
		TreeNode four = new TreeNode(4);
		TreeNode five = new TreeNode(5);
		TreeNode six = new TreeNode(5);
		TreeNode seven = new TreeNode(7);
		TreeNode eight = new TreeNode(8);
//		TreeNode nine = new TreeNode(9);
		TreeNode eleven = new TreeNode(11);
		TreeNode thirteen = new TreeNode(13);
		five.setLeft(four);
		five.setRight(eight);
		four.setLeft(eleven);
		eleven.setLeft(seven);
		eleven.setRight(two);
		eight.setLeft(thirteen);
		eight.setRight(three);
		three.setLeft(six);
		three.setRight(one);
		
		return five;
	}


	public boolean hasPathSumRe(TreeNode root, int sum) {
		if (root == null)
			return false;

		if (root.value == sum && (root.left == null && root.right == null))
			return true;

		return hasPathSumRe(root.left, sum - root.value)
				|| hasPathSumRe(root.right, sum - root.value);

	}
}
