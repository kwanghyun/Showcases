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
public class LowestCommonAncester {

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
	} else {
		return root;
	}
	//return null; ==> this will end up returns null
	return root;
}

	public static void main(String args []){
		
		LowestCommonAncester lca = new LowestCommonAncester();
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
		towenty.setLeft(eight);
		towenty.setRight(twoentytwo);
		eight.setLeft(four);
		eight.setRight(twowelve);
		twowelve.setLeft(ten);
		twowelve.setRight(fourteen);
		four.setLeft(one);
		twoentytwo.setLeft(twowentyone);
		twoentytwo.setRight(twowentyfour);
		eight.setLeft(ten);

		System.out.println(lca.findLowestCommonAncester(towenty, 1, 14).getValue());
		System.out.println(lca.findCom(towenty,  1, 14).getValue());
		System.out.println(lca.findCom(towenty,  1, 12).getValue());
		System.out.println(lca.findCom(towenty,  21, 24).getValue());
		System.out.println(lca.findCom(towenty,  1, 24).getValue());
	}
}
