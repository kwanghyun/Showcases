package algorithm.linkedlist;

import algorithm.trees.TreeNode;

public class BSTtoDoubleLinkedList {

	Node head;
	Node tail;
	
	public void convert(TreeNode root){
		if(root == null)
			return;
		
		convert(root.left);
		
		Node newNode = new Node(root.value);
		
		if(tail == null){
			tail = newNode;
			head = newNode;
		}else{
			tail.next = newNode;
			newNode.previous = tail;
			tail = newNode;
		}
		convert(root.right);
	}
	
	
	public static void main(String args[]){
		BSTtoDoubleLinkedList bdl = new BSTtoDoubleLinkedList();
		bdl.convert(bdl.generateBST());
		
		Node temp = bdl.head;
		while (temp != null){
			System.out.println(temp.val);
			temp = temp.next;
		}
		
	}
	
	public TreeNode generateBST() {
		//              5
		//             / \
		//            /   \
		//           /      \
		//         2        8
		//        / \      / \
		//      1   3    6   9
		TreeNode five = new TreeNode(5);
		TreeNode two = new TreeNode(2);
		TreeNode eight = new TreeNode(8);
		TreeNode one = new TreeNode(1);
		TreeNode three = new TreeNode(3);
		TreeNode six = new TreeNode(6);
		TreeNode nine = new TreeNode(9);

		five.setLeft(two);
		five.setRight(eight);
		two.setLeft(one);
		two.setRight(three);
		eight.setLeft(six);
		eight.setRight(nine);
		// eight.setLeft(ten);
		return five;
	}

}
