package algorithm.linkedlist;

import algorithm.utils.LinkedListUtils;

/*
 * Problem
 * 
 * Given a singly linked list, group all odd nodes together followed by the
 * even nodes. Please note here we are talking about the node number and not
 * the value in the nodes.
 * 
 * The program should run in O(1) space complexity and O(nodes) time
 * complexity.
 * 
 * Example:
 * 
 * Given 1->2->3->4->5->NULL, return 1->3->5->2->4->NULL.
 * 
 * Analysis
 * 
 * This problem can be solved by using two pointers. We iterate over the
 * link and move the two pointers.
 */
public class OddEvenLinkedList {

	public Node oddEvenList(Node head) {
		if (head == null)
			return head;

		Node result = head;
		Node p1 = head;
		Node p2 = head.next;
		Node connectNode = head.next;

		while (p1 != null && p2 != null) {
			Node t = p2.next;
			if (t == null)
				break;

			p1.next = p2.next;
			p1 = p1.next;

			p2.next = p1.next;
			p2 = p2.next;
		}

		p1.next = connectNode;

		return result;
	}

	public Node oddEvenListI(Node head) {
		if (head == null)
			return head;

		Node result = head;
		Node p1 = head;
		Node p2 = head.next;
		Node connectNode = head.next;

		while (p1 != null && p2 != null && p2.next != null) {

			p1.next = p2.next;
			p1 = p1.next;

			p2.next = p1.next;
			p2 = p2.next;
		}

		p1.next = connectNode;

		return result;
	}

	public Node oddEvenListII(Node head) {
		if (head == null)
			return head;

		Node oddHead = head;
		Node p1 = head;
		Node p2 = head.next;
		Node evenHead = head.next;

		while (p1.next != null && p2.next != null) {

			p1.next = p2.next;
			p1 = p2.next;

			p2.next = p1.next;
			p2 = p1.next;
		}

		p1.next = evenHead;

		return oddHead;
	}

	public static void main(String[] args) {
		OddEvenLinkedList ob = new OddEvenLinkedList();
		Node resultI = ob.oddEvenListI(LinkedListUtils.generateOrderedList(6));
		LinkedListUtils.printNodes(resultI);
		System.out.println("--------------------------------------");
		Node resultII = ob.oddEvenListII(LinkedListUtils.generateOrderedList(6));
		LinkedListUtils.printNodes(resultII);
	}
}
