package algorithm.linkedlist;



public class Merge2SortedLinkedListToDoublyLinkedList {
	//	Merge tow sorted LinkedLists to a doubly LinkedList
	//	ASCENDING
	public Node merge(Node head1, Node head2){
		Node resultHeader = null;
		Node newNode = null;
	//		If one of node is null, the value reference of the node 
	//		will throw Nullpoint exception.
		while(head1 != null && head2 != null){
			if(head1.val < head2.val){
				newNode = new Node(head1.val);
				head1 = head1.next;
			}else{
				newNode = new Node(head2.val);
				head2 = head2.next;
			}
			
			if(resultHeader == null){
				resultHeader = newNode;
			}else{
				newNode.next = resultHeader;
				resultHeader.previous = newNode;
				resultHeader = newNode;
			}
		}
		
		while(head1 != null){
			newNode = new Node(head1.val);
			head1 = head1.next;
			newNode.next = resultHeader;
			resultHeader.previous = newNode;
			resultHeader = newNode;
		}
	
		while(head2 != null){
			newNode = new Node(head2.val);
			head2 = head2.next;
			newNode.next = resultHeader;
			resultHeader.previous = newNode;
			resultHeader = newNode;
		}
	
		return resultHeader;
	}

	public Node generateFirstList(){
		Node node1 = new Node(1);
		Node node3 = new Node(3);
		Node node5 = new Node(5);
		Node node7 = new Node(7);
		Node node9 = new Node(9);
		Node node11 = new Node(11);
		node1.next = node3;
		node3.next = node5;
		node5.next = node7;
		node7.next = node9;
		node9.next = node11;
		node11.next = null;
		return node1;
	}
	
	public Node generateSecondList(){
		Node node2 = new Node(2);
		Node node4 = new Node(4);
		Node node6 = new Node(6);
		Node node8 = new Node(8);
		Node node10 = new Node(10);
		node2.next = node4;
		node4.next = node6;
		node6.next = node8;
		node8.next = node10;
		node10.next = null;
		return node2;
	}
	
	public void printAllNode(Node head){

		Node currentNode = head;
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
