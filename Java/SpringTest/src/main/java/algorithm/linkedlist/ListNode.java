package algorithm.linkedlist;

public class ListNode {
	public int val;
	public char ch;
	public ListNode next;
	public ListNode prev;
	public ListNode rnd;
	public ListNode child;
	public int min;

	public ListNode(int x) {
		val = x;
		next = null;
		prev = null;
	}

	public ListNode(char x) {
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

		if (child != null)
			str.append(", child.val=" + child.val);

		if (rnd != null)
			str.append(", rnd.val=" + rnd.val);

		if (min != 0)
			str.append(", min=" + min);

		str.append("]");
		return str.toString();
	}

}
