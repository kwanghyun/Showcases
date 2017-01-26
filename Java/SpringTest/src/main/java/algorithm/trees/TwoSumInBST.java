package algorithm.trees;

import java.util.Iterator;
import java.util.Stack;

class BinTreeIterator implements Iterator<TreeNode> {
	Stack<TreeNode> stack;
	boolean leftToRight;

	public boolean hasNext() {
		return !stack.empty();
	}

	public TreeNode next() {
		return stack.peek();
	}

	public void remove() {
		TreeNode node = stack.pop();
		if (leftToRight) {
			node = node.right;
			while (node.right != null) {
				stack.push(node);
				node = node.right;
			}
		} else {
			node = node.left;
			while (node.left != null) {
				stack.push(node);
				node = node.left;
			}
		}
	}

	public BinTreeIterator(TreeNode node, boolean leftToRight) {
		stack = new Stack<TreeNode>();
		this.leftToRight = leftToRight;

		if (leftToRight) {
			while (node != null) {
				stack.push(node);
				node = node.left;
			}
		} else {
			while (node != null) {
				stack.push(node);
				node = node.right;
			}
		}
	}
}

public class TwoSumInBST {

	/*
	 * Given a Balanced Binary Search Tree and a target sum, write a function
	 * that returns true if there is a pair with sum equals to target sum,
	 * otherwise return false. Expected time complexity is O(n) and only O(Logn)
	 * extra space can be used. Any modification to Binary Search Tree is not
	 * allowed. Note that height of a Balanced BST is always O(Logn).
	 * 
	 * A space optimized solution is discussed in previous post. The idea was to
	 * first in-place convert BST to Doubly Linked List (DLL), then find pair in
	 * sorted DLL in O(n) time. This solution takes O(n) time and O(Logn) extra
	 * space, but it modifies the given BST.
	 * 
	 * The solution discussed below takes O(n) time, O(Logn) space and doesn’t
	 * modify BST. The idea is same as finding the pair in sorted array (See
	 * method 1 of this for details). We traverse BST in Normal Inorder and
	 * Reverse Inorder simultaneously. In reverse inorder, we start from the
	 * rightmost node which is the maximum value node. In normal inorder, we
	 * start from the left most node which is minimum value node. We add sum of
	 * current nodes in both traversals and compare this sum with given target
	 * sum. If the sum is same as target sum, we return true. If the sum is more
	 * than target sum, we move to next node in reverse inorder traversal,
	 * otherwise we move to next node in normal inorder traversal. If any of the
	 * traversals is finished without finding a pair, we return false. Following
	 * is C++ implementation of this approach.
	 */
	boolean searchNumArray(int[] arr, int num) {
		int left = 0;
		int right = arr.length - 1;
		while (left < right) {
			int sum = arr[left] + arr[right];
			if (sum == num) {
				return true;
			} else if (sum > num) {
				right--;
			} else {
				left++;
			}
		}
		return false;
	}


	public static boolean searchNumBinTree(TreeNode node, int num) {
		if (node == null)
			return false;

		BinTreeIterator leftIter = new BinTreeIterator(node, true);
		BinTreeIterator rightIter = new BinTreeIterator(node, false);

		while (leftIter.hasNext() && rightIter.hasNext()) {
			TreeNode left = leftIter.next();
			TreeNode right = rightIter.next();
			int sum = left.val + right.val;
			if (sum == num) {
				return true;
			} else if (sum > num) {
				rightIter.remove();
				if (!rightIter.hasNext() || rightIter.next() == left) {
					return false;
				}
			} else {
				leftIter.remove();
				if (!leftIter.hasNext() || leftIter.next() == right) {
					return false;
				}
			}
		}

		return false;
	}

}
