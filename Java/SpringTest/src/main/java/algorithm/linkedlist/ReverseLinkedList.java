package algorithm.linkedlist;

public class ReverseLinkedList {
	public void reverseListIteratively(Node head) {
		if (head == null || head.next == null)
			return; // empty or just one node in list

		Node Second = head.next;

		// store third node before we change
		Node Third = Second.next;

		// Second's next pointer
		Second.next = head; // second now points to head
		head.next = null; // change head pointer to NULL

		// only two nodes, which we already reversed
		if (Third == null)
			return;

		Node CurrentNode = Third;
		Node PreviousNode = Second;

		while (CurrentNode != null) {
			Node NextNode = CurrentNode.next;
			CurrentNode.next = PreviousNode;
			PreviousNode = CurrentNode;
			CurrentNode = NextNode;
		}

		head = PreviousNode; // reset the head node
	}
}
