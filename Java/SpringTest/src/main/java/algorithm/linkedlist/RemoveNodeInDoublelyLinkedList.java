package algorithm.linkedlist;

public class RemoveNodeInDoublelyLinkedList {

	public static void main(String[] args) {
		RemoveNodeInDoublelyLinkedList obj = new RemoveNodeInDoublelyLinkedList();
		ListNode header = obj.generateList();
		obj.printAllNode(header);
		System.out.println("----------------------");
		obj.remove(header, 3);
		obj.printAllNode(header);
	}
	
	
	public void printAllNode(ListNode head){

		ListNode currentNode = head;
		while(currentNode != null){
			System.out.print("[" + currentNode.val + "] ");
			currentNode = currentNode.next;
		}
		System.out.print("\n");
	}
	
	public ListNode remove(ListNode node, int val){
		ListNode curr = node;
		while(curr != null){
			if(curr.val == val ){
				
				ListNode next = curr.next;
				ListNode prev = curr.prev;
				
				prev.next = next;
				next.prev = prev;
				
				break;
			}
			curr = curr.next;
		}
		return node;
	}
	
	
	public ListNode generateList(){
		ListNode node1 = new ListNode(1);
		ListNode node3 = new ListNode(3);
		ListNode node5 = new ListNode(5);
		ListNode node7 = new ListNode(7);
		ListNode node9 = new ListNode(9);
		ListNode node11 = new ListNode(11);
		node1.next = node3;
		node3.prev = node1;
		node3.next = node5;
		node5.prev = node3;
		node5.next = node7;
		node7.prev = node5;
		node7.next = node9;
		node9.prev = node7;
		node9.next = node11;
		node11.prev = node9;
		node11.next = null;
		return node1;
	}
}
