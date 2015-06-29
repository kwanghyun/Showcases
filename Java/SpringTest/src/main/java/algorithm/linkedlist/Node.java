package algorithm.linkedlist;

public class Node {
	public int val;
	public char ch;
	public Node next;
	public Node previous;

	public Node(int x) {
		val = x;
		next = null;
		previous = null;
	}

	public Node(char x) {
		ch = x;
		next = null;
		previous = null;
	}
}
