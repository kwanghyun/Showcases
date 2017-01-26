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

	public ListNode mergeSort(ListNode head) {
		if (head == null || head.next == null)
			return head;

		ListNode slow = head;
		ListNode fast = head;
		ListNode left = head;

		while (fast != null && fast.next != null && fast.next.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}

		ListNode right = slow.next;
		slow.next = null;

		ListNode first = mergeSort(left);
		ListNode second = mergeSort(right);

		ListNode merged = mergeI(first, second);
		return merged;
	}

	public ListNode mergeI(ListNode n1, ListNode n2) {
		ListNode fakeHeader = new ListNode(0);
		ListNode curr = fakeHeader;
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

	public static ListNode mergeSortList(ListNode head) {

		if (head == null || head.next == null)
			return head;

		// count total number of elements
		int count = 0;
		ListNode p = head;
		while (p != null) {
			count++;
			p = p.next;
		}

		// break up to two list
		int middle = count / 2;

		ListNode l = head, r = null;
		ListNode p2 = head;
		int countHalf = 0;
		while (p2 != null) {
			countHalf++;
			ListNode next = p2.next;

			if (countHalf == middle) {
				p2.next = null;
				r = next;
			}
			p2 = next;
		}

		// now we have two parts l and r, recursively sort them
		ListNode h1 = mergeSortList(l);
		ListNode h2 = mergeSortList(r);

		// merge together
		ListNode merged = merge(h1, h2);

		return merged;
	}

	public static ListNode merge(ListNode l, ListNode r) {
		ListNode p1 = l;
		ListNode p2 = r;

		ListNode fakeHead = new ListNode(100);
		ListNode pNew = fakeHead;

		while (p1 != null || p2 != null) {

			if (p1 == null) {
				pNew.next = new ListNode(p2.val);
				p2 = p2.next;
				pNew = pNew.next;
			} else if (p2 == null) {
				pNew.next = new ListNode(p1.val);
				p1 = p1.next;
				pNew = pNew.next;
			} else {
				if (p1.val < p2.val) {
					// if(fakeHead)
					pNew.next = new ListNode(p1.val);
					p1 = p1.next;
					pNew = pNew.next;
				} else if (p1.val == p2.val) {
					pNew.next = new ListNode(p1.val);
					pNew.next.next = new ListNode(p1.val);
					pNew = pNew.next.next;
					p1 = p1.next;
					p2 = p2.next;

				} else {
					pNew.next = new ListNode(p2.val);
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
		ListNode unsorted = LinkedListUtils.generateListFromArray(arr);
		LinkedListUtils.printNodes(unsorted);
		System.out.println("------------------------");
		ListNode sorted = ob.mergeSort(unsorted);
		// Node sorted = mergeSortList(unsorted);
		LinkedListUtils.printNodes(sorted);
	}
}
