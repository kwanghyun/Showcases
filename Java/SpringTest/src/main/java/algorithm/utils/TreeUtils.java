package algorithm.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import algorithm.trees.ConstructBinaryTreeFromInPostOrder;
import algorithm.trees.ConstructBinaryTreeFromPreInOrder;
import algorithm.trees.TreeNode;

public class TreeUtils {
	public static void printPreorder(TreeNode root) {
		if (root == null)
			return;

		System.out.print(" " + root.value + " ");
		printPreorder(root.left);
		printPreorder(root.right);
	}

	public static void printInorder(TreeNode root) {
		if (root == null)
			return;

		printInorder(root.left);
		System.out.print(" " + root.value + " ");
		printInorder(root.right);
	}

	public static void printPostorder(TreeNode root) {
		if (root == null)
			return;

		printPostorder(root.left);
		printPostorder(root.right);
		System.out.print(" " + root.value + " ");
	}

	public static void drawTree(TreeNode root) {
		int default_space = 2;
		List<List<TreeNode>> lists = getTreeAsLeveledList(root);
		int maxNodeCount = (int) Math.pow(2, lists.size() - 1);

		for (int level = 0; level < lists.size(); level++) {
			int indentation = (maxNodeCount * 3) / (lists.get(level).size() + 1);
			for (TreeNode node : lists.get(level)) {
				String spaces = new String(new char[indentation]).replace("\0", " ");
				if (node.value != -1) {
					System.out.print(spaces + node.value);
				} else {
					System.out.print(spaces + " ");
				}
			}
			System.out.println("");
			for (TreeNode node : lists.get(level)) {
				String spaces = new String(new char[indentation]).replace("\0", " ");
				if (node != null) {
					if (node.left != null || node.right != null) {
						if (node.left != null) {
							if (node.left.value != -1) {
								System.out.print(spaces + "/");
							} else {
								System.out.print(spaces + " ");
							}
						}

						// already space added at left child.
						if (node.left != null) {
							spaces = "";
						}

						if (node.right != null) {
							if (node.right.value != -1) {
								System.out.print(spaces + " \\");
							} else {
								System.out.print(spaces + "  ");
							}
						}
					}else{
						//Both null case
						System.out.print(spaces + "  ");
					}
				}
			}
			System.out.println("");

		}
	}

	private static List<List<TreeNode>> getTreeAsLeveledList(TreeNode root) {
		List<List<TreeNode>> lists = new ArrayList<>();
		Queue<TreeNode> q = new LinkedList<>();
		q.offer(root);
		boolean isDone = false;

		while (!isDone) {
			List<TreeNode> currList = new ArrayList<>();
			int size = q.size();
			int nullLeafCount = 0;
			for (int i = 0; i < size; i++) {
				TreeNode node = q.poll();

				if (node.value == -1) {
					nullLeafCount++;
					if (nullLeafCount == size) {
						isDone = true;
					}
				}
				currList.add(node);

				if (node.left != null) {
					q.offer(node.left);
				} else {
					q.add(new TreeNode(-1));
				}

				if (node.right != null) {
					q.offer(node.right);
				} else {
					q.add(new TreeNode(-1));
				}
			}

			System.out.println(currList);
			lists.add(new ArrayList<TreeNode>(currList));
		}
		return lists;
	}

	public static TreeNode buildInOrderAscendingTree() {
		int[] inorder = { 1, 2, 3, 4, 5, 6, 7 };
		int[] preorder = { 4, 3, 1, 2, 6, 5, 7 };
		return buildInPreorderTree(inorder, preorder);
	}

	public static TreeNode buildInOrderAscendingTree2_element_missplaced() {
		int[] inorder = { 1, 2, 6, 4, 5, 3, 7 };
		int[] preorder = { 4, 6, 1, 2, 3, 5, 7 };
		return buildInPreorderTree(inorder, preorder);
	}

	public static TreeNode buildBstFromPreorder(int count) {
		int[] preorder = new int[count];
		for (int i = 0; i < count; i++) {
			preorder[i] = i + 1;
		}
		return buildBstFromPreorder(preorder, 0, preorder.length - 1);
	}

	public static TreeNode buildBstFromPreorder(int[] preorder) {
		return buildBstFromPreorder(preorder, 0, preorder.length - 1);
	}

	public static TreeNode buildBstFromRange(int start, int end) {
		if (start > end)
			return null;
		int mid = (start + end) / 2;
		TreeNode left = buildBstFromRange(start, mid - 1);
		TreeNode root = new TreeNode(mid);
		TreeNode right = buildBstFromRange(mid + 1, end);
		root.left = left;
		root.right = right;
		return root;
	}

	private static int findDivision(int[] preoder, int start, int end) {
		int idx;
		for (idx = start + 1; idx < end; idx++) {
			if (preoder[start] < preoder[idx]) {
				break;
			}
		}
		return idx;
	}

	private static TreeNode buildBstFromPreorder(int[] preoder, int start, int end) {
		if (start > end)
			return null;

		int divisionIdx = findDivision(preoder, start, end);

		TreeNode node = new TreeNode(preoder[start]);
		node.left = buildBstFromPreorder(preoder, start + 1, divisionIdx - 1);
		node.right = buildBstFromPreorder(preoder, divisionIdx, end);
		return node;
	}

	public static TreeNode buildInPreorderTree(int[] inorder, int[] preorder) {
		if (inorder == null || preorder == null || inorder.length != preorder.length)
			return null;

		ConstructBinaryTreeFromPreInOrder inPostConst = new ConstructBinaryTreeFromPreInOrder();
		return inPostConst.buildTree(preorder, inorder);
	}

	public static void main(String[] args) {
		// int[] preorder = { 5, 3, 1, 4, 7, 6, 8 };
		int[] preorder = { 3, 1, 4 };
		// TreeNode root = buildBstFromPreorder(preorder);
		TreeNode root = buildBstFromRange(1, 15);
		// printPreorder(root);
		System.out.println("");
		drawTree(root);
		printPreorder(root);
		System.out.println("");
		printInorder(root);
		System.out.println("");
		printPostorder(root);
		System.out.println("");
	}
}
