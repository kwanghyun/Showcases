package algorithm.trees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

import algorithm.linkedlist.ListNode;

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

	public ArrayList<LinkedList<ListNode>> solution(TreeNode root) {
		ArrayList<LinkedList<ListNode>> result = new ArrayList<LinkedList<ListNode>>();
		LinkedList<TreeNode> q = new LinkedList<TreeNode>();
		q.add(root);
		
		while(!q.isEmpty()){
			int size = q.size();
			
			LinkedList<ListNode> newHeader = new LinkedList<ListNode>();
			
			for(int i = 0; i < size; i++){
				TreeNode curr = q.remove();
				newHeader.addLast(new ListNode(curr.val));
				
				if(curr.left != null) 
					q.add(curr.left);
				if(curr.right != null)
					q.add(curr.right);
			}
			
			result.add(newHeader);
		}
		return result;
	}

	public ArrayList<LinkedList<Integer>> convertRecursively(
			ArrayList<LinkedList<Integer>> result_list, TreeNode root, int level) {
		if (root == null)
			return null;
		
		if(result_list.size() == level){
			result_list.add(new LinkedList<Integer>());
		}
		result_list.get(level).addLast(root.val);
		
		if(root.left != null)
			result_list = convertRecursively(result_list, root.left, level + 1);
		if(root.right != null)
			result_list = convertRecursively(result_list, root.right, level + 1);
		
		return result_list;
	}



	public static void main(String args[]) {
		TreeNode root;
		CC_4_4_BSTtoLinkedList bll = new CC_4_4_BSTtoLinkedList();
			
		ArrayList<LinkedList<Integer>> convertedlist = new ArrayList<LinkedList<Integer>>();		
		convertedlist = bll.convertRecursively(convertedlist, bll.generateTree(), 0);
		System.out.println("-----------------------------");

		System.out.println("----------------@Convert() recursive-------------------");
		for (LinkedList<Integer> linkedList : convertedlist) {
			for (Integer i : linkedList) {
				System.out.print(i + ",");
			}
			System.out.print("\n");
		}

		System.out.println("----------------@Convert() while with queue -------------------");
		for (LinkedList<ListNode> linkedList : bll.solution(bll.generateTree())) {
			for (ListNode node : linkedList) {
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