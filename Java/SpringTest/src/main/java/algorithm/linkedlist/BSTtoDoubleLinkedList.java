package algorithm.linkedlist;

import algorithm.trees.TreeNode;
import algorithm.utils.LinkedListUtils;
import algorithm.utils.TreeUtils;

public class BSTtoDoubleLinkedList {

	Node head;
	Node tail;

	public void convert(TreeNode root) {
		if (root == null)
			return;

		convert(root.left);

		Node newNode = new Node(root.value);

		if (tail == null) {
			tail = newNode;
			head = newNode;
		} else {
			tail.next = newNode;
			newNode.prev = tail;
			tail = newNode;
		}
		convert(root.right);
	}

	public static void main(String args[]) {
		BSTtoDoubleLinkedList bdl = new BSTtoDoubleLinkedList();
		TreeNode root = TreeUtils.buildBstFromRange(1, 7);
		bdl.convert(root);
		TreeUtils.drawTree(root);

		// LinkedListUtils.printNodes(bdl.head);
		LinkedListUtils.drawList(bdl.head);
	}

}
