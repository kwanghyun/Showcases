package algorithm.trees;

/*
 * You have two very large binary trees: T1, with millions of nodes, and T2, 
 * with hundreds of nodes. Create an algorithm to decide if T2 is a subtree of T1.
 */
public class IsSubTree {
//                 1
//                / \
//               /   \
//              /     \
//             2       3
//            / \     /
//           4   5   6
//          /        / \
//         7       8   9
//Preorder:    1 2 4 7 5 3 6 8 9
//Inorder:     7 4 2 5 1 8 6 9 3
//Postorder:   7 4 5 2 8 9 6 3 1
//Level-order: 1 2 3 4 5 6 7 8 9

	
	public boolean isSubTree(TreeNode entire, TreeNode part){
		if(part == null) 
			return true;
		return findAMatch(entire, part);
	}
	
	public boolean findAMatch(TreeNode entire, TreeNode part){

		if(entire == null) return false;
		if(part == null) return false;
		if(entire.value == part.value) 
			return checkTree(entire , part) && checkTree(entire , part);
		return findAMatch(entire.left, part) || findAMatch(entire.right, part);
	}
	
	public boolean checkTree(TreeNode entire, TreeNode part){
 		if(part == null) return true;
 		if(entire.value != part.value) return false;
 		
 		return checkTree(entire.left, part.left) || checkTree(entire.right, part.right);
	}
	
	
	public static void main(String args[]){
		IsSubTree ist = new IsSubTree();
		System.out.println(ist.isSubTree(ist.generateEntireTree(), ist.generatePartTree()));
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
		TreeNode six = new TreeNode(6);
		TreeNode eight = new TreeNode(8);
		TreeNode nine = new TreeNode(9);
		six.setLeft(eight);
		six.setRight(nine);
		return six;
	}

}
