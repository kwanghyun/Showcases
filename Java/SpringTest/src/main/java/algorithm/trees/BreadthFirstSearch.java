package algorithm.trees;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BreadthFirstSearch {

	public TreeNode findNode(TreeNode root, int  value){
		
		Stack<TreeNode> stack = new Stack<TreeNode>();

		stack.push(root);
		while(!stack.isEmpty()){
			TreeNode node = stack.pop();
			System.out.println(node.val);
			if(node.val == value)
				return node;
			
			if(node.left != null)
				stack.add(node.left);
			if(node.right != null)
				stack.add(node.right);
			
		}
		return null;
	}

	public TreeNode findNode2(TreeNode root, int  value){
		
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		queue.add(root);
		while(!queue.isEmpty()){
			TreeNode node = queue.element();
			queue.remove();
			System.out.println(node.val);
			if(node.val == value)
				return node;
			
			if(node.left != null)
				queue.add(node.left);
			if(node.right != null)
				queue.add(node.right);
			
		}
		return null;
	}

	
	public static void main(String args[]){
		BreadthFirstSearch bfs = new BreadthFirstSearch();
//		System.out.println(bfs.findNode(bfs.generateTree(), 7).value);
		System.out.println(bfs.findNode2(bfs.generateTree(), 10).val);
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
