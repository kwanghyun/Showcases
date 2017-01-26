package algorithm.linkedlist;

/*
 * Given a linked list, reverse the nodes of a linked list k at a time and
 * return its modified list.
 * 
 * If the number of nodes is not a multiple of k then left-out nodes in the
 * end should remain as it is.
 * 
 * You may not alter the values in the nodes, only nodes itself may be
 * changed.
 * 
 * Only constant memory is allowed.
 * 
 * For example, Given this linked list: 1->2->3->4->5
 * 
 * For k = 2, you should return: 2->1->4->3->5
 * 
 * For k = 3, you should return: 3->2->1->4->5
 */
public class ReverseNodesInKGroup {
public ListNode reverseKGroup(ListNode head, int k) {
	if (head == null || k == 1)
		return head;

	ListNode fake = new ListNode(0);
	fake.next = head;
	ListNode pre = fake;
	int i = 0;

	ListNode p = head;
	while (p != null) {
		i++;
		if (i % k == 0) {
			pre = reverse(pre, p.next);
			p = pre.next;
		} else {
			p = p.next;
		}
	}

	return fake.next;
}

/*
 * 0->1->2->3->4->5->6 | | pre next
 *
 * after calling pre = reverse(pre, next)
 * 
 * 0->3->2->1->4->5->6 | | pre next
 */
	public ListNode reverse(ListNode start, ListNode end) {
		ListNode prev = start.next;
		ListNode curr = prev.next;

		while (curr != end) {
			prev.next = curr.next;
			curr.next = start.next;
			start.next = curr;
			curr = prev.next;
		}

		return prev;
	}
}
