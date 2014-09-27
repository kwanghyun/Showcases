package algorithm.linkedlist;

public class RemoveNode {

	Node head, tail;
	int count;

	// 3 => 4 => 5
	// previous.next = temp.next
	// 3 => 5
	public boolean delete(int value) {
		Node temp = tail;
		Node previous = tail;
		while (temp != null) {
			if (temp.val == value) {
				if (temp == previous) {
					tail = tail.next;
					temp = null;
					count--;
					return true;
				}
				previous.next = temp.next;
				temp = null;
				count--;
				return true;
			}
			previous = temp;
			temp = temp.next;
		}
		return false;
	}

	//  2 => 4 => 5 =>
	//  2 => previous.next = node
	//  2 => node.next = previous.next
	//  2 => 3 => 4 => 5 =>
	public boolean insertAfter(Node node, int index) {
		Node temp = tail;
		Node previous = tail;
		
		if (index > count || index < 0)
			return false;
		
		// 1 => 2
		// node.next = tail;
		// 0 => 1 => 2
		if(index == 0 ){
			node.next = tail;
			count++;
		}
		
		for(int i =0; i<index; i++){
			previous = temp;
			temp = temp.next;
		}
		
		previous.next = node;
		node.next = temp;
		count++;
		
		return true;
	}

	public boolean add(Node node) {
		if (head == null) {
			head = node;
			tail = node;
			count++;
			return true;
		}
		head.next = node;
		head = node;
		count++;
		return true;
	}

	public void printAll() {
		Node temp = tail;
		for (int i = 0; i < count; i++) {
			System.out.print("[" + temp.val + "], ");
			temp = temp.next;
		}
		System.out.println("");
	}

	public static void main(String args[]) {
		RemoveNode rn = new RemoveNode();
		for (int i = 0; i < 10; i++) {
			Node node = new Node(i);
			rn.add(node);
		}
		rn.printAll();
		System.out.println(rn.delete(3));
		rn.printAll();
		System.out.println(rn.insertAfter(new Node(3), 3));
		rn.printAll();
	}
}
