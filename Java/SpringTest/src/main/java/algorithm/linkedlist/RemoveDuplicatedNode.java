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

	public Node deleteDuplicates(Node head) {
		if (head == null || head.next == null)
			return head;

		Node prev = head;
		Node curr = head.next;

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

	public Node deleteDuplicates2(Node head) {
		if (head == null || head.next == null)
			return head;
		
		Node curr = head;
		while (curr != null && curr.next != null) {
			if (curr.val == curr.next.val) {
				curr.next = curr.next.next;
			} else {
				curr = curr.next;
			}
		}
		return head;
	}

	public String toString(Node root) {
		StringBuilder sb = new StringBuilder();
		Node tmp = root;
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
