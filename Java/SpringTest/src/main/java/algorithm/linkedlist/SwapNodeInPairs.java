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
		Node n_head = curr;

		while (curr != null) {
			Node nxt = curr.next;
			curr.next = prev;

			if (nxt == null || nxt.next == null) {
				prev.next = nxt;
				break;
			} else {
				prev.next = nxt.next;
				prev = nxt;
				curr = nxt.next;
			}
		}

		return n_head;
	}

	public static void main(String[] args) {
		SwapNodeInPairs obj = new SwapNodeInPairs();
		Node head = LinkedListUtils.generateListFromRange(1, 5);
		LinkedListUtils.printNodes(head);
		System.out.println("\n--------------swapPairsI--------------");
		LinkedListUtils.drawList(obj.swapPairsI(head));
		System.out.println("\n--------------swapPairsI--------------");
		Node head5 = LinkedListUtils.generateOrderedList(5);
		LinkedListUtils.drawList(obj.swapPairsI(head5));
		System.out.println("\n--------------swapPairsI--------------");
		Node head6 = LinkedListUtils.generateOrderedList(6);
		LinkedListUtils.drawList(obj.swapPairsI(head6));
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
