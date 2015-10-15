package algorithm.linkedlist;

/*
 Given a linked list, swap every two adjacent nodes and return its head.
 For example, given 1->2->3->4, you should return the list as 2->1->4->3.
 Your algorithm should use only constant space. You may not modify the values in
 the list, only nodes itself can be changed.

 Use two template variable to track the previous and next node of each pair.
 */
public class SwapNodeInPairs {
	public Node swapPairs(Node head) {
		
		if (head == null || head.next == null)
			return head;
		
		Node newNode = new Node(0);
		newNode.next = head;		
		Node p = newNode;
		
		while (p.next != null && p.next.next != null) {
			// use t1 to track first node
			Node t1 = p;
			p = p.next;
			t1.next = p.next;
			
			// use t2 to track next node of the pair
			Node t2 = p.next.next;
			p.next.next = p;
			p.next = t2;
		}
		
		return newNode.next;
	}
	
	public static void main(String[] args) {
		SwapNodeInPairs obj = new SwapNodeInPairs();
		Node head = obj.generateList();
		obj.printAllNode(head);
		System.out.println("\n----------------------------");
		obj.printAllNode(obj.swapPairs(head));
		
	}
	
	public void printAllNode(Node head) {
		Node currentNode = head;
		while (currentNode != null) {
			System.out.print("[" + currentNode.val + "] ");
			currentNode = currentNode.next;
		}
	}

	public Node generateList() {
		Node node1 = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		Node node4 = new Node(4);
		Node node5 = new Node(5);
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		node4.next = node5;
		node5.next = null;
		return node1;
	}
}
