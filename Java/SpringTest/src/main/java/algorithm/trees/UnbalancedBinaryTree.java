package algorithm.trees;

public class UnbalancedBinaryTree {
	public TreeNode rotateRight( TreeNode oldRoot ){
	    TreeNode newRoot = oldRoot.getLeft();
	    oldRoot.setLeft( newRoot.getRight() );
	    newRoot.setRight( oldRoot );
	    return newRoot;
	}
	
	public static void main(String args []){
		UnbalancedBinaryTree ubt = new UnbalancedBinaryTree();
		TreeNode one = new TreeNode(1);
		TreeNode two = new TreeNode(2);
		TreeNode three = new TreeNode(3);
		TreeNode four = new TreeNode(4);
		TreeNode five = new TreeNode(5);
		TreeNode six = new TreeNode(6);
		TreeNode seven = new TreeNode(7);
//		TreeNode eight = new TreeNode(8);
//		TreeNode nine = new TreeNode(9);
		six.setLeft(four);
		six.setRight(seven);
		four.setLeft(two);
		four.setRight(five);
		two.setRight(one);
		two.setLeft(three);		
		
		ubt.rotateRight(six);

	}
}
