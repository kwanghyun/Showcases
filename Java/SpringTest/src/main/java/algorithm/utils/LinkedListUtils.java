package algorithm.utils;

import java.util.List;

import algorithm.linkedlist.ListNode;

public class LinkedListUtils {

	public static ListNode generateOrderedList(int count) {
		ListNode fakeNode = new ListNode(0);
		ListNode p = fakeNode;
		for (int i = 0; i < count; i++) {
			ListNode n = new ListNode(i + 1);
			p.next = n;
			p = n;
		}
		return fakeNode.next;
	}

	public static ListNode generateReverseOrderedList(int count) {
		ListNode fakeNode = new ListNode(0);
		ListNode p = fakeNode;
		for (int i = count; i > 0; i--) {
			ListNode n = new ListNode(i);
			p.next = n;
			p = n;
		}
		return fakeNode.next;
	}

	public static int[] convertStrToArr(String str) {
		str.replace("[", "").replace("]", "");
		String[] strArr = str.split(",");
		int[] arr = new int[strArr.length];
		for (int i = 0; i < strArr.length; i++) {
			arr[i] = Integer.parseInt(strArr[i]);
		}
		return arr;
	}

	public static ListNode generateListFromString(String str) {
		int[] arr = convertStrToArr(str);
		ListNode fakeHeader = new ListNode(100);
		ListNode p = fakeHeader;
		for (int val : arr) {
			ListNode t = new ListNode(val);
			p.next = t;
			p = t;
		}
		return fakeHeader.next;
	}

	public static ListNode generateListFromArray(int[] arr) {
		ListNode fakeHeader = new ListNode(100);
		ListNode p = fakeHeader;
		for (int val : arr) {
			ListNode t = new ListNode(val);
			p.next = t;
			p = t;
		}
		return fakeHeader.next;
	}

	public static ListNode generateListFromRange(int start, int end) {
		ListNode fakeHeader = new ListNode(100);
		ListNode p = fakeHeader;
		while (start <= end) {
			ListNode t = new ListNode(start);
			p.next = t;
			p = t;
			start++;
		}
		return fakeHeader.next;
	}

	public static ListNode generateUnSortedList() {
		ListNode node1 = new ListNode(1);
		ListNode node2 = new ListNode(2);
		ListNode node3 = new ListNode(3);
		ListNode node4 = new ListNode(4);
		ListNode node5 = new ListNode(5);
		ListNode node6 = new ListNode(6);
		node1.next = node4;
		node4.next = node3;
		node3.next = node6;
		node6.next = node2;
		node2.next = node5;
		node5.next = null;

		return node1;
	}

	public static void printNodes(ListNode node) {
		ListNode p = node;
		while (p != null) {
			System.out.println(p);
			p = p.next;
		}
	}

	public static void drawList(ListNode node) {
		ListNode p = node;
		while (p != null) {
			if (p.next == null) {
				System.out.print(p.val);
			} else {
				System.out.print(p.val + " -> ");
			}
			p = p.next;
		}
		System.out.println("");
	}

	public static void main(String[] args) {
		int[] arr = { 1, 3, 4, 2, 5 };
		printNodes(generateListFromArray(arr));
		System.out.println("");
		printNodes(generateOrderedList(6));
		System.out.println("");
		printNodes(generateReverseOrderedList(6));

	}
}
