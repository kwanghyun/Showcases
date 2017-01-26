package algorithm.trees;


public class CloneBinaryTree {

	public TreeNode clone(TreeNode root){
		
		if(root == null)
			return null;
		
		TreeNode node = new TreeNode(root.val);
		node.left = clone(root.left);
		node.right = clone(root.right);
		
		return node;
	}
	
	public static void main(String args[]){
		CloneBinaryTree cbt = new CloneBinaryTree();
		cbt.printPreOrder(cbt.generateTree());
		TreeNode clonedRoot = cbt.clone(cbt.generateTree());
		System.out.println("");
		cbt.printPreOrder(clonedRoot);
		
	}
	
	public void printPreOrder(TreeNode root){
		if(root == null)
			return;
		System.out.print("[" + root.val + "] ");
		printPreOrder(root.left);
		printPreOrder(root.right);
		
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
