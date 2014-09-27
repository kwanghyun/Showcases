package algorithm.linkedlist;

public class RemoveDuplicatedNode {
	// Write code to remove duplicates from an unsorted linked list.
	// FOLLOW UP
	// How would you solve this problem if a temporary buffer is not allowed?
	Node first;

	public void insert(Node newNode) {
		Node current = first;
		first = newNode;
		first.next = current;
	}

	public void removeDuplicatedNode(Node root) {
		while (root != null) {
			Node pointer = root.next;
			Node previous = root;
			while (pointer != null) {
				if (root.ch == pointer.ch) {
					previous.next = pointer.next;
					if (previous.next == null)
						break;
					pointer = previous.next.next;
				}
				previous = pointer;
				pointer = pointer.next;
			}
			root = root.next;
		}
	}

	public String toString(Node root) {
		StringBuilder sb = new StringBuilder();
		Node tmp = root;
		while (tmp != null) {
			sb.append(tmp.ch).append(", ");
			tmp = tmp.next;
		}
		return sb.toString();
	}

	public static void main(String args[]) {
		RemoveDuplicatedNode rdn = new RemoveDuplicatedNode();

		StringBuilder sb = new StringBuilder("FOLLOW UP");
		sb.reverse();
		char[] charArr = new String(sb).toCharArray();

		for (char i : charArr) {
			Node node = new Node(i);
			rdn.insert(node);
		}
		System.out.println(rdn.toString(rdn.first));
		rdn.removeDuplicatedNode(rdn.first);
		System.out.println("____________________");
		System.out.println(rdn.toString(rdn.first));
	}
}
