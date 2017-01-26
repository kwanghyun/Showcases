package algorithm.linkedlist;

import algorithm.Utils;

/*
 * Given a linked list where every node represents a linked list and contains
 * two pointers of its type:
 * (i) Pointer to next node in the main list (we call it ‘right’ pointer in
 * below code)
 * (ii) Pointer to a linked list where this node is head (we call it ‘down’
 * pointer in below code).
 * All linked lists are sorted. See the following example
 * 
       5 -> 10 -> 19 -> 28
       |       |        |       |
       V      V       V      V
       7     20      22    35
       |                |      |
       V               V     V
       8               50   40
       |                        |
       V                       V
       30                    45
 * Write a function flatten() to flatten the lists into a single linked
 * list. The flattened linked list should also be sorted. For example, for
 * the above input list, output list should be
 * 5->7->8->10->19->20->22->28->30->35->40->45->50.
 */
public class FlattenLinkedList {
	ListNode head; // head of list
	// An utility function to merge two sorted linked lists

	ListNode mergeI(ListNode c, ListNode n) {
		if (c == null)
			return n;
		if (n == null)
			return c;

		if (c.val < n.val) {
			head.next = mergeI(c.child, n);
			head = c;
			return c;
		} else {
			head.next = mergeI(n.next, n.child);
			head = n;
			return n;
		}
	}

	boolean isNextIsSmaller(ListNode nxt, ListNode child) {
		if (nxt == null)
			return false;

		// if second linked list is empty then first is the result
		if (child == null)
			return true;

		if (nxt.val < child.val) {
			return true;
		} else {
			return false;
		}
	}

	void printInOrder(ListNode node) {
		if (node == null)
			return;
		System.out.println(node.val);
		if (isNextIsSmaller(head.next, head.child)) {
			printInOrder(node.next);
			printInOrder(node.child);
		} else {
			printInOrder(node.child);
			printInOrder(node.next);
		}
	}

	ListNode merge(ListNode a, ListNode b, int callstack) {
		// if first linked list is empty then second is the answer
		if (a == null)
			return b;

		// if second linked list is empty then first is the result
		if (b == null)
			return a;

		// compare the data members of the two linked lists
		// and put the larger one in the result
		ListNode result;

		if (a.val < b.val) {
			result = a;
			result.child = merge(a.child, b, callstack + 1);
		} else {
			result = b;
			result.child = merge(a, b.child, callstack + 1);
		}
		Utils.printCS(callstack, "flatten() - result = " + result.val);
		return result;
	}

	ListNode flatten(ListNode root, int callstack) {
		// Base Cases
		if (root == null || root.next == null)
			return root;

		// recur for list on right
		Utils.printCS(callstack, "flatten() - root.val = " + root.val);
		root.next = flatten(root.next, callstack + 1);

		// now merge
		Utils.printCS(callstack, "merge() - root.val = " + root.val);
		root = merge(root, root.next, callstack + 1);
		Utils.printCS(callstack, "final() - root.val = " + root.val);
		// return the root, it will be in turn merged with its left
		return root;
	}

	/*
	 * Utility function to insert a node at begining of the linked list
	 */
	ListNode push(ListNode head_ref, int data) {
		/*
		 * 1 & 2: Allocate the Node & Put in the data
		 */
		ListNode new_node = new ListNode(data);

		/* 3. Make next of new Node as head */
		new_node.child = head_ref;

		/* 4. Move the head to point to new Node */
		head_ref = new_node;

		/* 5. return to link it back */
		return head_ref;
	}

	/* Function to print nodes in the flattened linked list */
	void printList() {
		ListNode temp = head;
		while (temp != null) {
			System.out.print(temp.val + " ");
			temp = temp.child;
		}
		System.out.println();
	}

	/* Drier program to test above functions */
	public static void main(String args[]) {
		FlattenLinkedList ob = new FlattenLinkedList();

		ob.head = ob.push(ob.head, 30);
		ob.head = ob.push(ob.head, 8);
		ob.head = ob.push(ob.head, 7);
		ob.head = ob.push(ob.head, 5);

		ob.head.next = ob.push(ob.head.next, 20);
		ob.head.next = ob.push(ob.head.next, 10);

		ob.head.next.next = ob.push(ob.head.next.next, 50);
		ob.head.next.next = ob.push(ob.head.next.next, 22);
		ob.head.next.next = ob.push(ob.head.next.next, 19);

		ob.head.next.next.next = ob.push(ob.head.next.next.next, 45);
		ob.head.next.next.next = ob.push(ob.head.next.next.next, 40);
		ob.head.next.next.next = ob.push(ob.head.next.next.next, 35);
		ob.head.next.next.next = ob.push(ob.head.next.next.next, 20);

		// flatten the list
		ob.head = ob.flatten(ob.head, 0);
		ob.printInOrder(ob.head);
		// ob.printList();
	}
}
