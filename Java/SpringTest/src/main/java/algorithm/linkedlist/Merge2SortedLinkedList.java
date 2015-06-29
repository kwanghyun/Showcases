package algorithm.linkedlist;

public class Merge2SortedLinkedList {

	public Node mergeList(Node header1, Node header2){
		Node header = null;
		
		while(header1!= null && header2!= null){
			
			Node newNode = null;
			if(header1.val < header2.val){
				newNode = new Node(header1.val);
				header1 = header1.next;
			}else{
				newNode = new Node(header2.val);
				header2 = header2.next;
			}
			
			if(header == null){
				header = newNode;
			}else{
				newNode.next = header;
				header.previous = newNode;
				header = newNode;				
			}
		}
		
		while(header1!= null ){
			Node newNode = new Node(header1.val);
			header1 = header1.next;
			newNode.next = header;
			header.previous = newNode;
			header = newNode;
		}
		
		while(header2!= null ){
			Node newNode = new Node(header2.val);
			header2 = header2.next;
			newNode.next = header;
			header.previous = newNode;
			header = newNode;
		}
		
		return header;
	}
	
	public void printAllNode(Node head){
		Node currentNode = head;
		while(currentNode != null){
			System.out.print("[" + currentNode.val + "] ");
			currentNode = currentNode.next;
		}
		System.out.print("\n");
	}
	
	public Node generateFirstList(){
		Node node1 = new Node(1);
		Node node3 = new Node(3);
		Node node5 = new Node(5);
		Node node7 = new Node(7);
		Node node9 = new Node(9);
		node1.next = node3;
		node3.next = node5;
		node5.next = node7;
		node7.next = node9;
		node9.next = null;
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
	
	public static void main(String[] args) {
		Merge2SortedLinkedList mergedList = new Merge2SortedLinkedList();


		Node header1 = mergedList.generateFirstList();
		Node header2 = mergedList.generateSecondList();

		
		System.out.println("\n----------------LIST 1-------------------");
		mergedList.printAllNode(header1);
		System.out.println("\n----------------LIST 2-----------------");
		mergedList.printAllNode(header2);
		Node header = mergedList.mergeList(header1, header2);
		System.out.println("\n----------------AFTER Merge-----------------");
		mergedList.printAllNode(header);
	}

}
