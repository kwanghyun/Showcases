package algorithm.trees;

/*
 * Implement a function to check if a tree is balanced. For the purposes of this question, 
 * a balanced tree is defined to be a tree such that no two leaf nodes differ in distance 
 * from the root by more than one.
 * 
 * Time Complexity: O(n^2) Worst case occurs in case of skewed tree.
 */
public class BalancedBinaryTree {

	public boolean isBalanced(TreeNode root) {

		if (dfs(root) == -1)
			return false;
		return true;
	}

	private int dfs(TreeNode root) {
		if (root == null)
			return 0;

		int left = dfs(root.left);
		int right = dfs(root.right);

		if (left == -1 || right == -1) {
			return -1;
		} else if (Math.abs(left - right) >= 2) {
			return -1;
		}

		return 1 + Math.max(left, right);
	}

	TreeNode root;

	/* Returns true if binary tree with root as root is height-balanced */
	boolean isBalancedI(TreeNode node) {
		int lh; /* for height of left subtree */
		int rh; /* for height of right subtree */
		if (node == null)
			return true;

		/* Get the height of left and right sub trees */
		lh = height(node.left);
		rh = height(node.right);

		if (Math.abs(lh - rh) <= 1 && isBalanced(node.left) && isBalanced(node.right))
			return true;

		/* If we reach here then tree is not height-balanced */
		return false;
	}

	int max(int a, int b) {
		return (a >= b) ? a : b;
	}

	int height(TreeNode node) {
		if (node == null)
			return 0;

		return 1 + max(height(node.left), height(node.right));
	}

	/*
	 * Optimized implementation: Above implementation can be optimized by
	 * calculating the height in the same recursion rather than calling a
	 * height() function separately. Thanks to Amar for suggesting this
	 * optimized version. This optimization reduces time complexity to O(n).
	 * 
	 */
	class Height {

		int height = 0;
		int lh = 0; // Height of left subtree
		int rh = 0; // Height of right subtree
	}

	/* Returns true if binary tree with root as root is height-balanced */
	boolean isBalanced(TreeNode root, Height height) {
		/*
		 * l will be true if left subtree is balanced and r will be true if
		 * right subtree is balanced
		 */
		boolean l = false, r = false;

		/* If tree is empty then return true */
		if (root == null) {
			height.height = 0;
			return true;
		}

		/* Get the height of left and right sub trees */
		l = isBalanced(root.left, height);
		r = isBalanced(root.right, height);

		/*
		 * If difference between heights of left and right subtrees is more than
		 * 2 then this node is not balanced so return 0
		 */
		if ((height.lh - height.rh >= 2) || (height.rh - height.lh >= 2))
			return false;

		/*
		 * If this node is balanced and left and right subtrees are balanced
		 * then return true
		 */
		else
			return l && r;
	}

	public static void main(String args[]) {
		BalancedBinaryTree cbt = new BalancedBinaryTree();
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
		// TreeNode twowentyfive = new TreeNode(25);
		// TreeNode twowentysix = new TreeNode(26);
		towenty.setLeft(eight);
		towenty.setRight(twoentytwo);
		eight.setLeft(four);
		eight.setRight(twowelve);
		twowelve.setLeft(ten);
		twowelve.setRight(fourteen);
		four.setLeft(one);
		twoentytwo.setLeft(twowentyone);
		twoentytwo.setRight(twowentyfour);
		// twowentyfour.setRight(twowentyfive);
		// twowentyfive.setRight(twowentysix);
		System.out.println(cbt.isBalanced(towenty));
	}

}
