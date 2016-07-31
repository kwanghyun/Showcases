package algorithm.linkedlist;

import algorithm.utils.LinkedListUtils;

/*
 * Sort a linked list in O(n log n) time using constant space complexity.
 * 
 * Keys for solving the problem
 * 
 * Break the list to two in the middle Recursively sort the two sub lists
 * Merge the two sub lists This is my accepted answer for the problem.
 */
public class MergeSortLinkedList {

	public Node mergeSort(Node head) {
		if (head == null || head.next == null)
			return head;

		Node slow = head;
		Node fast = head;
		Node left = head;

		while (fast != null && fast.next != null && fast.next.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}

		Node right = slow.next;
		slow.next = null;

		Node first = mergeSort(left);
		Node second = mergeSort(right);

		Node merged = mergeI(first, second);
		return merged;
	}

	public Node mergeI(Node n1, Node n2) {
		Node fakeHeader = new Node(0);
		Node curr = fakeHeader;
		while (n1 != null || n2 != null) {
			if (n2 == null) {
				curr.next = n1;
				n1 = n1.next;
				curr = curr.next;
			} else if (n1 == null) {
				curr.next = n2;
				n2 = n2.next;
				curr = curr.next;
			} else {
				if (n1.val < n2.val) {
					curr.next = n1;
					n1 = n1.next;
					curr = curr.next;
				} else {
					curr.next = n2;
					n2 = n2.next;
					curr = curr.next;
				}
			}
		}
		return fakeHeader.next;
	}

	public static Node mergeSortList(Node head) {

		if (head == null || head.next == null)
			return head;

		// count total number of elements
		int count = 0;
		Node p = head;
		while (p != null) {
			count++;
			p = p.next;
		}

		// break up to two list
		int middle = count / 2;

		Node l = head, r = null;
		Node p2 = head;
		int countHalf = 0;
		while (p2 != null) {
			countHalf++;
			Node next = p2.next;

			if (countHalf == middle) {
				p2.next = null;
				r = next;
			}
			p2 = next;
		}

		// now we have two parts l and r, recursively sort them
		Node h1 = mergeSortList(l);
		Node h2 = mergeSortList(r);

		// merge together
		Node merged = merge(h1, h2);

		return merged;
	}

	public static Node merge(Node l, Node r) {
		Node p1 = l;
		Node p2 = r;

		Node fakeHead = new Node(100);
		Node pNew = fakeHead;

		while (p1 != null || p2 != null) {

			if (p1 == null) {
				pNew.next = new Node(p2.val);
				p2 = p2.next;
				pNew = pNew.next;
			} else if (p2 == null) {
				pNew.next = new Node(p1.val);
				p1 = p1.next;
				pNew = pNew.next;
			} else {
				if (p1.val < p2.val) {
					// if(fakeHead)
					pNew.next = new Node(p1.val);
					p1 = p1.next;
					pNew = pNew.next;
				} else if (p1.val == p2.val) {
					pNew.next = new Node(p1.val);
					pNew.next.next = new Node(p1.val);
					pNew = pNew.next.next;
					p1 = p1.next;
					p2 = p2.next;

				} else {
					pNew.next = new Node(p2.val);
					p2 = p2.next;
					pNew = pNew.next;
				}
			}
		}

		// printList(fakeHead.next);
		return fakeHead.next;
	}

	public static void main(String[] args) {
		MergeSortLinkedList ob = new MergeSortLinkedList();
		// int[] arr = { 3, 5, 2, 1, 4, 6 };
		int[] arr = { 3, 5, 2, 7, 1, 4, 6 };
		Node unsorted = LinkedListUtils.generateListFromArray(arr);
		LinkedListUtils.printNodes(unsorted);
		System.out.println("------------------------");
		Node sorted = ob.mergeSort(unsorted);
		// Node sorted = mergeSortList(unsorted);
		LinkedListUtils.printNodes(sorted);
	}
}
