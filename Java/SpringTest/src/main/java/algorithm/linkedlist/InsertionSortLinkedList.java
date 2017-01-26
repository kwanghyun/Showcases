package algorithm.linkedlist;

import algorithm.utils.LinkedListUtils;

/*
 * Insertion Sort List:
 * 
 * Sort a linked list using insertion sort.
 * 
 */
public class InsertionSortLinkedList {

	public static ListNode insertionSortList(ListNode head) {

		if (head == null || head.next == null)
			return head;

		ListNode start = head;
		ListNode end = head.next;
		while (end != null) {
			ListNode s = head;
			ListNode c = head.next;
			while (c != end) {
				if (c.val > end.val) {
					insert(s, c, start, end);
					break;
				}
				s = s.next;
				c = c.next;
			}
			start = start.next;
			end = end.next;
		}
		return head;
	}

	public static void insert(ListNode prev1, ListNode curr1, ListNode prev2, ListNode curr2) {
		ListNode t = curr2.next;
		prev1.next = curr2;
		curr2.next = curr1;
		prev2.next = t;
	}

	public static void swap(ListNode n1, ListNode n2) {
		int temp = n2.val;
		n2.val = n1.val;
		n1.val = temp;
	}

	public static ListNode insertionSortListI(ListNode head) {

		if (head == null || head.next == null)
			return head;

		ListNode new_h = new ListNode(head.val);
		ListNode end = head.next;

		// loop through each element in the list
		while (end != null) {
			// insert this element to the new list

			ListNode start = new_h;
			ListNode next = end.next;

			if (end.val <= new_h.val) {
				ListNode old_h = new_h;
				new_h = end;
				new_h.next = old_h;
			} else {
				while (start.next != null) {

					if (end.val > start.val && end.val <= start.next.val) {
						ListNode oldNext = start.next;
						start.next = end;
						end.next = oldNext;
					}

					start = start.next;
				}

				if (start.next == null && end.val > start.val) {
					start.next = end;
					end.next = null;
				}
			}

			// finally
			end = next;
		}

		return new_h;
	}

	public static void main(String[] args) {

		ListNode node = LinkedListUtils.generateUnSortedList();
		LinkedListUtils.printNodes(node);
		node = insertionSortList(node);
		LinkedListUtils.printNodes(node);
	}

	public static void printList(ListNode x) {
		if (x != null) {
			System.out.print(x.val + " ");
			while (x.next != null) {
				System.out.print(x.next.val + " ");
				x = x.next;
			}
			System.out.println();
		}

	}
}
