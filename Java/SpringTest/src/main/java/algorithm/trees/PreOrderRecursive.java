package algorithm.trees;

//                         1
//                        / \
//                       /   \
//                      /     \
//                    2       3
//                   / \     /
//                 4   5   6
//                /         / \
//               7       8   9
// Preorder:    1 2 4 7 5 3 6 8 9
// Inorder:     7 4 2 5 1 8 6 9 3
// Postorder:   7 4 5 2 8 9 6 3 1
// Level-order: 1 2 3 4 5 6 7 8 9
public class PreOrderRecursive {
	TreeNode root;

	void preorderTraversal(TreeNode root) {
		if (root == null)
			return;
		root.printValue();
		preorderTraversal(root.getLeft());
		preorderTraversal(root.getRight());
	}

	void inorderTraversal(TreeNode root) {
		if (root == null)
			return;
		inorderTraversal(root.getLeft());
		root.printValue();
		inorderTraversal(root.getRight());
	}

	void postTraversal(TreeNode root) {
		if (root == null)
			return;
		postTraversal(root.getLeft());
		postTraversal(root.getRight());
		root.printValue();
	}
	public static void main(String args[]) {
		PreOrderRecursive por = new PreOrderRecursive();
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
		por.preorderTraversal(one);
		System.out.println();
		por.inorderTraversal(one);
		System.out.println();
		por.postTraversal(one);
		System.out.println();
	}
}
