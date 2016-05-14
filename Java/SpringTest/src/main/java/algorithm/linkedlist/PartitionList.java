package algorithm.linkedlist;

/*
 * Given a linked list and a value x, partition it such that all nodes less
 * than x come before nodes greater than or equal to x.
 * 
 * You should preserve the original relative order of the nodes in each of
 * the two partitions.
 * 
 * For example, given 1->4->3->2->5->2 and x = 3, return 1->2->2->4->3->5.
 * 
 */
public class PartitionList {
	public Node partition(Node head, int x) {
		if (head == null)
			return null;

		Node fakeHead1 = new Node(0);
		Node fakeHead2 = new Node(0);
		fakeHead1.next = head;

		Node p = head;
		Node prev = fakeHead1;
		Node p2 = fakeHead2;

		while (p != null) {
			if (p.val < x) {
				p = p.next;
				prev = prev.next;
			} else {

				p2.next = p;
				prev.next = p.next;

				p = prev.next;
				p2 = p2.next;
			}
		}

		// close the list
		p2.next = null;

		prev.next = fakeHead2.next;

		return fakeHead1.next;
	}
}
