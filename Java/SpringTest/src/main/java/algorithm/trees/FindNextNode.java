package algorithm.trees;

import java.util.Stack;

/*
 * Write an algorithm to find the ¡®next¡¯ node (i.e., in-order successor) of 
 * a given node in a binary search tree where each node has a link to its parent.
 */

/*
 * Mistake Node :
 * 1. Put outside Recursive function for global variable.
 * 2. do something after root == null check  
 */
public class FindNextNode {
	TreeNode next;
	Stack<TreeNode> visitedList = new Stack<TreeNode>();
	boolean found = false;
	int count = 0; 
	int stopCount = Integer.MAX_VALUE;
	public TreeNode findNext(TreeNode root, int value){
		
		if(root == null)
			return null;

		visitedList.push(root);
		count++;

		if(count == stopCount)
			next = root;
	
		findNext(root.left, value);
		
		if(root.value == value){
			found = true;
			stopCount = count + 1;
			System.out.println("stopCount : " + stopCount);
		}
		
		findNext(root.right, value);
		
		return null;
	}
	
	public static void main(String args[]){
		FindNextNode fnn = new FindNextNode();
		fnn.findNext(fnn.generateTree(), 9);
		System.out.println(fnn.next.value);
		
	}
	
	public TreeNode generateTree(){
		
		//      1
		//     / \
		//    /   \
		//   /     \
		//  2       3
		// / \     /
		//4   5   6
		///        / \
		//7       8   9
		//Preorder:    1 2 4 7 5 3 6 8 9
		//Inorder:     7 4 2 5 1 8 6 9 3
		//Postorder:   7 4 5 2 8 9 6 3 1
		//Level-order: 1 2 3 4 5 6 7 8 9
		
		TreeNode one = new TreeNode(1);
		TreeNode two = new TreeNode(2);
		TreeNode three = new TreeNode(3);
		TreeNode four = new TreeNode(4);
		TreeNode five = new TreeNode(5);
		TreeNode six = new TreeNode(6);
		TreeNode seven = new TreeNode(7);
		TreeNode eight = new TreeNode(8);
		TreeNode nine = new TreeNode(9);
		one.setLeft(two);
		one.setRight(three);
		two.setLeft(four);
		two.setRight(five);
		three.setLeft(six);
		four.setLeft(seven);
		six.setLeft(eight);
		six.setRight(nine);
		return one;
	}
}
