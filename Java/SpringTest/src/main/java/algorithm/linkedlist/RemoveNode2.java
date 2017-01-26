package algorithm.linkedlist;

public class RemoveNode2 {
	// Implement an algorithm to delete a node in the middle of a single linked
	// list, given only access to that node.
	// EXAMPLE
	// Input: the node ��c�� from the linked list a->b->c->d->e
	// Result: nothing is returned, but the new linked list looks like
	// a->b->d->e

	//MISTAKE NODE : 
	// I can't delete a node because I've set a null on pointer "current" rather than Reference current.next. 
	
	ListNode root;

	public void insert(char ch) {
		ListNode node = new ListNode(ch);
		ListNode current = root;
		node.next = current;
		root = node;
	}

	public void remove(char ch) {
		ListNode current = root;
		ListNode previous = null;
		while (current != null) {
			if (current.ch == ch) {
				System.out.println("previous : " + previous.ch);
				System.out.println("current.next : " + current.next.ch);
				previous = current.next;
				current.next = null;
				break;
			}
			previous = current;
			current = current.next;
		}
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

	public static void main(String args[]){
		RemoveNode2 rn = new RemoveNode2();
		for(char ch : "edcba".toCharArray())
			rn.insert(ch);
		
		System.out.println(rn.toString(rn.root));
		System.out.println("-----------------------");
		rn.remove('c');
		System.out.println(rn.toString(rn.root));
		System.out.println("-----------------------");
	}
}
