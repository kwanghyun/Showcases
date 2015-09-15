package algorithm.trees;

import java.util.Stack;

/*
 * Write an algorithm to find the ��next�� node (i.e., in-order successor) of 
 * a given node in a binary search tree where each node has a link to its parent.
 */

/*
 * Mistake Node :
 * 1. Put outside Recursive function for global variable.
 * 2. do something after root == null check
 * 3. Return statement during the recursive is not final return, only return back from 
 *  current recursive. (recurPersist, recurValotile)
 */
public class CC_4_5_FindNextNode {

	boolean stopSignal = false;

	public TreeNode findNext(TreeNode root, int value) {
		TreeNode foundNode = null;
		if (root == null)
			return null;


		if (root.value == value) {
			stopSignal = true;
			return null;
		}
		if (stopSignal == true) {
			return root;
		}

		foundNode = findNext(root.left, value);

		if (foundNode == null)
			foundNode = findNext(root.right, value);

		return foundNode;
	}


	public static void main(String args[]) {
		CC_4_5_FindNextNode fnn = new CC_4_5_FindNextNode();
		System.out.println(fnn.findNext(fnn.generateTree(), 5).value);
	}

	public TreeNode generateTree() {

		// 		  1
		// 	     / \
		//     /    \
		//    /      \
		//   2       3
		//  / \      /
		// 4  5    6
		// /  		/ \
		// 7 	   8   9
		// Preorder: 1 2 4 7 5 3 6 8 9
		// Inorder: 7 4 2 5 1 8 6 9 3
		// Postorder: 7 4 5 2 8 9 6 3 1
		// Level-order: 1 2 3 4 5 6 7 8 9

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
