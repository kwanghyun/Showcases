package algorithm.linkedlist;

/*Given a linked list and a positive integer n, reverse the order 
 * of nodes in between n and last but n nodes.
 * example: 1->2->3->4->5, n=2. Output is 1->4->3->2->5*/
public class ReversePatial {
	//head, middle, tail, and combine later.
	public ListNode reverse(ListNode head, int n) {

		if (head == null)
			return null;
		ListNode returnNode = head;
		ListNode temp = head;
		ListNode current = head;
		ListNode previous = null;
		ListNode top = null;
		ListNode bottom = null;
		ListNode nextNode = null;
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
