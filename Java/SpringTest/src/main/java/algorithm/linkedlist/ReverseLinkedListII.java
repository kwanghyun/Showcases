package algorithm.linkedlist;

/*
 * Reverse a linked list from position m to n. Do it in-place and in
 * one-pass.
 * 
 * For example: given 1->2->3->4->5->NULL, m = 2 and n = 4, return
 * 1->4->3->2->5->NULL
 */
public class ReverseLinkedListII {
	public Node reverseBetween(Node head, int m, int n) {
		if (m == n)
			return head;

		Node prev = null;// track (m-1)th node
		Node first = new Node(0);// first's next points to mth
		Node second = new Node(0);// second's next points to (n+1)th

		int i = 0;
		Node p = head;
		while (p != null) {
			i++;
			if (i == m - 1) {
				prev = p;
			}

			if (i == m) {
				first.next = p;
			}

			if (i == n) {
				second.next = p.next;
				p.next = null;
			}

			p = p.next;
		}
		if (first.next == null)
			return head;

		// reverse list [m, n]
		Node p1 = first.next;
		Node p2 = p1.next;
		p1.next = second.next;

		while (p1 != null && p2 != null) {
			Node t = p2.next;
			p2.next = p1;
			p1 = p2;
			p2 = t;
		}

		// connect to previous part
		if (prev != null)
			prev.next = p1;
		else
			return p1;

		return head;
	}
}
