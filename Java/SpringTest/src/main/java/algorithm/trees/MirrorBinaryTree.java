package algorithm.trees;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MirrorBinaryTree {

	public TreeNode clone(TreeNode root){
		
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		queue.add(root);
		
		TreeNode newHead;
		
		while(!queue.isEmpty()){
			TreeNode node = queue.element();
			queue.remove();
			System.out.println(node.value);
			TreeNode newNode = new TreeNode(node.value);
			
			if(node.left != null){
				queue.add(node.left);
				newNode.left = new TreeNode(node.left.value);
			}
			if(node.right != null){
				queue.add(node.right);
				newNode.right = new TreeNode(node.right.value);
			}
		}
		return null;
	}
	
	public String serialize(TreeNode root){
		
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		queue.add(root);
		
		String str = "";
		
		while(!queue.isEmpty()){
			TreeNode node = queue.element();
			queue.remove();
			str += node.value;
			TreeNode newNode = new TreeNode(node.value);
			
			if(node.left != null){
				queue.add(node.left);
			}else{
				str += "#";
			}
			if(node.right != null){
				queue.add(node.right);
			}else{
				str += "#";
			}
		}
		return str;
	}

	public TreeNode deserialize(String str){
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		TreeNode newHead = new TreeNode(Integer.parseInt(str.charAt(0)+""));
		queue.add(newHead);
		int count = 1;
		
		while(count != str.length()){
			TreeNode node1 = queue.element();
			queue.remove();
			
			char ch1 = str.charAt(count++);
			char ch2 = str.charAt(count++);
			
			if(ch1 != '#'){
				node1.left = new TreeNode(Integer.parseInt(ch1+""));
				queue.add(node1.left);
			}else
				node1.left = null;
				
			if(ch2 != '#'){
				node1.right = new TreeNode(Integer.parseInt(ch2+""));
				queue.add(node1.right);
			}else
				node1.right = null;
				
		}
		return newHead;
	}

	public static void main(String args[]){
		MirrorBinaryTree mbt = new MirrorBinaryTree();
		System.out.println(mbt.serialize(mbt.generateTree()));
		System.out.println("-------------DES-------------------");
		mbt.printNode(mbt.deserialize(mbt.serialize(mbt.generateTree())));
	}
	
	public void printNode(TreeNode root){
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		queue.add(root);
				
		while(!queue.isEmpty()){
			TreeNode node = queue.element();
			queue.remove();
			System.out.print(node.value);
			
			if(node.left != null){
				queue.add(node.left);
			}
			if(node.right != null){
				queue.add(node.right);
			}
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
