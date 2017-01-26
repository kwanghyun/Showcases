package algorithm.linkedlist;

/*We have one single linked list. How we’ll travers it that we reach till 
 * second last (n-1) node. If we want to reach till (n\2) node.*/
public class SecondLastNode {

	public ListNode solution(ListNode head) {
		ListNode node = head;

		if (node == null || node.next == null)
			return null;

		while (node != null) {

			if (node.next != null && node.next.next == null) {
				break;
			} else {
				node = node.next;
			}

			if (node.next != null && node.next.next == null) {
				break;
			} else {
				node = node.next;
			}

		}
		return node;
	}

}
