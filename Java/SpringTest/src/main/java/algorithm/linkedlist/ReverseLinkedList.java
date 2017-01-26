package algorithm.linkedlist;

public class ReverseLinkedList {

	public ListNode reverseList(ListNode head) {

		if (head.next == null) // One node only
			return null;

		ListNode second = head.next;
		ListNode third = second.next;

		head.next = null;
		second.next = head;

		if (second.next == null) {
			// Two Node only, it's already reversed.
			return second;
		}

		ListNode currentNode = third;
		ListNode previousNode = second;

		while (currentNode != null) {
			ListNode nextNode = currentNode.next;
			// Only current.next to point previous Node
			currentNode.next = previousNode;
			previousNode = currentNode;
			currentNode = nextNode;
		}
		return previousNode;
	}

public ListNode reverseList2(ListNode head) {
	if (head == null || head.next == null)
		return head;

	ListNode prev = head;
	ListNode curr = head.next;
	head.next = null;

	while (prev != null && curr != null) {
		ListNode next = curr.next;
		curr.next = prev;
		prev = curr;
		curr = next;
	}
	return prev;
}

	public ListNode reverseList3(ListNode head) {
		if (head == null || head.next == null)
			return head;

		// get second node
		ListNode second = head.next;

		// set first’s next to be null
		head.next = null;

		ListNode rest = reverseList3(second);
		second.next = head;
		return rest;
	}

	public void printAllNode(ListNode head) {
		ListNode currentNode = head;
		while (currentNode != null) {
			System.out.print("[" + currentNode.val + "] ");
			currentNode = currentNode.next;
		}
	}

	public ListNode generateList() {
		ListNode node1 = new ListNode(1);
		ListNode node2 = new ListNode(2);
		ListNode node3 = new ListNode(3);
		ListNode node4 = new ListNode(4);
		ListNode node5 = new ListNode(5);
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		node4.next = node5;
		node5.next = null;
		return node1;
	}

	public static void main(String args[]) {
		ReverseLinkedList mrl = new ReverseLinkedList();
		ListNode head = mrl.generateList();
		ListNode head2 = mrl.generateList();
		mrl.printAllNode(head);
		System.out.println("\n-----------------------------------------");
		mrl.printAllNode(mrl.reverseList2(head));
		System.out.println("\n-----------------------------------------");
		mrl.printAllNode(mrl.reverseList3(head2));
	}
}
