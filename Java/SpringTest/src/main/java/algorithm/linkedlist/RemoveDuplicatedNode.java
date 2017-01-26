package algorithm.linkedlist;

/*Given a sorted linked list, delete all duplicates such that each element appear only
 once.
 For example,
 Given 1->1->2, return 1->2.
 Given 1->1->2->3->3, return 1->2->3.

 The key of this problem is using the right loop condition. And change what is necessary
 in each loop. You can use different iteration conditions like the following 2
 solutions.
 */
public class RemoveDuplicatedNode {

	public ListNode deleteDuplicates(ListNode head) {
		if (head == null || head.next == null)
			return head;

		ListNode prev = head;
		ListNode curr = head.next;

		while (curr != null) {
			if (curr.val == prev.val) {
				prev.next = curr.next;
				curr = curr.next;
			} else {
				prev = curr;
				curr = curr.next;
			}
		}
		return head;
	}

	public ListNode deleteDuplicates2(ListNode head) {
		if (head == null || head.next == null)
			return head;
		
		ListNode curr = head;
		while (curr != null && curr.next != null) {
			if (curr.val == curr.next.val) {
				curr.next = curr.next.next;
			} else {
				curr = curr.next;
			}
		}
		return head;
	}

	public String toString(ListNode root) {
		StringBuilder sb = new StringBuilder();
		ListNode tmp = root;
		while (tmp != null) {
			sb.append(tmp.ch).append(", ");
			tmp = tmp.next;
		}
		return sb.toString();
	}

	public static void main(String args[]) {
		RemoveDuplicatedNode rdn = new RemoveDuplicatedNode();

	}
}
