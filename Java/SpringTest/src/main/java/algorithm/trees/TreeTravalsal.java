package algorithm.trees;

import java.util.*;

//                         1
//                        / \
//                       /   \
//                      /     \
//                    2       3
//                   / \     /
//                 4   5   6
//                /         / \
//               7       8   9
// Preorder:    1 2 4 7 5 3 6 8 9
// Inorder:     7 4 2 5 1 8 6 9 3
// Postorder:   7 4 5 2 8 9 6 3 1
// Level-order: 1 2 3 4 5 6 7 8 9

/*
 * Given a binary tree, return the preorder traversal of its nodes' values.
 * 
 * For example: Given binary tree {1,#,2,3}, 
 * 
	   1
	    \
	     2
	    /
	   3
 * return [1,2,3].
 * 
 * Note: Recursive solution is trivial, could you do it iteratively?
 */

public class TreeTravalsal {

	TreeNode root;

	void preorderTraversal(TreeNode root) {
		if (root == null)
			return;
		root.printValue();
		preorderTraversal(root.getLeft());
		preorderTraversal(root.getRight());
	}

	public ArrayList<Integer> preorderTraversalI(TreeNode root) {
		ArrayList<Integer> returnList = new ArrayList<Integer>();

		if (root == null)
			return returnList;

		Stack<TreeNode> stack = new Stack<TreeNode>();
		stack.push(root);

		while (!stack.empty()) {
			TreeNode n = stack.pop();
			returnList.add(n.val);

			if (n.right != null) {
				stack.push(n.right);
			}
			if (n.left != null) {
				stack.push(n.left);
			}

		}
		return returnList;
	}

	void inorderTraversal(TreeNode root) {
		if (root == null)
			return;
		inorderTraversal(root.getLeft());
		root.printValue();
		inorderTraversal(root.getRight());
	}

	public ArrayList<Integer> inorderTraversalI(TreeNode root) {
		// IMPORTANT: Please reset any member data you declared, as
		// the same Solution instance will be reused for each test case.
		ArrayList<Integer> lst = new ArrayList<Integer>();

		if (root == null)
			return lst;

		Stack<TreeNode> stack = new Stack<TreeNode>();
		// define a pointer to track nodes
		TreeNode p = root;

		while (!stack.empty() || p != null) {

			// if it is not null, push to stack
			// and go down the tree to left
			if (p != null) {
				stack.push(p);
				p = p.left;

				// if no left child
				// pop stack, process the node
				// then let p point to the right
			} else {
				TreeNode t = stack.pop();
				lst.add(t.val);
				p = t.right;
			}
		}

		return lst;
	}

	void postTraversal(TreeNode root) {
		if (root == null)
			return;
		postTraversal(root.getLeft());
		postTraversal(root.getRight());
		root.printValue();
	}

	public List<Integer> postorderTraversal(TreeNode root) {
		List<Integer> res = new ArrayList<Integer>();

		if (root == null) {
			return res;
		}

		Stack<TreeNode> stack = new Stack<TreeNode>();
		stack.push(root);

		while (!stack.isEmpty()) {
			TreeNode temp = stack.peek();
			if (temp.left == null && temp.right == null) {
				TreeNode pop = stack.pop();
				res.add(pop.val);
			} else {
				if (temp.right != null) {
					stack.push(temp.right);
					temp.right = null;
				}

				if (temp.left != null) {
					stack.push(temp.left);
					temp.left = null;
				}
			}
		}

		return res;
	}

	public static void main(String args[]) {
		TreeTravalsal por = new TreeTravalsal();
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
		por.preorderTraversal(one);
		System.out.println();
		por.inorderTraversal(one);
		System.out.println();
		por.postTraversal(one);
		System.out.println();
		System.out.println(Math.pow(2, 32));
	}
}
