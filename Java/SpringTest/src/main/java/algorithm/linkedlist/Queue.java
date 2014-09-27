package algorithm.linkedlist;

public class Queue {
	Node first, last;

	public void enqueue(Node newNode) {
		if (first == null) {
			first = newNode;
			last = first;
		} else {
			last.next = newNode;
			last = newNode;
		}
	}

	public Node dequeue() {
		if (first == null) {
			return null;
		} else {
			Node temp = new Node(first.val);
			first = first.next;
			return temp;
		}
	}
}
