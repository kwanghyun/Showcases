package algorithm.linkedlist;

import java.util.Queue;
import java.util.LinkedList;

public class Sum {

	// You have two numbers represented by a linked list, where each node
	// contains a single digit. The digits are
	// stored in reverse order, such that the 1¡¯s digit is at the head of the
	// list. Write a function that adds the
	// two numbers and returns the sum as a linked list.
	// EXAMPLE
	// Input: (3 -> 1 -> 5) + (5 -> 9 -> 2)
	// Output: 8 -> 0 -> 8

	Node first;
	Node last;

	public void insert(int i) {
		Node node = new Node(i);
		if (first == null) {
			first = node;
			last = node;
		} else {
			Node current = last;
			current.next = node;
			last = node;
		}
	}

	public Sum sum(Node head1, Node head2) {
		Sum newList = new Sum();
		int sum = 0;
		int surplusPre = 0;
		int surplusCur = 0;
		while (head1 != null || head2 != null) {
			
			sum = ((head1 == null) ? 0 : head1.val)
					+ ((head2 == null) ? 0 : head2.val);

			if (sum >= 10) {
				surplusCur = (int) sum / 10;
			}
			sum = sum % 10;

			if (surplusPre > 0) {
				sum = sum + surplusPre;
				surplusPre = 0;
				surplusCur = 0;
			}

			surplusPre = surplusCur;
			newList.insert(sum);

			if(head1 != null) head1 = head1.next;
			if(head2 != null) head2 = head2.next;
		}
		return newList;
	}
	
	//TODO make as interger and add

	public String toString(Node root) {
		StringBuilder sb = new StringBuilder();
		Node tmp = root;
		while (tmp != null) {
			sb.append(tmp.val).append(", ");
			tmp = tmp.next;
		}
		return sb.toString();
	}

	public static void main(String args[]) {
		
		Queue<String> list = new LinkedList<String>();
		
		Sum obj1 = new Sum();
		obj1.insert(3);
		obj1.insert(1);
		obj1.insert(5);
		obj1.insert(5);
		System.out.println(obj1.toString(obj1.first));
		System.out.println("-----------------------");
		Sum obj2 = new Sum();
		obj2.insert(5);
		obj2.insert(9);
		obj2.insert(2);
		obj2.insert(9);
		obj2.insert(9);
		System.out.println(obj2.toString(obj2.first));
		System.out.println("-----------------------");
		Sum obj3 = new Sum();
		Sum obj4 = obj3.sum(obj1.first, obj2.first);
		System.out.println(obj4.toString(obj4.first));
		// System.out.println("-----------------------");

	}

}
