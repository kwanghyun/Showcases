package algorithm.linkedlist;

public class ReverseLinkedList {

	public Node reverseList(Node head) {

		if (head.next == null) // One node only
			return null;

		Node second = head.next;
		Node third = second.next;

		head.next = null;
		second.next = head;

		if (second.next == null) {
			// Two Node only, it's already reversed.
			return second;
		}

		Node currentNode = third;
		Node previousNode = second;

		while (currentNode != null) {
			Node nextNode = currentNode.next;
			// Only current.next to point previous Node
			currentNode.next = previousNode;
			previousNode = currentNode;
			currentNode = nextNode;
		}
		return previousNode;
	}

public Node reverseList2(Node head) {
	if (head == null || head.next == null)
		return head;

	Node prev = head;
	Node curr = head.next;
	head.next = null;

	while (prev != null && curr != null) {
		Node next = curr.next;
		curr.next = prev;
		prev = curr;
		curr = next;
	}
	return prev;
}

	public Node reverseList3(Node head) {
		if (head == null || head.next == null)
			return head;

		// get second node
		Node second = head.next;

		// set first’s next to be null
		head.next = null;

		Node rest = reverseList3(second);
		second.next = head;
		return rest;
	}

	public void printAllNode(Node head) {
		Node currentNode = head;
		while (currentNode != null) {
			System.out.print("[" + currentNode.val + "] ");
			currentNode = currentNode.next;
		}
	}

	public Node generateList() {
		Node node1 = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		Node node4 = new Node(4);
		Node node5 = new Node(5);
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		node4.next = node5;
		node5.next = null;
		return node1;
	}

	public static void main(String args[]) {
		ReverseLinkedList mrl = new ReverseLinkedList();
		Node head = mrl.generateList();
		Node head2 = mrl.generateList();
		mrl.printAllNode(head);
		System.out.println("\n-----------------------------------------");
		mrl.printAllNode(mrl.reverseList2(head));
		System.out.println("\n-----------------------------------------");
		mrl.printAllNode(mrl.reverseList3(head2));
	}
}
