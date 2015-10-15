package algorithm.linkedlist;

/*Given a linked list and a positive integer n, reverse the order 
 * of nodes in between n and last but n nodes.
 * example: 1->2->3->4->5, n=2. Output is 1->4->3->2->5*/
public class ReversePatial {
	//head, middle, tail, and combine later.
	public Node reverse(Node head, int n) {

		if (head == null)
			return null;
		Node returnNode = head;
		Node temp = head;
		Node current = head;
		Node previous = null;
		Node top = null;
		Node bottom = null;
		Node nextNode = null;
		int size = 0;
		while (temp != null) {
			temp = temp.next;
			size++;
		}
		
		//find n and size -n and swap
		for(int idx = 0; idx < n; idx++){
			
		}

		//
		for (int idx = 0; idx <= size - n; idx++) {
			if(idx <= n){
				nextNode = current.next;
				current.next = previous;
			}			
			previous = current;
			current = nextNode;
		}

		return returnNode;
	}
}
