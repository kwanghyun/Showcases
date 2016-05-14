package algorithm.linkedlist;

public class Utils {

	public static Node generateOneList() {
		Node node1 = new Node(1);
		node1.next = null;
		return node1;
	}

	public static Node generateTwoList() {
		Node node1 = new Node(1);
		Node node2 = new Node(2);
		node1.next = node2;
		node2.next = null;
		return node1;
	}

	public static Node generate5List() {
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

	public static Node generate6List() {
		Node node1 = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		Node node4 = new Node(4);
		Node node5 = new Node(5);
		Node node6 = new Node(6);
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		node4.next = node5;
		node5.next = node6;
		node6.next = null;
		return node1;
	}

	public static Node generateNonSortedList() {
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
	

	public static void printNodes(Node head) {
		while (head != null) {
			System.out.println(head);
			head = head.next;
		}
	}
}
