package algorithm.linkedlist;

import java.util.HashMap;

public class CloneRandomLinkedList {

	/*
	 * A linked list is given such that each node contains an additional random
	 * pointer which could point to any node in the list or null.
	 * 
	 * Return a deep copy of the list.
	 */

	public Node clone(Node head) {
		if (head == null)
			return null;
		HashMap<Node, Node> map = new HashMap<Node, Node>();
		Node cloneHead = new Node(head.val);

		Node originP = head;
		Node cloneP = cloneHead;
		map.put(head, cloneHead);
		originP = originP.next;
		
		while (originP != null) {
			Node newNode = new Node(originP.val);
			map.put(originP, newNode);
			cloneP.next = newNode;
			cloneP = newNode;
			originP = originP.next;
		}

		originP = head;
		cloneP = cloneHead;
		while (originP != null) {
			if (originP.random != null)
				cloneP.random = map.get(originP.random);
			else
				cloneP.random = null;

			originP = originP.next;
			cloneP = cloneP.next;
		}

		return cloneHead;
	}

	public static void main(String args[]) {
		CloneRandomLinkedList obj = new CloneRandomLinkedList();
		Node node1 = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		Node node4 = new Node(4);
		Node node5 = new Node(5);

		node1.next = node2;
		node1.random = node3;
		node2.next = node3;
		node2.random = node4;
		node3.next = node4;
		node3.random = node1;
		node4.next = node5;
		node4.random = node4;
		node5.next = null;
		node5.random = null;

		Node originHead = node1;
		Node cloneHead = obj.clone(originHead);
		System.out.println("------------------------------");
		obj.check(originHead, cloneHead);

	}

	public void check(Node origin, Node clone) {

		while (origin != null) {

			if (origin.next != null && clone.next != null
					&& origin.next == clone.next)
				throw new RuntimeException("Pointing the same object");
			if (origin.random != null && clone.random != null
					&& origin.random == clone.random)
				throw new RuntimeException("Pointing the same object");

			System.out.println(clone.val);
			origin = origin.next;
			clone = clone.next;
		}

	}

}
