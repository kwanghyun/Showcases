package algorithm.linkedlist;

import algorithm.utils.LinkedListUtils;

/*
 Given a linked list, swap every two adjacent nodes and return its head.
 For example, given 1->2->3->4, you should return the list as 2->1->4->3.
 Your algorithm should use only constant space. You may not modify the values in
 the list, only nodes itself can be changed.

 Use two template variable to track the previous and next node of each pair.
 */
public class SwapNodeInPairs {

	public Node swapPairsI(Node head) {

		if (head == null || head.next == null)
			return head;

		Node prev = head;
		Node curr = head.next;

		while (curr.next != null && curr.next.next != null) {
			curr.next = prev;
			prev.next = curr.next;
			prev = prev.next;
			curr = prev.next;
		}
		return head;
	}

	public static void main(String[] args) {
		SwapNodeInPairs obj = new SwapNodeInPairs();
		Node head = LinkedListUtils.generateOrderedList(5);
		LinkedListUtils.printNodes(head);
		System.out.println("\n----------------------------");
		LinkedListUtils.printNodes(obj.swapPairs(head));
		System.out.println("\n----------------------------");
		Node head5 = LinkedListUtils.generateOrderedList(5);
		LinkedListUtils.printNodes(obj.swapPairs(head5));
		System.out.println("\n----------------------------");
		Node head6 = LinkedListUtils.generateOrderedList(6);
		LinkedListUtils.printNodes(obj.swapPairs(head6));
	}

	public Node swapPairs(Node head) {

		if (head == null || head.next == null)
			return head;

		Node newNode = new Node(0);
		newNode.next = head;
		Node p = newNode;

		while (p.next != null && p.next.next != null) {
			// use t1 to track first node
			Node t1 = p;
			p = p.next;
			t1.next = p.next;

			// use t2 to track next node of the pair
			Node t2 = p.next.next;
			p.next.next = p;
			p.next = t2;
		}

		return newNode.next;
	}
}
