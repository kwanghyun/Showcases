package algorithm.trees;

import algorithm.utils.TreeUtils;

/*
 * In Binary Tree, Inorder successor of a TreeNode is the next TreeNode in Inorder
 * traversal of the Binary Tree. Inorder Successor is NULL for the last TreeNode
 * in Inoorder traversal. In Binary Search Tree, Inorder Successor of an
 * input TreeNode can also be defined as the TreeNode with the smallest key greater
 * than the key of input TreeNode. So, it is sometimes important to find next
 * TreeNode in sorted order.
 * 
 *                       	20
 *                         / \
 *                	 8            22
 *                 / \                  
 *          	4      12                    
 *                    	/ \                      
 *                  10   14                        
 *  In the above diagram, inorder successor of 8 is 10, inorder successor of
 * 10 is 12 and inorder successor of 14 is 20.
 * 
 * Method 1 (Uses Parent Pointer) In this method, we assume that every
 * TreeNode has parent pointer.
 * 
 * The Algorithm is divided into two cases on the basis of right subtree of
 * the input TreeNode being empty or not.
 * 
 * Input: TreeNode, root // TreeNode is the TreeNode whose Inorder successor
 * is needed. output: succ // succ is Inorder successor of TreeNode.
 * 
 * 1) If right subtree of TreeNode is not NULL, then succ lies in right
 * subtree. Do following. Go to right subtree and return the TreeNode with
 * minimum key value in right subtree.
 * 
 * 2) If right sbtree of TreeNode is NULL, then succ is one of the
 * ancestors. Do following. Travel up using the parent pointer until you see
 * a TreeNode which is left child of it’s parent. The parent of such a
 * TreeNode is the succ.
 */
public class InorderSuccessorInBST {

	static TreeNode head;

	/*
	 * Time Complexity: O(h) where h is height of tree.
	 * 
	 * Given a binary search tree and a number, inserts a new TreeNode with the
	 * given number in the correct place in the tree. Returns the new root
	 * pointer which the caller should then use (the standard trick to avoid
	 * using reference parameters).
	 */
	TreeNode insert(TreeNode node, int data) {

		/*
		 * 1. If the tree is empty, return a new, single TreeNode
		 */
		if (node == null) {
			return (new TreeNode(data));
		} else {

			TreeNode temp = null;

			/* 2. Otherwise, recur down the tree */
			if (data <= node.val) {
				temp = insert(node.left, data);
				node.left = temp;
				temp.parent = node;

			} else {
				temp = insert(node.right, data);
				node.right = temp;
				temp.parent = node;
			}

			/* return the (unchanged) TreeNode pointer */
			return node;
		}
	}

	TreeNode inOrderSuccessor(TreeNode root, TreeNode n) {

		// step 1 of the above algorithm
		if (n.right != null) {
			return minValue(n.right);
		}

		// step 2 of the above algorithm
		TreeNode p = n.parent;
		while (p != null && n == p.right) {
			n = p;
			p = p.parent;
		}
		return p;
	}

	/*
	 * Given a non-empty binary search tree, return the minimum data value found
	 * in that tree. Note that the entire tree does not need to be searched.
	 */
	TreeNode minValue(TreeNode TreeNode) {
		TreeNode current = TreeNode;

		/* loop down to find the leftmost leaf */
		while (current.left != null) {
			current = current.left;
		}
		return current;
	}


	// Driver program to test above functions
	public static void main(String[] args) {
		InorderSuccessorInBST tree = new InorderSuccessorInBST();
		TreeNode root = null, temp = null, suc = null, min = null;
		root = tree.insert(root, 20);
		root = tree.insert(root, 8);
		root = tree.insert(root, 22);
		root = tree.insert(root, 4);
		root = tree.insert(root, 12);
		root = tree.insert(root, 10);
		root = tree.insert(root, 14);
		temp = root.left.right.right;
		TreeUtils.drawTree(root);
		suc = tree.inOrderSuccessor(root, temp);
		if (suc != null) {
			System.out.println("Inorder successor of " + temp.val + " is " + suc.val);
		} else {
			System.out.println("Inorder successor does not exist");
		}
	}
}
