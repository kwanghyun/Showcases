package algorithm.stackNQueue;

import algorithm.linkedlist.*;
/*
 * How would you design a stack which, in addition to push and pop, also has a function min 
 * which returns the minimum element? Push, pop and min should all operate in O(1) time.
 */
public class PopPushMin {

	ListNode first;
	ListNode min; 
	
	public void push(int value){
		ListNode node = new ListNode(value);		
		ListNode current = first;
		node.next = current;
		first = node;
		
		if(min == null ){
			min = node;
		}else{
			
			if(min.val > value)
				min = node;
		}
	}
	
	public ListNode pop(){
		ListNode current = first;
		first = first.next;
		current.next = null;
		return current;
	}
	
	public ListNode min(){
		return min;
	}
	
	public String toString(ListNode root) {
		StringBuilder sb = new StringBuilder();
		ListNode tmp = root;
		while (tmp != null) {
			sb.append(tmp.val).append(", ");
			tmp = tmp.next;
		}
		return sb.toString();
	}

	
	public static void main(String args[]){
		PopPushMin ppm = new PopPushMin();
		ppm.push(3);
		ppm.push(2);
		ppm.push(5);
		ppm.push(1);
		ppm.push(4);
		
		System.out.println(ppm.toString(ppm.first));
		System.out.println("_________________");
		System.out.println("MIN : "+ppm.min().val);
		System.out.println("_________________");
		System.out.println("Pop() : " + ppm.pop().val);
		System.out.println("Pop() : " + ppm.pop().val);
		System.out.println("_________________");
		System.out.println(ppm.toString(ppm.first));
	}
}
