package algorithm.linkedlist;

import java.util.HashMap;

public class CloneRandomLinkedList {

	/*
	 * A linked list is given such that each node contains an additional random
	 * pointer which could point to any node in the list or null.
	 * 
	 * Return a deep copy of the list.
	 */

	public ListNode clone(ListNode head) {
		if (head == null)
			return null;
		
		HashMap<ListNode, ListNode> map = new HashMap<>();
		ListNode clone_h = new ListNode(head.val);

		ListNode origin_p = head;
		ListNode clone_p = clone_h;
		
		map.put(head, clone_h);
		origin_p = origin_p.next;
		
		while (origin_p != null) {
			ListNode newNode = new ListNode(origin_p.val);
			map.put(origin_p, newNode);
			clone_p.next = newNode;
			clone_p = newNode;
			origin_p = origin_p.next;
		}

		origin_p = head;
		clone_p = clone_h;
		while (origin_p != null) {
			if (origin_p.rnd != null)
				clone_p.rnd = map.get(origin_p.rnd);
			else
				clone_p.rnd = null;

			origin_p = origin_p.next;
			clone_p = clone_p.next;
		}

		return clone_h;
	}

	public static void main(String args[]) {
		CloneRandomLinkedList obj = new CloneRandomLinkedList();
		ListNode node1 = new ListNode(1);
		ListNode node2 = new ListNode(2);
		ListNode node3 = new ListNode(3);
		ListNode node4 = new ListNode(4);
		ListNode node5 = new ListNode(5);

		node1.next = node2;
		node1.rnd = node3;
		node2.next = node3;
		node2.rnd = node4;
		node3.next = node4;
		node3.rnd = node1;
		node4.next = node5;
		node4.rnd = node4;
		node5.next = null;
		node5.rnd = null;

		ListNode originHead = node1;
		ListNode cloneHead = obj.clone(originHead);
		System.out.println("------------------------------");
		obj.check(originHead, cloneHead);

	}

	public void check(ListNode origin, ListNode clone) {

		while (origin != null) {

			if (origin.next != null && clone.next != null
					&& origin.next == clone.next)
				throw new RuntimeException("Pointing the same object");
			if (origin.rnd != null && clone.rnd != null
					&& origin.rnd == clone.rnd)
				throw new RuntimeException("Pointing the same object");

			System.out.println(clone.val);
			origin = origin.next;
			clone = clone.next;
		}

	}

}
