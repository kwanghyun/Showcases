package algorithm.trees;

/*
 * Serializing/deserializing binary tree in most space-efficient way
 */
public class SerializeDeserialize {
	
	public String serialize(TreeNode root, String str){
		
		if(root == null)
			return "#";
		str = root.value + serialize(root.left, str) + serialize(root.right, str);
		return str;
	}
	
	public TreeNode deserialize(char[] arr, int count){
		if(count > arr.length)
			return null;
		
		if(arr[count] == '#'){
			System.out.println("#hit : " + count);
			return null;
		}
		TreeNode node = new TreeNode(Character.getNumericValue(arr[count]));
		node.left = deserialize(arr, count +1);
		node.right = deserialize(arr, count +1);
		
		return node;
	}
	
	public static void main(String args[]){
		SerializeDeserialize mw = new SerializeDeserialize();
		String str = "";
		System.out.println(mw.serialize(mw.generateEntireTree(), str));
		String rstr = mw.serialize(mw.generateEntireTree(), str);
		System.out.println("--------------------------");
		char[] arr = rstr.toCharArray();
		mw.printPreOrder(mw.deserialize(arr, 0));
	}
	
	public void printPreOrder(TreeNode root){
		if(root == null)
			return;
		System.out.println(root.value);
		printPreOrder(root.left);
		printPreOrder(root.right);
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

}
