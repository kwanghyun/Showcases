package algorithm.linkedlist;

import algorithm.utils.LinkedListUtils;

/*
 * Insertion Sort List:
 * 
 * Sort a linked list using insertion sort.
 * 
 */
public class InsertionSortLinkedList {

	public static Node insertionSort(Node head) {

		if (head == null || head.next == null)
			return head;

		Node prev = head;
		Node curr = head.next;
		while (curr != null) {
			Node t_prv = head;
			Node t_crr = head.next;
			while (t_crr != curr) {
				if (t_crr.val > curr.val) {
					insert(t_prv, t_crr, prev, curr);
					break;
				}
				t_prv = t_prv.next;
				t_crr = t_crr.next;
			}
			prev = prev.next;
			curr = curr.next;
		}
		return head;
	}

	public static void insert(Node prev1, Node curr1, Node prev2, Node curr2) {
		Node t = curr2.next;
		prev1.next = curr2;
		curr2.next = curr1;
		prev2.next = t;
	}

	public static void swap(Node n1, Node n2) {
		int temp = n2.val;
		n2.val = n1.val;
		n1.val = temp;
	}

	public static Node insertionSortList(Node head) {

		if (head == null || head.next == null)
			return head;

		Node newHead = new Node(head.val);
		Node pointer = head.next;

		// loop through each element in the list
		while (pointer != null) {
			// insert this element to the new list

			Node innerPointer = newHead;
			Node next = pointer.next;

			if (pointer.val <= newHead.val) {
				Node oldHead = newHead;
				newHead = pointer;
				newHead.next = oldHead;
			} else {
				while (innerPointer.next != null) {

					if (pointer.val > innerPointer.val && pointer.val <= innerPointer.next.val) {
						Node oldNext = innerPointer.next;
						innerPointer.next = pointer;
						pointer.next = oldNext;
					}

					innerPointer = innerPointer.next;
				}

				if (innerPointer.next == null && pointer.val > innerPointer.val) {
					innerPointer.next = pointer;
					pointer.next = null;
				}
			}

			// finally
			pointer = next;
		}

		return newHead;
	}

	public static void main(String[] args) {

		Node node = LinkedListUtils.generateUnSortedList();
		LinkedListUtils.printNodes(node);
		node = insertionSort(node);
		LinkedListUtils.printNodes(node);
	}

	public static void printList(Node x) {
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
