package algorithm.linkedlist;

public class Merge2SortedLinkedList {

	public ListNode mergeList(ListNode header1, ListNode header2){
		ListNode header = null;
		
		while(header1!= null && header2!= null){
			
			ListNode newNode = null;
			if(header1.val < header2.val){
				newNode = new ListNode(header1.val);
				header1 = header1.next;
			}else{
				newNode = new ListNode(header2.val);
				header2 = header2.next;
			}
			
			if(header == null){
				header = newNode;
			}else{
				newNode.next = header;
				header.prev = newNode;
				header = newNode;				
			}
		}
		
		while(header1!= null ){
			ListNode newNode = new ListNode(header1.val);
			header1 = header1.next;
			newNode.next = header;
			header.prev = newNode;
			header = newNode;
		}
		
		while(header2!= null ){
			ListNode newNode = new ListNode(header2.val);
			header2 = header2.next;
			newNode.next = header;
			header.prev = newNode;
			header = newNode;
		}
		
		return header;
	}
	
	public void printAllNode(ListNode head){
		ListNode currentNode = head;
		while(currentNode != null){
			System.out.print("[" + currentNode.val + "] ");
			currentNode = currentNode.next;
		}
		System.out.print("\n");
	}
	
	public ListNode generateFirstList(){
		ListNode node1 = new ListNode(1);
		ListNode node3 = new ListNode(3);
		ListNode node5 = new ListNode(5);
		ListNode node7 = new ListNode(7);
		ListNode node9 = new ListNode(9);
		node1.next = node3;
		node3.next = node5;
		node5.next = node7;
		node7.next = node9;
		node9.next = null;
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
	
	public static void main(String[] args) {
		Merge2SortedLinkedList mergedList = new Merge2SortedLinkedList();


		ListNode header1 = mergedList.generateFirstList();
		ListNode header2 = mergedList.generateSecondList();

		
		System.out.println("\n----------------LIST 1-------------------");
		mergedList.printAllNode(header1);
		System.out.println("\n----------------LIST 2-----------------");
		mergedList.printAllNode(header2);
		ListNode header = mergedList.mergeList(header1, header2);
		System.out.println("\n----------------AFTER Merge-----------------");
		mergedList.printAllNode(header);
	}

}
