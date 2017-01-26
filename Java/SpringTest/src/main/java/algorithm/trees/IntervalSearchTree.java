package algorithm.trees;

import java.util.ArrayList;

import algorithm.utils.TreeUtils;

/*
Interval Tree
Consider a situation where we have a set of intervals and we need following operations to be implemented efficiently. 
1) Add an interval
2) Remove an interval
3) Given an interval x, find if x overlaps with any of the existing intervals.

Interval Tree: The idea is to augment a self-balancing Binary Search Tree (BST) like Red Black Tree, AVL Tree, etc to maintain set of intervals so that all operations can be done in O(Logn) time.

Every node of Interval Tree stores following information.
a) i: An interval which is represented as a pair [low, high]
b) max: Maximum high value in subtree rooted with this node.

The low value of an interval is used as key to maintain order in BST. The insert and delete operations are same as insert and delete in self-balancing BST used.

IntervalSearcTree

The main operation is to search for an overlapping interval. Following is algorithm for searching an overlapping interval x in an Interval tree rooted with root.

Interval overlappingIntervalSearch(root, x)
1) If x overlaps with root's interval, return the root's interval.

2) If left child of root is not empty and the max  in left child 
is greater than x's low value, recur for left child

3) Else recur for right child.
How does the above algorithm work?
Let the interval to be searched be x. We need to prove this in for following two cases.

Case 1: When we go to right subtree, one of the following must be true.
a) There is an overlap in right subtree: This is fine as we need to return one overlapping interval.
b) There is no overlap in either subtree: We go to right subtree only when either left is NULL or maximum value in left is smaller than x.low. So the interval cannot be present in left subtree.

Case 2: When we go to left subtree, one of the following must be true.
a) There is an overlap in left subtree: This is fine as we need to return one overlapping interval.
b) There is no overlap in either subtree: This is the most important part. We need to consider following facts.
… We went to left subtree because x.low <= max in left subtree
…. max in left subtree is a high of one of the intervals let us say [a, max] in left subtree.
…. Since x doesn’t overlap with any node in left subtree x.low must be smaller than ‘a‘.
…. All nodes in BST are ordered by low value, so all nodes in right subtree must have low value greater than ‘a‘.
…. From above two facts, we can say all intervals in right subtree have low value greater than x.low. So x cannot overlap with any interval in right subtree.*/
public class IntervalSearchTree {

	private IntervalNode root;

	private class IntervalNode {
		IntervalNode left;
		int start;
		int end;
		int maxEnd;
		IntervalNode right;

		public IntervalNode(IntervalNode left, int start, int end, int maxEnd, IntervalNode right) {
			this.left = left;
			this.start = start;
			this.end = end;
			this.maxEnd = maxEnd;
			this.right = right;
		}
	}

	/**
	 * Adds an interval to the the calendar
	 * 
	 * @param start
	 *            the start of interval
	 * @param end
	 *            the end of the interval.
	 */
	public void add(int start, int end) {
		if (start >= end)
			throw new IllegalArgumentException("The end " + end + " should be greater than start " + start);

		IntervalNode p = root;
		while (p != null) {
			p.maxEnd = (end > p.maxEnd) ? end : p.maxEnd;
			if (start < p.start) {
				if (p.left == null) {
					p.left = new IntervalNode(null, start, end, end, null);
					return;
				}
				p = p.left;
			} else {
				if (p.right == null) {
					p.right = new IntervalNode(null, start, end, end, null);
					return;
				}
				p = p.right;
			}
		}
		root = new IntervalNode(null, start, end, end, null);
	}

	/**
	 * Tests if the input interval overlaps with the existing intervals.
	 * 
	 * Rules: 1. If interval intersects return true. obvious. 2. if (leftsubtree
	 * == null || leftsubtree.max <= low) go right 3. else go left
	 * 
	 * @param start
	 *            the start of the interval
	 * @param end
	 *            the end of the interval return true if overlap, else false.
	 */
	public IntervalNode getOverlap(int start, int end) {
		if (start >= end)
			throw new IllegalArgumentException("The end " + end + " should be greater than start " + start);

		IntervalNode p = root;

		while (p != null) {
			if (p.start < end && p.end > start)
				return p;

			if (p.left != null && p.left.maxEnd > start) {
				p = p.left;
			} else {
				p = p.right;
			}
		}
		return null;
	}

	/*
	 * Note that the above implementation uses simple Binary Search Tree insert
	 * operations. Therefore, time complexity of the above implementation is
	 * more than O(nLogn). We can use Red-Black Tree or AVL Tree balancing
	 * techniques to make the above implementation O(nLogn).
	 */
	public void printConflicting(int[][] intervals) {
		// Create an empty Interval Search Tree, add first appointment
		IntervalSearchTree ist = new IntervalSearchTree();

		ist.add(intervals[0][0], intervals[0][1]);

		// Process rest of the intervals
		for (int i = 1; i < intervals.length; i++) {
			int[] in = intervals[i];

			IntervalNode result = ist.getOverlap(in[0], in[1]);
			if (result != null) {
				System.out.println("Interval [ " + result.start + ", " + result.end + "] is confict with Inteval ["
						+ in[0] + ", " + in[1] + "]");
			}
			ist.add(in[0], in[1]);
		}

	}

	public static void main(String[] args) {

		IntervalSearchTree intervalSearchTree = new IntervalSearchTree();

		int[][] intervals = { { 1, 5 }, { 3, 7 }, { 2, 6 }, { 10, 15 }, { 5, 6 }, { 4, 100 } };
		intervalSearchTree.printConflicting(intervals);

		// intervalSearchTree.add(17, 19);
		// intervalSearchTree.add(5, 8);
		// intervalSearchTree.add(21, 24);
		// intervalSearchTree.add(5, 8);
		// intervalSearchTree.add(4, 8);
		// intervalSearchTree.add(15, 18);
		// intervalSearchTree.add(7, 10);
		// intervalSearchTree.add(16, 22);
		//
		// System.out.println("Expected true, Actual: " +
		// intervalSearchTree.getOverlap(23, 25));
		// System.out.println("Expected false, Actual: " +
		// intervalSearchTree.getOverlap(12, 14));
		// System.out.println("Expected true, Actual: " +
		// intervalSearchTree.getOverlap(21, 23));
		// // testing adjoint
		// System.out.println("Expected false, Actual: " +
		// intervalSearchTree.getOverlap(10, 15));
		// System.out.println("Expected false, Actual: " +
		// intervalSearchTree.getOverlap(10, 14));
		// System.out.println("Expected false, Actual: " +
		// intervalSearchTree.getOverlap(11, 15));
	}
}