package algorithm.linkedlist;

public class Node {
	public int val;
	public char ch;
	public Node next;
	public Node prev;
	public Node rnd;
	public int min;

	public Node(int x) {
		val = x;
		next = null;
		prev = null;
	}

	public Node(char x) {
		ch = x;
		next = null;
		prev = null;
	}

	@Override
	public String toString() {

		StringBuilder str = new StringBuilder();

		if (val != 0)
			str.append("Node [val=" + val);
		else
			str.append("Node [ch=" + ch);

		if (next != null)
			str.append(", next.val=" + next.val);

		if (prev != null)
			str.append(", prev.val=" + prev.val);

		if (rnd != null)
			str.append(", rnd.val=" + rnd.val);

		if (min != 0)
			str.append(", min=" + min);

		str.append("]");
		return str.toString();
	}

}
