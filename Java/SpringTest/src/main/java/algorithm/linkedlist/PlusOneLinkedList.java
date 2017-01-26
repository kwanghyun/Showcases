package algorithm.linkedlist;

import algorithm.utils.LinkedListUtils;

/*
 * Given a non-negative number represented as a singly linked list of
 * digits, plus one to the number.
 * 
 * The digits are stored such that the most significant digit is at the head
 * of the list.
 * 
 * Example: 
 * Input: 1->2->3
 * 
 * Output: 1->2->4
 */
public class PlusOneLinkedList {
	public ListNode plusOne(ListNode head) {
		int listLen = 0;
		StringBuilder numStr = new StringBuilder();
		ListNode p = head;
		while (p != null) {
			listLen++;
			numStr.append(p.val);
			p = p.next;
		}

		// Add one
		int carry = 1;
		for (int i = numStr.length() - 1; i >= 0; i--) {
			int num = carry + numStr.charAt(i) - '0';
			carry = num / 10;
			numStr.replace(i, i + 1, "" + num % 10);
		}
		if (carry > 0)
			numStr.insert(0, carry);

		p = head;
		int cIdx = 0;
		if (listLen < numStr.length()) {
			ListNode newNode = new ListNode((int) numStr.charAt(0) - '0');
			newNode.next = head;
			head = newNode;
			cIdx++;
		}

		while (p != null) {
			p.val = numStr.charAt(cIdx) - '0';
			p = p.next;
			cIdx++;
		}

		return head;
	}

	public static void main(String[] args) {
		// StringBuilder sb = new StringBuilder("0123456");
		// sb.replace(6, 7, "E");
		// System.out.println(sb);

		PlusOneLinkedList ob = new PlusOneLinkedList();
		ListNode head = LinkedListUtils.generateListFromString("9");
		head = ob.plusOne(head);
		LinkedListUtils.drawList(head);
	}
}
