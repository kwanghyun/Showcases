package algorithm.trees;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MirrorBinaryTree {

public TreeNode mirror(TreeNode root){
	if(root == null)
		return null;
	
	TreeNode temp = root.left;
	root.left = mirror(root.right);
	root.right = mirror(temp);
	
	return root;
}
	
	public TreeNode mirror2(TreeNode root){
		if(root == null)
			return null;
		
		TreeNode temp = root.left;
		root.left = root.right;
		root.right = temp;
		
		mirror(root.left);
		mirror(root.right);

		return root;
	}
		
		

	public static void main(String args[]){
		MirrorBinaryTree mbt = new MirrorBinaryTree();
		System.out.println("-------------BEFORE-------------------");
		TreeNode header = mbt.generateTree();
		mbt.printNode(header);
		System.out.println("\n-------------After-------------------");
		TreeNode mirrored = mbt.mirror(header);
		System.out.println();
		mbt.printNode(mirrored);
		
		TreeNode header2 = mbt.generateTree();
		System.out.println("\n-------------After-------------------");
		TreeNode mirrored2 = mbt.mirror2(header2);
		System.out.println();
		mbt.printNode(mirrored2);
		
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
		// 		 1
		// 		/ \
		// 	   /   \
		//    /    \
		//   2     3
		//  / \    /
		// 4  5  6
		///   / \
		//7  8 9
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
