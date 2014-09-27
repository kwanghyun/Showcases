package algorithm.linkedlist;

//1
//2(new node, first) -> 1
//3 -> 2 -> 1
//4 -> 3 -> 2 -> 1
public class Stack {
	private int total;
	private Node first;

	public Stack() {
	}

	public void push(int value) {
		Node current = first;
		first = new Node(value);
		first.val = value;
		first.next = current;
		total++;
	}

	public int pop() {
		if (first == null)
			new IllegalStateException();
		int value = first.val;
		first = first.next;
		total--;
		return value;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Node tmp = first;
		while (tmp != null) {
			sb.append(tmp.val).append(", ");
			tmp = tmp.next;
		}
		return sb.toString();
	}

	public static void main(String args[]) {
		int length = 5;
		Stack stack = new Stack();
		for (int i = 1; i < length + 1; i++)
			stack.push(i);

		System.out.println(stack.toString());
		System.out.println("---------------------------------------");
		for (int i = 1; i < length + 1; i++)
			System.out.println(stack.pop());
	}
}
