package algorithm.linkedlist;

public class Nth_to_last_element {

	Node first;
	int size = 0;

	public void insert(Node newNode) {
		Node current = first;
		first = newNode;
		first.next = current;
		size++;
	}

	public Node delete() {
		if (first == null)
			return null;
		Node current = first;
		first = first.next;
		size--;
		return current;
	}

	 public Node findNthToLastElement(int n){
		 if(n < 1 || n > size)
			 new IllegalArgumentException("n is out of bound");
		 Node temp = first;
		 for(int i = 0; i < size-n; i++){
			 temp = temp.next;
		 }
		 return temp;
	 }

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Node tmp = first;
		while (tmp != null) {
			sb.append(tmp.val).append(", ");
			tmp = tmp.next;
		}
		return sb.toString();
	}

	public static void main(String args[]) {
		Nth_to_last_element nle = new Nth_to_last_element();
		for (int i = 0; i < 5; i++) {
			nle.insert(new Node(i + 1));
		}

		System.out.println(nle.toString());
		System.out.println("--------------------------");

		System.out.println(nle.findNthToLastElement(5).val);
//		for (int i = 0; i < 5; i++) {
//			System.out.println(nle.delete().val);
//		}
	}
}
