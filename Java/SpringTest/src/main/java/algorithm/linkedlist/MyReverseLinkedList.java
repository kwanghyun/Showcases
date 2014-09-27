package algorithm.linkedlist;

public class MyReverseLinkedList {

	public Node reverseList(Node head){

		if(head.next == null) //One node only 
			return null;
		
		Node second = head.next;
		Node third = second.next;
		
		head.next = null;
		second.next = head;
		
		if(second.next == null){
			//Two Node only, it's already reversed.
			return second; 
		}
		
		Node currentNode = third;
		Node previousNode = second; 
		
		while(currentNode != null){
			Node nextNode = currentNode.next;
			//Only current.next to point previous Node
			currentNode.next = previousNode;
			previousNode = currentNode;
			currentNode = nextNode;
		}
		return previousNode;
	}
	 

	
	public void printAllNode(Node head){
		Node currentNode = head;
		while(currentNode != null){
			System.out.print("[" + currentNode.val + "], ");
			currentNode = currentNode.next;
		}
	}
	
	public static void main(String args []){
		MyReverseLinkedList mrl = new MyReverseLinkedList(); 
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
		mrl.printAllNode(node1);
		System.out.println("\n-----------------------------------------");
		mrl.reverseList(node1);
		mrl.printAllNode(node5);
	}
}
