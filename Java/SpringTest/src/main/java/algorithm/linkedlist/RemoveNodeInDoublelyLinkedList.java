package algorithm.linkedlist;

public class RemoveNodeInDoublelyLinkedList {

	public static void main(String[] args) {
		RemoveNodeInDoublelyLinkedList obj = new RemoveNodeInDoublelyLinkedList();
		Node header = obj.generateList();
		obj.printAllNode(header);
		System.out.println("----------------------");
		obj.remove(header, 3);
		obj.printAllNode(header);
	}
	
	
	public void printAllNode(Node head){

		Node currentNode = head;
		while(currentNode != null){
			System.out.print("[" + currentNode.val + "] ");
			currentNode = currentNode.next;
		}
		System.out.print("\n");
	}
	
	public Node remove(Node node, int val){
		Node curr = node;
		while(curr != null){
			if(curr.val == val ){
				
				Node next = curr.next;
				Node prev = curr.previous;
				
				prev.next = next;
				next.previous = prev;
				
				break;
			}
			curr = curr.next;
		}
		return node;
	}
	
	
	public Node generateList(){
		Node node1 = new Node(1);
		Node node3 = new Node(3);
		Node node5 = new Node(5);
		Node node7 = new Node(7);
		Node node9 = new Node(9);
		Node node11 = new Node(11);
		node1.next = node3;
		node3.previous = node1;
		node3.next = node5;
		node5.previous = node3;
		node5.next = node7;
		node7.previous = node5;
		node7.next = node9;
		node9.previous = node7;
		node9.next = node11;
		node11.previous = node9;
		node11.next = null;
		return node1;
	}
}
