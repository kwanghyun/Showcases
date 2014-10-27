package algorithm.trees;

/*
 * Implement a function to check if a tree is balanced. For the purposes of this question, 
 * a balanced tree is defined to be a tree such that no two leaf nodes differ in distance 
 * from the root by more than one.
 */
public class CC_4_1_CheckBalancedTree {

	public int getMaxDepth(TreeNode root){
		if(root == null)
			return 0;
		return 1 + Math.max( getMaxDepth(root.left), getMaxDepth(root.right));
	}
	
	public int getMinDepth(TreeNode root){
		if(root == null)
			return 0;
		return 1+ Math.min( getMinDepth(root.left), getMinDepth(root.right));
	}
	
	public boolean isBalancedTree(TreeNode root){
		return (getMaxDepth(root) - getMinDepth(root) <= 1);
	}
	
//        20
//        / \
//       /    \
//      /       \
//     8       22
//    / \      /  \
//  4  12   21  24
//      
//         

	public static void main(String args[]){
		CC_4_1_CheckBalancedTree cbt = new CC_4_1_CheckBalancedTree();
		TreeNode towenty = new TreeNode(20);
		TreeNode eight = new TreeNode(8);
		TreeNode twoentytwo = new TreeNode(22);
		TreeNode four = new TreeNode(4);
		TreeNode twowelve = new TreeNode(12);
		TreeNode ten = new TreeNode(10);
		TreeNode fourteen = new TreeNode(14);
		TreeNode one = new TreeNode(1);
		TreeNode twowentyone = new TreeNode(21);
		TreeNode twowentyfour = new TreeNode(24);
//		TreeNode twowentyfive = new TreeNode(25);
//		TreeNode twowentysix = new TreeNode(26);
		towenty.setLeft(eight);
		towenty.setRight(twoentytwo);
		eight.setLeft(four);
		eight.setRight(twowelve);
		twowelve.setLeft(ten);
		twowelve.setRight(fourteen);
		four.setLeft(one);
		twoentytwo.setLeft(twowentyone);
		twoentytwo.setRight(twowentyfour);
//		twowentyfour.setRight(twowentyfive);
//		twowentyfive.setRight(twowentysix);
		System.out.println(cbt.isBalancedTree(towenty));
	}
	
}
