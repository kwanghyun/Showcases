package algorithm.trees;

/*
 * Serializing/deserializing binary tree in most space-efficient way
 */
public class SerializeDeserialize {

	int index = 0;

	public String serialize(TreeNode root, String str) {

		if (root == null)
			return "#";
		return root.value + serialize(root.left, str)
				+ serialize(root.right, str);
	}

	public TreeNode deserialize(char[] arr) {
		if (index >= arr.length)
			return null;

		if (arr[index] == '#') {
			index++;
			System.out.println("#hit : " + index);
			return deserialize(arr);
		}else{
			TreeNode node = new TreeNode(Character.getNumericValue(arr[index]));
			index++;
			node.left = deserialize(arr);
			index++;
			node.right = deserialize(arr);
			return node;
		}	
	}
	
	int idx = 0;
	public TreeNode dese(String str) {
		if (idx > str.length() - 1)
			return null;

		if (str.charAt(idx) == '#') {
			idx ++;
			return dese(str);
		}

		TreeNode node = new TreeNode(Character.getNumericValue(str.charAt(idx)));
		idx ++;
		node.left = dese(str);
		idx ++;
		node.right = dese(str);

		return node;
	}
	
	//NOTE : by using local param "index" it will start -f
	public boolean dese(String str, int index) {
		if (index > str.length() - 1)
			return false;

		if (str.charAt(index) == '#') {
			return false;
		}

		TreeNode node = new TreeNode(Character.getNumericValue(str.charAt(index)));
		
		if(dese(str, index + 1))
			node.left = node; 
		if(dese(str, index + 1))
			node.right = node; 
		
		return true;
	}

	public static void main(String args[]) {
		SerializeDeserialize mw = new SerializeDeserialize();
		String serializedStr = "";
		serializedStr = mw.serialize(mw.createTestTree(), serializedStr);
		System.out.println("serializedStr :: " + serializedStr);
		System.out.println("--------------------------");
		char[] arr = serializedStr.toCharArray();
		mw.printPreOrder(mw.deserialize(arr));
//		System.out.println("--------------------------");
//		mw.printPreOrder(mw.dese(serializedStr, 0));
		System.out.println("--------------------------");
		TreeNode root = mw.dese(serializedStr);
		mw.printPreOrder(root);
		System.out.println("--------------------------");
		mw.printInOrder(root);
	}

	public void printPreOrder(TreeNode root) {
		if (root == null)
			return;
		System.out.println(root.value);
		printPreOrder(root.left);
		printPreOrder(root.right);
	}
	
	public void printInOrder(TreeNode root) {
		if (root == null)
			return;
		printPreOrder(root.left);
		System.out.println(root.value);
		printPreOrder(root.right);
	}

	// 		 1
	// 		/  \
	// 	   2   3
	//   / \    /
	//  4  5 	6
	//  / 		/ \
	// 7     8   9
	public TreeNode createTestTree() {
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
