package algorithm.linkedlist;

/*
 * Given a singly linked list, determine if it is a palindrome.
 * 
 * Java Solution 1
 * 
 * We can create a new list in reversed order and then compare each
 * node. The time and space are O(n).
 */
public class PalindromeLinkedList {

	public boolean check(Node head) {
		if (head == null)
			return false;

		Node originH = head;
		Node reverseH = null;

		while (originH != null) {
			Node node = new Node(originH.val);
			node.next = reverseH;
			reverseH = node;
			originH = originH.next;
		}

		originH = head;
		while (originH != null) {
			if (originH.val != reverseH.val)
				return false;
			originH = originH.next;
			reverseH = reverseH.next;
		}
		
		return true;
	}

	public Node generateLinkedList() {
		Node node1 = new Node(1);
		Node node3 = new Node(3);
		Node node5 = new Node(5);
		Node node7 = new Node(7);
		Node node9 = new Node(9);
		node1.next = node3;
		node3.next = node5;
		node5.next = node7;
		node7.next = node9;
		node9.next = null;
		return node1;
	}

	public Node generateLinkedList2() {
		Node node1 = new Node(1);
		Node node3 = new Node(3);
		Node node5 = new Node(5);
		Node node7 = new Node(3);
		Node node9 = new Node(1);
		node1.next = node3;
		node3.next = node5;
		node5.next = node7;
		node7.next = node9;
		node9.next = null;
		return node1;
	}

	public static void main(String[] args) {
		PalindromeLinkedList ob = new PalindromeLinkedList();
		System.out.println(ob.check(ob.generateLinkedList()));
		System.out.println(ob.check(ob.generateLinkedList2()));

	}
}
