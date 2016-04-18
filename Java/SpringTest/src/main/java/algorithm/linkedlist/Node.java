package algorithm.linkedlist;

public class Node {
	public int val;
	public char ch;
	public Node next;
	public Node previous;
	public Node random;
	public int min;

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

	@Override
	public String toString() {
		return "Node [val=" + val + ", ch=" + ch + ", next=" + next + ", previous=" + previous + ", random=" + random
				+ ", min=" + min + "]";
	}

}
