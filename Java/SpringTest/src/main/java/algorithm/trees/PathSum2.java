package algorithm.trees;

import java.util.ArrayList;
import java.util.List;

import algorithm.utils.TreeUtils;

/*
 * Given a binary tree and a sum, find all root-to-leaf paths where each path’s sum equals
 the given sum.
 For example, given the below binary tree and sum = 22,
        5
      /  \
     4   8
    /    / \
  11 13 4(3)
  / \     /    \
 7  2   5(6)  1
 the method returns the following:
 [
 [5,4,11,2],
 [5,8,4,5]
 ]

 This problem can be converted to be a typical depth-first search problem. A recursive
 depth-first search algorithm usually requires a recursive method call, a reference to
 the final result, a temporary result, etc.

 */
public class PathSum2 {

	public List<List<Integer>> pathSum(TreeNode root, int sum) {

		ArrayList<List<Integer>> result = new ArrayList<>();

		if (root == null)
			return result;

		ArrayList<Integer> list = new ArrayList<>();
		list.add(root.val);

		dfs(root, sum - root.val, result, list);

		return result;
	}

	public void dfs(TreeNode node, int sum, List<List<Integer>> result, List<Integer> list) {

		if (node.left == null && node.right == null && sum == 0) {
			result.add(new ArrayList<>(list));
		}

		if (node.left != null) {
			list.add(node.left.val);
			dfs(node.left, sum - node.left.val, result, list);
			list.remove(list.size() - 1);
		}

		if (node.right != null) {
			list.add(node.right.val);
			dfs(node.right, sum - node.right.val, result, list);
			list.remove(list.size() - 1);
		}
	}

	public ArrayList<ArrayList<Integer>> pathSum2(TreeNode root, int sum) {

		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> list = new ArrayList<>();
		list.add(root.val);

		pathSum2(root, sum - root.val, result, list);

		return result;
	}

	public void pathSum2(TreeNode root, int sum, ArrayList<ArrayList<Integer>> result, ArrayList<Integer> list) {

		if (root.left == null && root.right == null && sum == 0) {
			ArrayList<Integer> temp = new ArrayList<>();
			temp.addAll(list);
			result.add(temp);
		}

		if (root.left != null) {
			list.add(root.left.val);
			pathSum2(root.left, sum - root.left.val, result, list);
			list.remove(list.size() - 1);
		}

		if (root.right != null) {
			list.add(root.right.val);
			pathSum2(root.right, sum - root.right.val, result, list);
			list.remove(list.size() - 1);
		}
	}

	public int maxPathSum(TreeNode root) {
		int max = 0;
		if (root == null)
			return 0;
		max = Math.max(root.val + maxPathSum(root.right), root.val + maxPathSum(root.left));
		return max;
	}

	public static void main(String[] args) {
		PathSum2 obj = new PathSum2();
		TreeNode root = obj.generateEntireTree();
		TreeUtils.drawTree(root);
		List<List<Integer>> lists = obj.pathSum(root, 22);
		System.out.println(lists);

		List<ArrayList<Integer>> lists2 = obj.pathSum2(root, 22);
		System.out.println(lists2);

		TreeNode root1 = TreeUtils.buildBstFromRange(1, 7);
		TreeUtils.drawTree(root1);
		System.out.println(obj.maxPathSum(root1));
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
		// TreeNode nine = new TreeNode(9);
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

}
