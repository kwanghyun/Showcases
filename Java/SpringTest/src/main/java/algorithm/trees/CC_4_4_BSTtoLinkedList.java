package algorithm.trees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

import algorithm.linkedlist.Node;

public class CC_4_4_BSTtoLinkedList {
	/*
	 * Given a binary search tree, design an algorithm which creates a linked
	 * list of all the nodes at each depth (eg, if you have a tree with depth D,
	 * you��ll have D linked lists).
	 */
	/*
	 * MISTAKE NOTE 1. break before add to arraylist. so last depth didn't print
	 * out. 2. In a while loop, didn't change traget node from root, so it keep
	 * add left,right node of root node.
	 */
	ArrayList<Node> firstNodeList = new ArrayList<Node>();

	public ArrayList<LinkedList<Node>> convertBSTtoLinkedList(TreeNode root) {

		ArrayList<ArrayList<TreeNode>> treeNodeVisitList = new ArrayList<ArrayList<TreeNode>>();
		ArrayList<LinkedList<Node>> resultList = new ArrayList<LinkedList<Node>>();

		int level = 0;
		ArrayList<TreeNode> newList = new ArrayList<TreeNode>();
		newList.add(root);
		treeNodeVisitList.add(newList);
		
		while (treeNodeVisitList.get(level).size() != 0) {
			LinkedList<Node> newLinkedList = new LinkedList<Node>();
			ArrayList<TreeNode> nextVisitList = new ArrayList<TreeNode>();
			
			for (TreeNode child : treeNodeVisitList.get(level)) {	
				newLinkedList.addLast(new Node(child.value));
				
				if (child.left != null)
					nextVisitList.add(child.left);
				if (child.right != null)
					nextVisitList.add(child.right);
			}
			
			resultList.add(newLinkedList);

			treeNodeVisitList.add(nextVisitList);
			level++;
		}

		return resultList;
	}

	public ArrayList<LinkedList<Integer>> solution(TreeNode root) {
		ArrayList<LinkedList<Integer>> lists = new ArrayList<LinkedList<Integer>>();
		ArrayList<Stack<TreeNode>> leveList = new ArrayList<Stack<TreeNode>>();
		Stack<TreeNode> stack = new Stack<TreeNode>();

		int level = 0;
		stack.push(root);
		leveList.add(level, stack);

		while (true) {

			LinkedList<Integer> list = new LinkedList<Integer>();
			Stack<TreeNode> nextStack = new Stack<TreeNode>();
			leveList.add(level + 1, nextStack);

			while (!leveList.get(level).isEmpty()) {
				System.out.println(leveList.get(level).size());
				TreeNode node = leveList.get(level).pop();
				list.add(node.value);

				if (node.left != null)
					leveList.get(level + 1).push(node.left);
				if (node.right != null)
					leveList.get(level + 1).push(node.right);
			}

			lists.add(level, list);

			if (leveList.get(level + 1).isEmpty())
				break;

			level++;
		}
		return lists;
	}

	public ArrayList<LinkedList<Integer>> convertRecursively(
			ArrayList<LinkedList<Integer>> result_list, TreeNode root, int level) {
		if (root == null)
			return null;
		
		if(result_list.size() == level){
			result_list.add(new LinkedList<Integer>());
		}
		result_list.get(level).addLast(root.value);
		
		if(root.left != null)
			result_list = convertRecursively(result_list, root.left, level + 1);
		if(root.right != null)
			result_list = convertRecursively(result_list, root.right, level + 1);
		
		return result_list;
	}

	public void printAll() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (algorithm.linkedlist.Node node : firstNodeList) {
			algorithm.linkedlist.Node temp = node;
			while (node != null) {
				list.add(temp.val);
				temp = temp.next;
			}
			System.out.println(Arrays.toString(list.toArray()));
			System.out.println("-----------------------------");
		}
	}

	public static void main(String args[]) {
		TreeNode root;
		CC_4_4_BSTtoLinkedList bll = new CC_4_4_BSTtoLinkedList();
		
		// ArrayList<LinkedList<Node>> list = bll.convertBSTtoLinkedList(root);
//		ArrayList<LinkedList<Integer>> list = bll.solution(root);
		
		ArrayList<LinkedList<Integer>> convertedlist = new ArrayList<LinkedList<Integer>>();		
		convertedlist = bll.convertRecursively(convertedlist, bll.generateTree(), 0);
//		System.out.println("-----------------------------");
//		for (LinkedList<Integer> linkedList : list) {
//			for (Integer i : linkedList) {
//				System.out.print(i + ",");
//			}
//			System.out.print("\n");
//		}
		System.out.println("----------------@Convert() recursive-------------------");
		for (LinkedList<Integer> linkedList : convertedlist) {
			for (Integer i : linkedList) {
				System.out.print(i + ",");
			}
			System.out.print("\n");
		}
		
		System.out.println("----------------@Convert() while-------------------");
		for (LinkedList<Node> linkedList : bll.convertBSTtoLinkedList(bll.generateTree())) {
			for (Node node : linkedList) {
				System.out.print(node.val + ",");
			}
			System.out.print("\n");
		}
	}

	public TreeNode generateTree() {
		// 1
		// / \
		// / \
		// / \
		// 2 3
		// / \ /
		// 4 5 6
		// / / \
		// 7 8 9
		TreeNode one = new TreeNode(1);
		TreeNode two = new TreeNode(2);
		TreeNode three = new TreeNode(3);
		TreeNode four = new TreeNode(4);
		TreeNode five = new TreeNode(5);
		TreeNode six = new TreeNode(6);
		TreeNode seven = new TreeNode(7);
		TreeNode eight = new TreeNode(8);
		TreeNode nine = new TreeNode(9);
		// TreeNode ten = new TreeNode(10);
		one.setLeft(two);
		one.setRight(three);
		two.setLeft(four);
		two.setRight(five);
		three.setLeft(six);
		four.setLeft(seven);
		six.setLeft(eight);
		six.setRight(nine);
		// eight.setLeft(ten);
		return one;
	}

}