package algorithm.linkedlist;

import java.util.HashSet;
import java.util.Set;

public class FindBeginingNode {

	// Given a circular linked list, implement an algorithm which returns node
	// at the beginning of the loop.
	// DEFINITION
	// Circular linked list: A (corrupt) linked list in which a node��s next
	// pointer points to an earlier node, so as to make a loop in the linked
	// list.
	// EXAMPLE
	// input: A -> B -> C -> D -> E -> C [the same C as earlier]
	// output: C

	ListNode head;

	public void insert(ListNode node) {
		ListNode current = head;
		node.next = current;
		head = node;
	}

	
	public ListNode Solution(ListNode head) {

		ListNode fast = head.next.next;
		ListNode slow = head.next;

		while (fast != null) {
			if (fast.ch == slow.ch) {
				break;
			} else {
				fast = fast.next.next;
				slow = slow.next;
			}
		}

		while (head.ch != slow.ch) {
			head = head.next;
			slow = slow.next;
		}
		return slow;
	}
	
	public static void main(String args[]) {
		FindBeginingNode fbn = new FindBeginingNode();
		ListNode nodec = new ListNode('C');
		ListNode nodee = new ListNode('E');
		ListNode noded = new ListNode('D');
		ListNode nodeb = new ListNode('B');
		ListNode nodea = new ListNode('C');

		fbn.insert(nodec);
		fbn.insert(nodee);
		fbn.insert(noded);
		fbn.insert(nodec);
		fbn.insert(nodeb);
		fbn.insert(nodea);


		System.out.println("------------------------------");
		System.out.println((fbn.Solution(fbn.head).ch));
		
	}
}
