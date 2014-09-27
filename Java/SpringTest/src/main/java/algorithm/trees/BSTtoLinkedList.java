package algorithm.trees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;

import algorithm.linkedlist.Node;

public class BSTtoLinkedList {
	/*
	 * Given a binary search tree, design an algorithm which creates a linked
	 * list of all the nodes at each depth (eg, if you have a tree with depth D,
	 * you¡¯ll have D linked lists).
	 */

	ArrayList<Node> firstNodeList = new ArrayList<Node>();

	// FAIL .... too complicate
	public void convert(TreeNode root) {
		Stack<TreeNode> visitList = new Stack<TreeNode>();
		visitList.push(root);
		int count = 1;
		int oldHeight = 1;
		int newHeight = 1;
		Node first;
		while (!visitList.isEmpty()) {

			TreeNode current = visitList.pop();
			System.out.println(current.value);
			// Insert Node
			Node newNode = new Node(current.value);

			if (firstNodeList.size() < newHeight) {
				Node newfirst = null;
				firstNodeList.add(newfirst);
				first = newfirst;
			} else {
				first = firstNodeList.get(newHeight - 1);
			}

			newHeight = (int) Math.ceil((count + 1) / 2);
			if (oldHeight == newHeight) {
				Node temp = first;
				newNode.next = temp;
			}
			first = newNode;
			firstNodeList.add(newHeight - 1, first);

			if (current.right != null)
				visitList.push(current.right);
			if (current.left != null)
				visitList.push(current.left);
			oldHeight = newHeight;
			count++;
		}
	}

	/*
	 * MISTAKE NOTE
	 * 1. break before add to arraylist. so last depth didn't print out. 
	 */
	public ArrayList<LinkedList<Node>> convertBSTtoLinkedList(TreeNode root) {
		
		ArrayList<ArrayList<TreeNode>> resultList = new ArrayList<ArrayList<TreeNode>>();
		ArrayList<LinkedList<Node>> l_list = new ArrayList<LinkedList<Node>>();		
		
		ArrayList<TreeNode> newList = new ArrayList<TreeNode>();		
		resultList.add(newList);
		int level = 0;
		resultList.get(level).add(root);
		
		while(true) {
			LinkedList<Node> aLinkedList = new LinkedList<Node>();
			ArrayList<TreeNode> nextList = new ArrayList<TreeNode>();
			System.out.println( resultList.get(level).size());
			
			for (int i = 0; i < resultList.get(level).size(); i++) {
				
				aLinkedList.addLast(new Node( resultList.get(level).get(i).value));

				if (resultList.get(level).get(i).left != null)
					nextList.add(resultList.get(level).get(i).left);
				if (resultList.get(level).get(i).right != null)
					nextList.add(resultList.get(level).get(i).right);
			}
			//MUST add before exit 
			l_list.add(aLinkedList);			

			if(nextList.size() == 0)
				break;
			
			resultList.add(nextList);	
			level++;
		}

		return l_list;
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
		BSTtoLinkedList bll = new BSTtoLinkedList();
		root = bll.generateTree();
		ArrayList<LinkedList<Node>> list = bll.convertBSTtoLinkedList(root);
		System.out.println("-----------------------------");
		for(LinkedList<Node> linkedList :list){
			for(Node node : linkedList){
				System.out.print(node.val +",");
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