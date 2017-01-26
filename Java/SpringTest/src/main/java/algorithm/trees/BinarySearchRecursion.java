package algorithm.trees;

public class BinarySearchRecursion {
//	class Node{
//		String value;
//		Node left;
//		Node right;
//		public Node(String val){
//			this.value = val;
//		}
//	}
	
	TreeNode root = null;
	
	public void insert(TreeNode newNode){
		root = insert(root, newNode);
	}
	
	public TreeNode insert(TreeNode root, TreeNode newNode){
		if(root==null){
			root = newNode;
		}else if(root.str.compareTo(newNode.str) < 0){
				root.right = insert(root.right, newNode);
		}else if(root.str.compareTo(newNode.str) > 0){
				root.left = insert(root.left, newNode);
		}
		return root;
	}
	
	public void preOrder(TreeNode root){
		if(root != null){
			System.out.println(root.val);
			preOrder(root.left);
			preOrder(root.right);
		}
	}
	
	public void middleOrder(TreeNode root){
		if(root != null){

			preOrder(root.left);
			System.out.println(root.val);
			preOrder(root.right);
		}
	}
	public void endOrder(TreeNode root){
		if(root != null){

			preOrder(root.left);
			preOrder(root.right);
			System.out.println(root.val);
		}
	}
	public static void main(String args []){
		BinarySearchRecursion bsr = new BinarySearchRecursion();
		bsr.insert(new TreeNode("kkkk"));
		bsr.insert(new TreeNode("aaaa"));
		bsr.insert(new TreeNode("zzzz"));
		bsr.insert(new TreeNode("cccc"));
		bsr.insert(new TreeNode("dddd"));
		bsr.insert(new TreeNode("jjjj"));

		bsr.preOrder(bsr.root);
		System.out.println("------------------------");
		bsr.middleOrder(bsr.root);
		System.out.println("------------------------");
		bsr.endOrder(bsr.root);

	}
}
