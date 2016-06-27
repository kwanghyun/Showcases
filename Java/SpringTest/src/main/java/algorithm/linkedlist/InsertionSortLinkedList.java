package algorithm.linkedlist;

/*
 * Insertion Sort List:
 * 
 * Sort a linked list using insertion sort.
 * 
 */
public class InsertionSortLinkedList {

	public static Node insertionSort(Node node) {

		if (node == null || node.next == null)
			return node;

		Node h = node;
		Node p = node;
		while (p != null) {
			h = node;
			while (h != p) {
				if (h.val > h.next.val) {
					swap(h, h.next);
				}
				h = h.next;
			}
			p = p.next;
		}
		return node;
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
		Node n1 = new Node(2);
		Node n2 = new Node(3);
		Node n3 = new Node(4);

		Node n4 = new Node(3);
		Node n5 = new Node(4);
		Node n6 = new Node(5);

		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n5;
		n5.next = n6;

		// n1 = insertionSortList(n1);
		n1 = insertionSort(n1);

		printList(n1);

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
