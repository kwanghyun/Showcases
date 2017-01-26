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
	public ListNode oddEvenList_(ListNode head) {
		if (head == null)
			return head;

		ListNode oddHead = head;
		ListNode evenHead = head.next;

		ListNode p = head;
		ListNode oddLastNode = null;
		boolean isOdd = true;

		while (p != null && p.next != null) {

			ListNode n = p.next;
			p.next = p.next.next;

			if (isOdd) {
				oddLastNode = p;
			}
			isOdd = !isOdd;

			p = n;
		}

		oddLastNode.next = evenHead;

		return oddHead;
	}

	public ListNode oddEvenList(ListNode head) {
		if (head == null)
			return head;

		ListNode result = head;
		ListNode p1 = head;
		ListNode p2 = head.next;
		ListNode connectNode = head.next;

		while (p1 != null && p2 != null) {
			ListNode t = p2.next;
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

	public ListNode oddEvenListI(ListNode head) {
		if (head == null)
			return head;

		ListNode result = head;
		ListNode p1 = head;
		ListNode p2 = head.next;
		ListNode connectNode = head.next;

		while (p1 != null && p2 != null && p2.next != null) {

			p1.next = p2.next;
			p1 = p1.next;

			p2.next = p1.next;
			p2 = p2.next;
		}

		p1.next = connectNode;

		return result;
	}

	public ListNode oddEvenListII(ListNode head) {
		if (head == null)
			return head;

		ListNode oddHead = head;
		ListNode p1 = head;
		ListNode p2 = head.next;
		ListNode evenHead = head.next;

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
		System.out.println("-----------------oddEvenList_---------------------");
		ListNode result = ob.oddEvenList_(LinkedListUtils.generateOrderedList(6));
		LinkedListUtils.drawList(result);
		ListNode result1 = ob.oddEvenList_(LinkedListUtils.generateOrderedList(5));
		LinkedListUtils.drawList(result1);
		System.out.println("-----------------oddEvenListI---------------------");
		ListNode resultI = ob.oddEvenListI(LinkedListUtils.generateOrderedList(6));
		LinkedListUtils.drawList(resultI);
		System.out.println("-----------------oddEvenListII---------------------");
		ListNode resultII = ob.oddEvenListII(LinkedListUtils.generateOrderedList(6));
		LinkedListUtils.drawList(resultII);
	}
}
