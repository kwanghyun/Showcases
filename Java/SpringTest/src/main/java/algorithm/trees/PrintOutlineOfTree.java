package algorithm.trees;

import java.util.Stack;


public class PrintOutlineOfTree {

	public String getLeftNodes(TreeNode root, String str){
		
		if(root == null) 
			return null;
		
		str = str + root.value;
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
		
		str = str + root.value;
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
				str += node.value;
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
	}
	
	//       1
	//      / \
	//     /   \
	//    /     \
	//   2      3
	//   / \    /
	//  4 5   6
	//  /     / \
	// 7     8  9
	// Preorder: 1 2 4 7 5 3 6 8 9
	// Inorder: 7 4 2 5 1 8 6 9 3
	// Postorder: 7 4 5 2 8 9 6 3 1
	// Level-order: 1 2 3 4 5 6 7 8 9
	
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
}
