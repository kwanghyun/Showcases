package algorithm.linkedlist;

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
