package algorithm.trees;

import java.util.ArrayList;
import java.util.Stack;

public class PostOrder {
	//Definition for binary tree
	public class TreeNode {
	    int val;
	    TreeNode left;
	    TreeNode right;
	    TreeNode(int x) { val = x; }
	}
	 
	 
	public class Solution {
	    public ArrayList<Integer> postorderTraversal(TreeNode root) {
	 
	        ArrayList<Integer> lst = new ArrayList<Integer>();
	 
	        if(root == null)
	            return lst; 
	 
	        Stack<TreeNode> stack = new Stack<TreeNode>();
	        stack.push(root);
	 
	        TreeNode prev = null;
	        while(!stack.empty()){
	            TreeNode curr = stack.peek();
	 
	            // go down the tree.
	            //check if current node is leaf, if so, process it and pop stack,
	            //otherwise, keep going down
	            if(prev == null || prev.left == curr || prev.right == curr){
	                //prev == null is the situation for the root node
	                if(curr.left != null){
	                    stack.push(curr.left);
	                }else if(curr.right != null){
	                    stack.push(curr.right);
	                }else{
	                    stack.pop();
	                    lst.add(curr.val);
	                }
	 
	            //go up the tree from left node    
	            //need to check if there is a right child
	            //if yes, push it to stack
	            //otherwise, process parent and pop stack
	            }else if(curr.left == prev){
	                if(curr.right != null){
	                    stack.push(curr.right);
	                }else{
	                    stack.pop();
	                    lst.add(curr.val);
	                }
	 
	            //go up the tree from right node 
	            //after coming back from right node, process parent node and pop stack. 
	            }else if(curr.right == prev){
	                stack.pop();
	                lst.add(curr.val);
	            }
	 
	            prev = curr;
	        }
	 
	        return lst;
	    }
	}
}
