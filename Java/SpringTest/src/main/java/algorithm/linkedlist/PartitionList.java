package algorithm.linkedlist;

import algorithm.utils.LinkedListUtils;

/*
 * Given a linked list and a value x, partition it such that all nodes less
 * than x come before nodes greater than or equal to x.
 * 
 * You should preserve the original relative order of the nodes in each of
 * the two partitions.
 * 
 * For example, given 1->4->3->2->5->2 and x = 3, return 1->2->2->4->3->5.
 * 
 */
public class PartitionList {

	public ListNode partitionI(ListNode head, int x) {
		if (head == null)
			return null;

		ListNode sp = new ListNode(0);
		ListNode bp = new ListNode(0);
		ListNode sHead = sp;
		ListNode bHead = bp;

		ListNode p = head;

		while (p != null) {
			if (head.val < x) {
				sp.next = p;
				sp = sp.next;
			} else {
				bp.next = p;
				bp = bp.next;
			}
			p = p.next;
		}
		bp.next = null;
		sp.next = bHead.next;

		return sHead.next;
	}

	public ListNode partition(ListNode head, int x) {
		if (head == null)
			return null;

		ListNode fakeHead1 = new ListNode(0);
		ListNode fakeHead2 = new ListNode(0);
		fakeHead1.next = head;

		ListNode p = head;
		ListNode prev = fakeHead1;
		ListNode p2 = fakeHead2;

		while (p != null) {
			if (p.val < x) {
				p = p.next;
				prev = prev.next;
			} else {

				p2.next = p;
				prev.next = p.next;

				p = prev.next;
				p2 = p2.next;
			}
		}

		// close the list
		p2.next = null;

		prev.next = fakeHead2.next;

		return fakeHead1.next;
	}

	public static void main(String[] args) {
		PartitionList ob = new PartitionList();

		int[] arr1 = { 1, 4, 3, 2, 5, 2 };
		ListNode head1 = LinkedListUtils.generateListFromArray(arr1);
		LinkedListUtils.drawList(head1);
		System.out.println("");
		ob.partitionI(head1, 3);
		LinkedListUtils.drawList(head1);

		System.out.println("");

		int[] arr = { 1, 4, 3, 2, 5, 2 };
		ListNode head = LinkedListUtils.generateListFromArray(arr);
		LinkedListUtils.drawList(head);
		System.out.println("");
		ob.partition(head, 3);
		LinkedListUtils.drawList(head);

	}
}
