package algorithm.trees;

/*
 * Given a sorted (increasing order) array, write an algorithm to create a binary tree with minimal height.
 */

/*
 * Mistake Node 
 * 1. Give end length of array but it give me error
 */
public class CC_4_3_ArrayToBST {
	
	TreeNode root;
	
	public TreeNode insertToBST(int[] arr, TreeNode root, int start, int end) {

		if (end < start)
			return null;

		int middle = start + (end - start) / 2;
		System.out.println("middle[ "+middle+" ]: "+ arr[middle]);
		root = new TreeNode(arr[middle]);

		root.left = insertToBST(arr, root.left, start, middle -1);
		root.right = insertToBST(arr, root.right, middle +1, end);

		return root;
	}

	public void printBstInOrder(TreeNode root) {
		if (root == null)
			return;
		printBstInOrder(root.left);
		System.out.println(root.value);
		printBstInOrder(root.right);
	}

	public static void main(String args[]) {
		int[] arr ={ 1,2,3,4,5,6,7,8,9};
		CC_4_3_ArrayToBST atb = new CC_4_3_ArrayToBST();
//		System.out.println("val : "+(int)((9-8)/2));
		TreeNode root = atb.insertToBST(arr, atb.root, 0, arr.length-1);
		atb.printBstInOrder(root);
	}

}
