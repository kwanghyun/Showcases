package algorithm.trees;

//                1
//               / \
//              /   \
//             /     \
//           2       3
//          / \     /
//        4   5   6
//        /        / \
//       7       8   9

public class HeightOfTree {
	public int treeHeight( TreeNode n ){
	    if( n == null ) return 0;
	    return 1 + Math.max( 
	    		treeHeight( n.getLeft() ), treeHeight( n.getRight() ) );
	}
	public static void main(String args []){
		HeightOfTree hot = new HeightOfTree();
		TreeNode one = new TreeNode(1);
		TreeNode two = new TreeNode(2);
		TreeNode three = new TreeNode(3);
		TreeNode four = new TreeNode(4);
		TreeNode five = new TreeNode(5);
		TreeNode six = new TreeNode(6);
		TreeNode seven = new TreeNode(7);
		TreeNode eight = new TreeNode(8);
		TreeNode nine = new TreeNode(9);
//		TreeNode ten = new TreeNode(10);
		one.setLeft(two);
		one.setRight(three);
		two.setLeft(four);
		two.setRight(five);
		three.setLeft(six);
		four.setLeft(seven);
		six.setLeft(eight);
		six.setRight(nine);
//		eight.setLeft(ten);
		System.out.println("@@Result : "+hot.treeHeight(one));
	}
}
