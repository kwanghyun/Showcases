package algorithm.trees;

/*
 * You have two very large binary trees: T1, with millions of nodes, and T2, 
 * with hundreds of nodes. Create an algorithm to decide if T2 is a subtree of T1.
 * 
 * Mistake Note :
 * 
 * 1. When find a common node in left side, we don't need to go right side so 
 * 	"Or" boolean condition doesn't executed second condition when first one is true.
 * 
 */
public class CC_4_7_IsSubTree {
	//          1
	//         / \
	//        /   \
	//       /      \
	//      2       3
	//     / \      /
	//    4  5   6
	//   /       / \
	// 7       8  9
	// Preorder: 1 2 4 7 5 3 6 8 9
	// Inorder: 7 4 2 5 1 8 6 9 3
	// Postorder: 7 4 5 2 8 9 6 3 1
	// Level-order: 1 2 3 4 5 6 7 8 9

	public boolean isSubTree(TreeNode root1, TreeNode root2) {

		TreeNode match = findNode(root1, root2);
		if (match == null)
			return false;

		return checkTrees(match, root2);
	}

	public TreeNode findNode(TreeNode root1, TreeNode root2) {
		TreeNode foundNode = null;
		if (root1 == null)
			return null;

		if (root1.value == root2.value) {
			return root1;
		}

		foundNode = findNode(root1.left, root2);
		if (foundNode == null)
			foundNode = findNode(root1.right, root2);

		return foundNode;
	}

	public boolean checkTrees(TreeNode root1, TreeNode root2) {
		if (root2 == null)
			return true;

		if (root1 == null)
			return false;

		if (root1.value != root2.value)
			return false;

		return checkTrees(root1.left, root2.left)
				&& checkTrees(root1.right, root2.right);
	}

	public static void main(String args[]) {
		CC_4_7_IsSubTree ist = new CC_4_7_IsSubTree();
//		System.out.println(ist.isSubTree(ist.generateEntireTree(),
//				ist.generatePartTree()));
//
//		System.out.println(ist.findNode(ist.generateEntireTree(),
//				ist.generatePartTree()).value);
		
		System.out.println(ist.find(ist.generateEntireTree(), 7).value);
	}

	public TreeNode find(TreeNode node, int value) {
		TreeNode foundNode = null;
		if (node == null)
			return null;

		if (node.value == value)
			return node;

//		if (node.left != null) {
//			foundNode = find(node.left, value);
//		}
//		if (node.right != null) {
//			foundNode = find(node.right, value);
//		}
		foundNode = find(node.left, value);
		if(foundNode == null){
			foundNode = find(node.right, value);
		}
		
		return foundNode;
	}

	public TreeNode generateEntireTree() {
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

	public TreeNode generatePartTree() {
		TreeNode six = new TreeNode(6);
		TreeNode eight = new TreeNode(8);
		TreeNode nine = new TreeNode(9);
		six.setLeft(eight);
		six.setRight(nine);
		return six;
	}

}
