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

	public TreeNode findNext(TreeNode root, int value, boolean stopSignal) {
		TreeNode foundNode = null;
		if (root == null)
			return null;
		
		//Stop signal must be defined first
		if (stopSignal == true) {
			return root;
		}else if (root.value == value) {
			stopSignal = true;
		}

		foundNode = findNext(root.left, value, stopSignal);

		if (foundNode == null)
			foundNode = findNext(root.right, value, stopSignal);

		return foundNode;
	}
}
