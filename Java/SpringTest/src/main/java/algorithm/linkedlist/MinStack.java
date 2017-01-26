package algorithm.linkedlist;

/*
 * Design a stack that supports push, pop, top, and retrieving the minimum
 * element in constant time.
 * 
 * push(x) -- Push element x onto stack. pop() -- Removes the element on top
 * of the stack. top() -- Get the top element. getMin() -- Retrieve the
 * minimum element in the stack.
 * 
 * Analysis
 * 
 * To make constant time of getMin(), we need to keep track of the minimum
 * element for each element in the stack.
 * 
 * Java Solution
 * 
 * Define a node class that holds element value, min value, and pointer to
 * elements below it.
 */
public class MinStack {

	ListNode head;
	int min = Integer.MAX_VALUE;

	public void push(int value) {
		ListNode node = new ListNode(value);
		if (head != null) {
			node.next = head;
		}
		node.min = Math.min(min, node.val);
		min = node.min;
		head = node;
	}

	public ListNode pop() {
		if (head == null)
			throw new IllegalStateException("Stack is Empty");

		ListNode temp = head;
		head = head.next;
		return temp;
	}

	public ListNode top() {
		if (head == null)
			throw new IllegalStateException("Stack is Empty");
		return head;
	}

	public int getMin() {
		if (head == null)
			throw new IllegalStateException("Stack is Empty");
		return head.min;
	}

	public void printAll() {
		ListNode h = head;
		while (h != null) {
			System.out.println(h);
			h = h.next;
		}
	}

	public static void main(String[] args) {
		MinStack stack = new MinStack();
		stack.push(4);
		stack.push(2);
		stack.push(5);
		stack.push(1);
		stack.push(3);
		stack.push(6);
		stack.printAll();
		System.out.println("-----------------------");
		System.out.println("stack.top()" + stack.top());
		System.out.println("stack.getMin() : " + stack.getMin());
		System.out.println("-----------------------");
		stack.pop();
		stack.pop();
		stack.pop();
		System.out.println("stack.top()" + stack.top());
		System.out.println("stack.getMin() : " + stack.getMin());
		System.out.println("-----------------------");
	}
}
