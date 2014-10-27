package algorithm.linkedlist;

/*
 * 1) Given a single linked list of integers determine if it is palindrome. like 1 2 3 4 3 2 1. 
 * Need to support 1 2 3 3 4 3 3 1 as well. No extra allocation or space should be consumed.
 * (can use some temp variable).
 */
public class CheckPalindrome {

	Node root;
	
	public boolean checkPalindrome(Node head){
		Node temp = head;
		Node first = head;
		Node second = head;
		
		int count = 0;
		int middle = 0;
		
		while(temp != null){
			count ++;
			temp = temp.next;
		}
		
		middle = count / 2;
		
		for(int i = 0; i < middle + 1; i++){
			second = second.next;
		}
		
		while(second != null ){
			char val1 = second.ch;
			char val2 = 0;
			Node temp1 = first;
			
			for(int i = 0; i < count - (middle +1); i++){
				val2 = temp1.ch;
				temp1 = temp1.next;
			}
			
			if(val1 != val2)
				return false;		
			
			temp1 = first;
			middle++;
			second = second.next;
		}
		
		return true;
	}
	
	public void insert(char ch) {
		Node node = new Node(ch);
		Node current = root;
		node.next = current;
		root = node;
	}
	

	public static void main(String args[]){
		CheckPalindrome cp = new CheckPalindrome();
		for(char ch : "12345543211".toCharArray())
			cp.insert(ch);
	
		System.out.println(cp.checkPalindrome(cp.root));
	}
}
