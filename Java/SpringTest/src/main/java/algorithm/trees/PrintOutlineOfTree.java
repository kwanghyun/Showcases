package algorithm.trees;

import java.util.LinkedList;
import java.util.Stack;


public class PrintOutlineOfTree {

	public void printOutline(TreeNode root) {
		LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
		queue.add(root);

		while (!queue.isEmpty()) {
			int size = queue.size();

			for (int idx = 0; idx < size; idx++) {
				TreeNode node = queue.remove();
				if (idx == 0)
					System.out.println(node.val);
				else if (idx == size - 1)
					System.out.println(node.val);
				else if (node.right == null && node.left == null)
					System.out.println(node.val);

				if (node.right != null) {
					queue.add(node.right);
				}
				if (node.left != null) {
					queue.add(node.left);
				}
			}
		}
	}
	
	public String getLeftNodes(TreeNode root, String str){
		
		if(root == null) 
			return null;
		
		str = str + root.val;
		if(root.left != null){
			str = getLeftNodes(root.left, str);
		}else if(root.right != null){
			str = getLeftNodes(root.right, str);
		}
		return str;
	}

	public String getRightNodes(TreeNode root, String str){
		
		if(root == null) 
			return null;
		
		str = str + root.val;
		if(root.right != null){
			str = getRightNodes(root.right, str);
		}else if(root.left != null){
			str = getRightNodes(root.left, str);
		}
		return str;
	}

	public String getLeaves(TreeNode root, String str){
		
		Stack<TreeNode> stack = new Stack<TreeNode>();
		stack.push(root);
		
		while(stack.size() > 0){
			TreeNode node = stack.pop();
			if(node.left == null && node.right == null){
				str += node.val;
			}else{
				if(node.left != null){
					stack.push(node.left);
				}
				if(node.right != null){
					stack.push(node.right);
				}
			}
		}
		
		return str;
	}
	
	


	public static void main(String args[]){
		PrintOutlineOfTree poo = new PrintOutlineOfTree();
		String leftStr = "";
		String rightStr = "";
		String leaves = "";
		System.out.println("LEFT :: " + poo.getLeftNodes(poo.generateEntireTree(), leftStr));
		System.out.println("-----------------------------------------------------");
		System.out.println("RIGHT :: " + poo.getRightNodes(poo.generateEntireTree(), rightStr));
		System.out.println("-----------------------------------------------------");
		System.out.println("LEAVES :: " + poo.getLeaves(poo.generateEntireTree(), leaves));
		System.out.println("-----------------------------------------------------");
		poo.printOutline(poo.generateEntireTree());
	}
	
	//           1
	//      /        \
	//     /          \
	//    /            \
	//   2             3
	//   / \          /   \
	//  4   5       6    10
	//  /   / \     / \    / \
	// 7  13 14 8  9 11 12
	
	public TreeNode generateEntireTree(){
		TreeNode one = new TreeNode(1);
		TreeNode two = new TreeNode(2);
		TreeNode three = new TreeNode(3);
		TreeNode four = new TreeNode(4);
		TreeNode five = new TreeNode(5);
		TreeNode six = new TreeNode(6);
		TreeNode seven = new TreeNode(7);
		TreeNode eight = new TreeNode(8);
		TreeNode nine = new TreeNode(9);
		TreeNode ten = new TreeNode(10);
		TreeNode eleven = new TreeNode(11);
		TreeNode twelve = new TreeNode(12);
		TreeNode thirteen = new TreeNode(13);
		TreeNode fourteen = new TreeNode(14);
		one.setLeft(two);
		one.setRight(three);
		two.setLeft(four);
		two.setRight(five);
		five.setLeft(thirteen);
		five.setRight(fourteen);
		three.setLeft(six);
		three.setRight(ten);
		four.setLeft(seven);
		six.setLeft(eight);
		six.setRight(nine);
		ten.setLeft(eleven);
		ten.setRight(twelve);
		return one;
	}
}
