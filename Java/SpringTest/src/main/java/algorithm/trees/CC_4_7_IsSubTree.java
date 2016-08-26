package algorithm.trees;

import java.util.Arrays;

import algorithm.utils.TreeUtils;

/*
 * You have two very large binary trees: T1, with millions of nodes, and T2, 
 * with hundreds of nodes. Create an algorithm to decide if T2 is a subtree of T1.
 * 
 * Mistake Note :
 * 
 * 1. When find a common node in left side, we don't need to go right side so 
 * 	"Or" boolean condition doesn't executed second condition when first one is true.
 * 
 */
public class CC_4_7_IsSubTree {

	public boolean isSubTree(TreeNode root1, TreeNode root2) {

		TreeNode match = findNode(root1, root2);
		if (match == null)
			return false;

		return checkTrees(match, root2);
	}

	public TreeNode findNode(TreeNode root1, TreeNode root2) {
		if (root1 == null)
			return null;

		if (root1.value == root2.value) {
			return root1;
		}

		System.out.println(root1.value);

		if (root1.left != null)
			return findNode(root1.left, root2);
		else
			return findNode(root1.right, root2);

	}

	public boolean checkTrees(TreeNode root1, TreeNode root2) {
		if (root2 == null)
			return true;

		if (root1 == null)
			return false;

		if (root1.value != root2.value)
			return false;

		return checkTrees(root1.left, root2.left) && checkTrees(root1.right, root2.right);
	}

	public static void main(String args[]) {
		CC_4_7_IsSubTree ist = new CC_4_7_IsSubTree();

		int[] big_preorder = { 5, 3, 1, 4, 7, 6, 8 };
		TreeNode bigTree = TreeUtils.buildBstFromPreorder(big_preorder);
		TreeUtils.drawTree(bigTree);
		int[] small_preorder = { 3, 1, 4 };
		TreeNode smallTree = TreeUtils.buildBstFromPreorder(small_preorder);
		TreeUtils.drawTree(smallTree);
		System.out.println("is Subtree? -> " + ist.isSubTree(bigTree, smallTree));

		System.out.println("");

		int[] big_preorder1 = { 5, 3, 1, 4, 7, 6, 8 };
		TreeNode bigTree1 = TreeUtils.buildBstFromPreorder(big_preorder1);
		int[] small_preorder1 = { 4, 1, 7 };
		TreeNode smallTree1 = TreeUtils.buildBstFromPreorder(small_preorder1);
		TreeUtils.drawTree(smallTree1);
		System.out.println("is Subtree? -> " + ist.isSubTree(bigTree1, smallTree1));

	}

	public TreeNode find(TreeNode node, int value) {
		TreeNode foundNode = null;
		if (node == null)
			return null;

		if (node.value == value)
			return node;

		foundNode = find(node.left, value);
		if (foundNode == null) {
			foundNode = find(node.right, value);
		}

		return foundNode;
	}

	public TreeNode generateEntireTree() {
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

	public TreeNode generatePartTree() {
		TreeNode six = new TreeNode(6);
		TreeNode eight = new TreeNode(8);
		TreeNode nine = new TreeNode(9);
		six.setLeft(eight);
		six.setRight(nine);
		return six;
	}

}
