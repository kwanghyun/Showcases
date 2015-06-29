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
	 * you¡¯ll have D linked lists).
	 */
	/*
	 * MISTAKE NOTE 1. break before add to arraylist. so last depth didn't print
	 * out. 2. In a while loop, didn't change traget node from root, so it keep
	 * add left,right node of root node.
	 */
	ArrayList<Node> firstNodeList = new ArrayList<Node>();

	public ArrayList<LinkedList<Node>> convertBSTtoLinkedList(TreeNode root) {

		ArrayList<ArrayList<TreeNode>> resultList = new ArrayList<ArrayList<TreeNode>>();
		ArrayList<LinkedList<Node>> l_list = new ArrayList<LinkedList<Node>>();

		ArrayList<TreeNode> newList = new ArrayList<TreeNode>();
		resultList.add(newList);
		int level = 0;
		resultList.get(level).add(root);

		while (true) {
			LinkedList<Node> aLinkedList = new LinkedList<Node>();
			ArrayList<TreeNode> nextList = new ArrayList<TreeNode>();
			System.out.println(resultList.get(level).size());

			for (int i = 0; i < resultList.get(level).size(); i++) {

				aLinkedList
						.addLast(new Node(resultList.get(level).get(i).value));

				if (resultList.get(level).get(i).left != null)
					nextList.add(resultList.get(level).get(i).left);
				if (resultList.get(level).get(i).right != null)
					nextList.add(resultList.get(level).get(i).right);
			}
			// MUST add before exit
			l_list.add(aLinkedList);

			if (nextList.size() == 0)
				break;

			resultList.add(nextList);
			level++;
		}

		return l_list;
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

	public ArrayList<LinkedList<Integer>> convert(
			ArrayList<LinkedList<Integer>> result_list, TreeNode root, int level) {
		if (root == null)
			return null;
		
		if(result_list.size() == level){
			result_list.add(new LinkedList<Integer>());
		}
		result_list.get(level).addLast(root.value);
		
		if(root.left != null)
			result_list = convert(result_list, root.left, level + 1);
		if(root.right != null)
			result_list = convert(result_list, root.right, level + 1);
		
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
		root = bll.generateTree();
		// ArrayList<LinkedList<Node>> list = bll.convertBSTtoLinkedList(root);
		ArrayList<LinkedList<Integer>> list = bll.solution(root);
		
		ArrayList<LinkedList<Integer>> convertedlist = new ArrayList<LinkedList<Integer>>();		
		convertedlist = bll.convert(convertedlist, root, 0);
		System.out.println("-----------------------------");
		for (LinkedList<Integer> linkedList : list) {
			for (Integer i : linkedList) {
				System.out.print(i + ",");
			}
			System.out.print("\n");
		}
		System.out.println("----------------@Convert()-------------------");
		for (LinkedList<Integer> linkedList : convertedlist) {
			for (Integer i : linkedList) {
				System.out.print(i + ",");
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