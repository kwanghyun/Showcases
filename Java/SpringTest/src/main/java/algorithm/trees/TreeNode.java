package algorithm.trees;

public class TreeNode {
	public int val;
	String str;
	public TreeNode left;
	public TreeNode right;
	public TreeNode next;
	public TreeNode parent;

	public TreeNode(int value) {
		this.left = null;
		this.right = null;
		this.val = value;
	}

	public TreeNode(String str) {
		this.left = null;
		this.right = null;
		this.str = str;
	}

	public TreeNode(TreeNode left, TreeNode right, int value) {
		this.left = left;
		this.right = right;
		this.val = value;
	}

	public TreeNode getLeft() {
		return left;
	}

	public void setValue(int value) {
		this.val = value;
	}

	public void setLeft(TreeNode left) {
		this.left = left;
	}

	public void setRight(TreeNode right) {
		this.right = right;
	}

	public TreeNode getRight() {
		return right;
	}

	public int getValue() {
		return val;
	}

	public void printValue() {
		System.out.println(val);
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("[value=" + val);

		if (parent != null) {
			sb.append(" : parent = " + parent.val);
		}

		if (left != null) {
			sb.append(" : left = " + left.val);
		}

		if (right != null) {
			sb.append(" : right = " + right.val);
		}
		sb.append("]");
		return sb.toString();
	}

}
