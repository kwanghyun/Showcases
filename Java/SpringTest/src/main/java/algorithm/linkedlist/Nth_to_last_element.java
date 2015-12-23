package algorithm.linkedlist;

/*Given a linked list, remove the nth node from the end of list and return its head.
 For example, given linked list 1->2->3->4->5 and n = 2, the result is 1->2->3->5.

 Use fast and slow pointers. The fast pointer is n steps ahead of the slow pointer. When
 the fast reaches the end, the slow pointer points at the previous element of the target
 element.
 */
public class Nth_to_last_element {
	public Node removeNthFromEnd(Node head, int n) {

		if (head == null)
			return null;

		Node fast = head;
		Node slow = head;

		for (int i = 0; i < n; i++) {
			fast = fast.next;
		}

		// if remove the first node
		if (fast == null) {
			head = head.next;
			return head;
		}

		while (fast.next != null) {
			fast = fast.next;
			slow = slow.next;
		}

		slow.next = slow.next.next;

		return head;
	}
}
