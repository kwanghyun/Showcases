package algorithm.utils;

import java.util.List;

import algorithm.linkedlist.Node;

public class LinkedListUtils {

	public static Node generateOrderedList(int count) {
		Node fakeNode = new Node(0);
		Node p = fakeNode;
		for (int i = 0; i < count; i++) {
			Node n = new Node(i + 1);
			p.next = n;
			p = n;
		}
		return fakeNode.next;
	}

	public static Node generateReverseOrderedList(int count) {
		Node fakeNode = new Node(0);
		Node p = fakeNode;
		for (int i = count; i > 0; i--) {
			Node n = new Node(i);
			p.next = n;
			p = n;
		}
		return fakeNode.next;
	}

	public static Node generateListFromArray(int[] arr) {
		Node fakeHeader = new Node(100);
		Node p = fakeHeader;
		for (int val : arr) {
			Node t = new Node(val);
			p.next = t;
			p = t;
		}
		return fakeHeader.next;
	}

	public static Node generateListFromRange(int start, int end) {
		Node fakeHeader = new Node(100);
		Node p = fakeHeader;
		while (start <= end) {
			Node t = new Node(start);
			p.next = t;
			p = t;
			start++;
		}
		return fakeHeader.next;
	}

	public static Node generateUnSortedList() {
		Node node1 = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		Node node4 = new Node(4);
		Node node5 = new Node(5);
		Node node6 = new Node(6);
		node1.next = node4;
		node4.next = node3;
		node3.next = node6;
		node6.next = node2;
		node2.next = node5;
		node5.next = null;

		return node1;
	}

	public static void printNodes(Node node) {
		Node p = node;
		while (p != null) {
			System.out.println(p);
			p = p.next;
		}
	}

	public static void drawList(Node node) {
		Node p = node;
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
