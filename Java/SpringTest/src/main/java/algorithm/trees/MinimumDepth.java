package algorithm.trees;

import java.util.LinkedList;

/*Given a binary tree, find its minimum depth.
 The minimum depth is the number of nodes along the shortest path from the root
 node down to the nearest leaf node.
                1
               / \
              /   \
             /     \
           2       3
          / \     /
        4   5   6
        /        / \
       7       8   9
       
 Need to know LinkedList is a queue. add() and remove() are the two methods to
 manipulate the queue.
 
 */


public class MinimumDepth {
	public int minDepth(TreeNode root) {
		if (root == null) {
			return 0;
		}
		
		LinkedList<TreeNode> nodes = new LinkedList<TreeNode>();
		LinkedList<Integer> counts = new LinkedList<Integer>();
		nodes.add(root);
		counts.add(1);
		
		while (!nodes.isEmpty()) {
			TreeNode curr = nodes.remove();
			int count = counts.remove();
			if (curr.left != null) {
				nodes.add(curr.left);
				counts.add(count + 1);
			}
			if (curr.right != null) {
				nodes.add(curr.right);
				counts.add(count + 1);
			}
			
			if (curr.left == null && curr.right == null) {
				return count;
			}
		}
		return 0;
	}
	
	/*
	@@Result1 : 3
	@@Result2 : 2 -> Recursive will give you wrong answer 
	*/
	public int minDepth2(TreeNode root){
		if(root == null) return 0;
			
		return 1 + Math.min(minDepth2(root.left), minDepth2(root.right));
	}
	
	
	public static void main(String args []){
		MinimumDepth hot = new MinimumDepth();
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
		
		System.out.println("@@Result1 : "+hot.minDepth(one));
		System.out.println("@@Result2 : "+hot.minDepth2(one));
	}
}
