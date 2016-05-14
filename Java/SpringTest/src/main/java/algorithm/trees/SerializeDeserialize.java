package algorithm.trees;

/*
 * Given a binary search tree (BST), how can you serialize and deserialize
 * it in O(n) time?
 * 
 * For example, for the following BST, 5 2 7 1 3 6 8 4
 */
public class SerializeDeserialize {

    class TreeNode
    {
        TreeNode left;
        TreeNode right;
        int val;
     
        public TreeNode(int x)
        {
            this.val = x;
        }
    }
 
    private void printInorder(TreeNode root)
    {
        if (root == null) return;
         
        printInorder(root.left);
        System.out.print(" "+ root.val + ",");
	    printInorder(root.right);
    }
     
    private void printPreorder(TreeNode root)
    {
        if (root == null) return;
         
        System.out.print(" "+ root.val + ",");
        printPreorder(root.left);
        printPreorder(root.right);
    }
     
    private TreeNode deserializeArrayOptimized(int[] preorder, int[] currIndex, int min, int max)
    {
        if (currIndex[0] >= preorder.length) return null;
         
        TreeNode root = null;
         
        if ((preorder[currIndex[0]] > min) && (preorder[currIndex[0]] < max))
        {
            root = new TreeNode(preorder[currIndex[0]]);
            currIndex[0] += 1;
            root.left = deserializeArrayOptimized(preorder, currIndex, min, root.val);
            root.right = deserializeArrayOptimized(preorder, currIndex, root.val, max);
        }
         
        return root;
    }
     
    private int findDivision(int[] preorder, int value, int low, int high)
    {
        int i;
        for (i = low; i <= high; i++)
        {
            if (value < preorder[i])
                break;
        }
        return i;
    }
 
    private TreeNode deserializeArray(int[] preorder, int low, int high)
    {
        if (low > high) return null;
         
        TreeNode root = new TreeNode(preorder[low]);
         
        int divIndex = findDivision(preorder, root.val, low+1, high);
         
        root.left = deserializeArray(preorder, low + 1, divIndex - 1);
        root.right = deserializeArray(preorder, divIndex, high);
 
        return root;
    }
     
    public static void main (String[] args)
    {
        /*
                5
          2            7
        1   3        6    8
              4
        */
         
        int[] preorder = {5,2,1,3,4,7,6,8};
         
        SerializeDeserialize solution = new SerializeDeserialize();
         
        int[] currIndex = new int[1];
        currIndex[0] = 0;
         
        int min  = Integer.MIN_VALUE;
        int max  = Integer.MAX_VALUE;
 
        TreeNode root = solution.deserializeArrayOptimized(preorder, currIndex, min, max);
         
        // TreeNode root = solution.deserializeArray(preorder, 0, preorder.length - 1);
         
        System.out.print("Inorder array for constructed BST is:");
        solution.printInorder(root);
         
        System.out.println("");
         
        System.out.print("Preorder array for constructed BST is:");
        solution.printPreorder(root);
 
    }
    
	public void printPreOrder(TreeNode root) {
		if (root == null)
			return;
		System.out.println(root.val);
		printPreOrder(root.left);
		printPreOrder(root.right);
	}

	public void printInOrder(TreeNode root) {
		if (root == null)
			return;
		printPreOrder(root.left);
		System.out.println(root.val);
		printPreOrder(root.right);
	}

	// 1
	// / \
	// 2 3
	// / \ /
	// 4 5 6
	// / / \
	// 7 8 9
//	public TreeNode createTestTree() {
//		TreeNode one = new TreeNode(1);
//		TreeNode two = new TreeNode(2);
//		TreeNode three = new TreeNode(3);
//		TreeNode four = new TreeNode(4);
//		TreeNode five = new TreeNode(5);
//		TreeNode six = new TreeNode(6);
//		TreeNode seven = new TreeNode(7);
//		TreeNode eight = new TreeNode(8);
//		TreeNode nine = new TreeNode(9);
//		one.setLeft(two);
//		one.setRight(three);
//		two.setLeft(four);
//		two.setRight(five);
//		three.setLeft(six);
//		four.setLeft(seven);
//		six.setLeft(eight);
//		six.setRight(nine);
//		return one;
//	}

}
