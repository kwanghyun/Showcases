package algorithm.trees;

/*
 * You are given a binary tree in which each node contains a value. 
 * Design an algorithm to print all paths which sum up to that value. 
 * Note that it can be any path in the tree - it does not have to start 
 * at the root.
 */
public class BiniaryTreeSum {
	
	public int sum(TreeNode root, int sum){
		if(root == null)
			return 0;
		sum = root.value;
		return  sum + sum(root.left, sum) + sum(root.right, sum);
	}
	
	public static void main(String args[]){
		BiniaryTreeSum obj = new BiniaryTreeSum();
		System.out.println(obj.sum(obj.generateEntireTree(), 0));
		System.out.println(1+2+3+4+5+6+7+8+9);
	}

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
