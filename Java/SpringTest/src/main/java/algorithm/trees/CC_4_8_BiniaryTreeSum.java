package algorithm.trees;

/*
 * You are given a binary tree in which each node contains a value. 
 * Design an algorithm to print all paths which sum up to that value. 
 * Note that it can be any path in the tree - it does not have to start 
 * at the root.
 * 
 * Mistake Node 
 * 
 */
public class CC_4_8_BiniaryTreeSum {
	
	public int sum(TreeNode root, int sum){
		if(root == null)
			return 0;
		sum = root.value;
		return  sum + sum(root.left, sum) + sum(root.right, sum);
	}
	
	public int treeSum(TreeNode root, int sum){
		if(root == null) 
			return 0;		
		sum =  root.value + sum(root.left, sum) + sum(root.right, sum);
		return sum;
	}
	
	public int recursion(TreeNode root, int sum){
		if(root == null) 
			return 0;		

		sum = root.value + recursion(root.left, sum) +  recursion(root.right, sum);
		return sum;
	}
	
	public static void main(String args[]){
		CC_4_8_BiniaryTreeSum obj = new CC_4_8_BiniaryTreeSum();
//		System.out.println(obj.sum(obj.generateEntireTree(), 0));
//		System.out.println(1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9);
//		System.out.println(obj.treeSum(obj.generateEntireTree(), 0));
		System.out.println(obj.recursion(obj.generateEntireTree(), 0));
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