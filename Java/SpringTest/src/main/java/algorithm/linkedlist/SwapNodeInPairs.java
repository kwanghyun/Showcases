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

	public ListNode swapPairsII(ListNode head) {
		
		ListNode p = head;
		/* Traverse only till there are atleast 2 nodes left */
		while (p != null && p.next != null) {

			/* Swap the data */
			int tmp = p.val;
			p.val = p.next.val;
			p.next.val = tmp;
			p = p.next.next;
		}
		return head;
	}

	public ListNode swapPairsI(ListNode head) {

		if (head == null || head.next == null)
			return head;

		ListNode prev = head;
		ListNode curr = head.next;
		ListNode n_head = curr;

		while (curr != null) {
			ListNode nxt = curr.next;
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
		ListNode head = LinkedListUtils.generateListFromRange(1, 5);
		LinkedListUtils.printNodes(head);
		System.out.println("\n--------------swapPairsI--------------");
		LinkedListUtils.drawList(obj.swapPairsI(head));
		System.out.println("\n--------------swapPairsI--------------");
		ListNode head5 = LinkedListUtils.generateOrderedList(5);
		LinkedListUtils.drawList(obj.swapPairsI(head5));
		System.out.println("\n--------------swapPairsI--------------");
		ListNode head6 = LinkedListUtils.generateOrderedList(6);
		LinkedListUtils.drawList(obj.swapPairsI(head6));
		System.out.println("\n--------------swapPairsII--------------");
		ListNode head7 = LinkedListUtils.generateOrderedList(5);
		LinkedListUtils.drawList(obj.swapPairsII(head7));
		System.out.println("\n--------------swapPairsII--------------");
		ListNode head8 = LinkedListUtils.generateOrderedList(6);
		LinkedListUtils.drawList(obj.swapPairsII(head8));
	}

	public ListNode swapPairs(ListNode head) {

		if (head == null || head.next == null)
			return head;

		ListNode newNode = new ListNode(0);
		newNode.next = head;
		ListNode p = newNode;

		while (p.next != null && p.next.next != null) {
			// use t1 to track first node
			ListNode t1 = p;
			p = p.next;
			t1.next = p.next;

			// use t2 to track next node of the pair
			ListNode t2 = p.next.next;
			p.next.next = p;
			p.next = t2;
		}

		return newNode.next;
	}
}
