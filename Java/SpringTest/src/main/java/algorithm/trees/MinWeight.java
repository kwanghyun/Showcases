package algorithm.trees;

/*
 * In a Binary Tree, weight of each node is described by the value of the node multiplied 
 * by the level (i.e. for root node value is 1* value in root node), And the weight of tree 
 * is sum of all the node weights. 
 * Find the minimum tree weight out of all the binary trees possible from a given set of numbers.
 */
public class MinWeight {

public int findMinWeight(TreeNode root1, TreeNode root2){
	return Math.min(findWeight(root1, 1), findWeight(root2, 1));
}

	public int findWeight(TreeNode root, int level) {
		if (root == null)
			return 0;
		return level * root.val 
				+ level * findWeight(root.left, level + 1) 
				+ level * findWeight(root.right, level + 1);
	}
	
	public static void main(String args[]){
		MinWeight mw = new MinWeight();
		System.out.println("#Min :: " + mw.findMinWeight(mw.generateEntireTree(), mw.generatePartTree()));
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

	public TreeNode generatePartTree(){
		TreeNode one = new TreeNode(1);
		TreeNode two = new TreeNode(2);
		TreeNode three = new TreeNode(3);
		TreeNode four = new TreeNode(4);
		TreeNode five = new TreeNode(5);
		TreeNode six = new TreeNode(6);
		one.setLeft(two);
		one.setRight(three);
		two.setLeft(four);
		two.setRight(five);
		three.setLeft(six);
		return one;
	}
}
