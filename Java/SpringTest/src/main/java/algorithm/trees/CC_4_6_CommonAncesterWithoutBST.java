package algorithm.trees;

public class CC_4_6_CommonAncesterWithoutBST {
	// 1
	// / \
	// / \
	// / \
	// 2 3
	// / \ /
	// 4 5 6
	// / / \
	// 7 8 9
	// Preorder: 1 2 4 7 5 3 6 8 9
	// Inorder: 7 4 2 5 1 8 6 9 3
	// Postorder: 7 4 5 2 8 9 6 3 1
	// Level-order: 1 2 3 4 5 6 7 8 9

	public TreeNode findCommonAncester(TreeNode root, int value1, int value2) {
		if (find(root.left, value1) && find(root.left, value2))
			return findCommonAncester(root.left, value1, value2);
		else if (find(root.right, value1) && find(root.right, value2))
			return findCommonAncester(root.right, value1, value2);

		System.out.println(root.value);
		return root;
	}

	public boolean find(TreeNode root, int value) {
		if (root == null)
			return false;
		if (root.value == value)
			return true;
		//		System.out.println(root.value);
		return find(root.left, value) || find(root.right, value);
	}


	

	public static void main(String args[]) {
		CC_4_6_CommonAncesterWithoutBST ca = new CC_4_6_CommonAncesterWithoutBST();
		TreeNode foundNode = ca.findCommonAncester(ca.generateTree(), 7, 5);
		System.out.println(foundNode.value);
//		System.out.println(ca.recursion(0, 0));
	}

	public TreeNode generateTree() {

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
