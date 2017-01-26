package algorithm.linkedlist;



public class Merge2SortedLinkedListToDoublyLinkedList {
	//	Merge tow sorted LinkedLists to a doubly LinkedList
	//	ASCENDING
	public ListNode merge(ListNode head1, ListNode head2){
		ListNode resultHeader = null;
		ListNode newNode = null;
	//		If one of node is null, the value reference of the node 
	//		will throw Nullpoint exception.
		while(head1 != null && head2 != null){
			if(head1.val < head2.val){
				newNode = new ListNode(head1.val);
				head1 = head1.next;
			}else{
				newNode = new ListNode(head2.val);
				head2 = head2.next;
			}
			
			if(resultHeader == null){
				resultHeader = newNode;
			}else{
				newNode.next = resultHeader;
				resultHeader.prev = newNode;
				resultHeader = newNode;
			}
		}
		
		while(head1 != null){
			newNode = new ListNode(head1.val);
			head1 = head1.next;
			newNode.next = resultHeader;
			resultHeader.prev = newNode;
			resultHeader = newNode;
		}
	
		while(head2 != null){
			newNode = new ListNode(head2.val);
			head2 = head2.next;
			newNode.next = resultHeader;
			resultHeader.prev = newNode;
			resultHeader = newNode;
		}
	
		return resultHeader;
	}

	public ListNode generateFirstList(){
		ListNode node1 = new ListNode(1);
		ListNode node3 = new ListNode(3);
		ListNode node5 = new ListNode(5);
		ListNode node7 = new ListNode(7);
		ListNode node9 = new ListNode(9);
		ListNode node11 = new ListNode(11);
		node1.next = node3;
		node3.next = node5;
		node5.next = node7;
		node7.next = node9;
		node9.next = node11;
		node11.next = null;
		return node1;
	}
	
	public ListNode generateSecondList(){
		ListNode node2 = new ListNode(2);
		ListNode node4 = new ListNode(4);
		ListNode node6 = new ListNode(6);
		ListNode node8 = new ListNode(8);
		ListNode node10 = new ListNode(10);
		node2.next = node4;
		node4.next = node6;
		node6.next = node8;
		node8.next = node10;
		node10.next = null;
		return node2;
	}
	
	public void printAllNode(ListNode head){

		ListNode currentNode = head;
		while(currentNode != null){
			System.out.print("[" + currentNode.val + "] ");
			currentNode = currentNode.next;
		}
		System.out.print("\n");
	}
	
	public static void main(String[] args) {
		Merge2SortedLinkedListToDoublyLinkedList m =
				new Merge2SortedLinkedListToDoublyLinkedList();
		
		m.printAllNode(m.merge(m.generateFirstList(), m.generateSecondList()));
	}
}
