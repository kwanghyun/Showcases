package algorithm.linkedlist;

public class Queue {
	ListNode first, last;

	public void enqueue(ListNode newNode) {
		if (first == null) {
			first = newNode;
			last = first;
		} else {
			last.next = newNode;
			last = newNode;
		}
	}

	public ListNode dequeue() {
		if (first == null) {
			return null;
		} else {
			ListNode temp = new ListNode(first.val);
			first = first.next;
			return temp;
		}
	}
}
