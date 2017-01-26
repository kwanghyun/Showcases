package algorithm.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import algorithm.utils.TreeUtils;

/*
 * Given a non-empty binary search tree and a target value, find k values in
 * the BST that are closest to the target.
 * 
 * Note: Given target value is a floating point. You may assume k is always
 * valid, that is: k ≤ total nodes. You are guaranteed to have only one
 * unique set of k values in the BST that are closest to the target. 
 * 
 * Follow up: Assume that the BST is balanced, could you solve it in less than O(n)
 * runtime (where n = total nodes)?
 */
public class ClosestBinarySearchTreeValueII {

	class QNode implements Comparable<QNode> {
		int key;
		double value;

		public QNode(int key, double value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public int compareTo(QNode o) {
			if (o.value - this.value == 0)
				return 0;
			else if (o.value - this.value > 0)
				return 1;
			else
				return -1;
		}
	}

	double cloestVal;

	public List<Integer> closestKValues(TreeNode root, double target, int k) {
		List<Integer> list = new ArrayList<>();
		Queue<QNode> q = new PriorityQueue<>();
		cloestVal = Math.abs(target - root.val);
		dfs(root, target, k, q);

		while (!q.isEmpty()) {
			list.add(q.poll().key);
		}
		return list;
	}

	public void dfs(TreeNode root, double target, int k, Queue<QNode> q) {
		if (root == null)
			return;

		double diff = Math.abs(target - root.val);
		q.offer(new QNode(root.val, diff));
		if (q.size() > k) {
			q.poll();
		}

		dfs(root.left, target, k, q);
		dfs(root.right, target, k, q);
	}

	public static void main(String[] args) {
		TreeNode root = TreeUtils.buildBstFromRange(1, 9);
		TreeUtils.drawTree(root);
		ClosestBinarySearchTreeValueII ob = new ClosestBinarySearchTreeValueII();
		System.out.println(ob.closestKValues(root, 2.3, 2));
		System.out.println(ob.closestKValues(root, 9.3, 3));
		System.out.println(ob.closestKValues(root, 0.7, 1));
		System.out.println(ob.closestKValues(root, 5.1, 4));
	}
}
