package algorithm.trees;

//            20
//           / \
//          /    \
//         /       \
//        8       22
//       / \      /  \
//     4  12   21  24
//    /     / \
//   1  10  14
public class CommonAncester {

	public TreeNode findLowestCommonAncester(TreeNode root, int value1, int value2) {
		
		while (root != null) {
			int value = root.getValue();

			if (value > value1 && value > value2) {
				root = root.getLeft();
			} else if (value < value1 && value < value2) {
				root = root.getRight();
			} else {
				return root;
			}
		}
		return null;
	}
	
	public TreeNode findCom(TreeNode root, int val1, int val2) {
	
		if (root == null)
			return null;
	
		if (root.value > val1 && root.value > val2) {
			root = findCom(root.left, val1, val2);
		} else if (root.value < val1 && root.value < val2) {
			root = findCom(root.right, val1, val2);
		}
		return root;
	}

}
