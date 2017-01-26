package algorithm.linkedlist;

import algorithm.utils.LinkedListUtils;

/*
 * Given a singly linked list, rotate the linked list counter-clockwise by k
 * nodes. Where k is a given positive integer. For example, if the given
 * linked list is 10->20->30->40->50->60 and k is 4, the list should be
 * modified to 50->60->10->20->30->40. Assume that k is smaller than the
 * count of nodes in linked list.
 */
public class RotateLinkedList {
	ListNode head; // head of list

	// This function rotates a linked list counter-clockwise
	// and updates the head. The function assumes that k is
	// smaller than size of linked list. It doesn't modify
	// the list if k is greater than or equal to size

	void rotate(int k) {
		if (k == 0)
			return;

		// Let us understand the below code for example k = 4
		// and list = 10->20->30->40->50->60.
		ListNode current = head;

		// current will either point to kth or NULL after this
		// loop. current will point to node 40 in the above example
		int count = 1;
		while (count < k && current != null) {
			current = current.next;
			count++;
		}

		// If current is NULL, k is greater than or equal to count
		// of nodes in linked list. Don't change the list in this case
		if (current == null)
			return;

		// current points to kth node. Store it in a variable.
		// kthNode points to node 40 in the above example
		ListNode kthNode = current;

		// current will point to last node after this loop
		// current will point to node 60 in the above example
		while (current.next != null)
			current = current.next;

		// Change next of last node to previous head
		// Next of 60 is now changed to node 10

		current.next = head;

		// Change head to (k+1)th node
		// head is now changed to node 50
		head = kthNode.next;

		// change next of kth node to null
		kthNode.next = null;
	}

	public ListNode rotateRight(ListNode head, int k) {
		if (head == null || head.next == null || k == 0)
			return head;

		ListNode newH = null;
		ListNode prev = head;
		ListNode curr = head.next;
		int idx = 1;
		while (curr != null) {

			if (idx == k) {
				newH = curr;
				prev.next = null;
			}
			prev = curr;
			curr = curr.next;
			idx++;
		}
		
		if (newH == null)
			return head;
		
		prev.next = head;

		return newH;
	}

	public static void main(String[] args) {
		RotateLinkedList ob = new RotateLinkedList();
		LinkedListUtils.printNodes(ob.rotateRight(LinkedListUtils.generateOrderedList(6), 6));

	}
}
