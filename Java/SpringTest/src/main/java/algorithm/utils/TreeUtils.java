package algorithm.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import algorithm.trees.ConstructBinaryTreeFromInPostOrder;
import algorithm.trees.ConstructBinaryTreeFromPreInOrder;
import algorithm.trees.TreeNode;

public class TreeUtils {
	public static final int NULL_NODE_INT = -1;

	public static void printPreorder(TreeNode root) {
		if (root == null)
			return;
		printPreorder_(root);
		System.out.println("");
	}

	private static void printPreorder_(TreeNode root) {
		if (root == null)
			return;

		System.out.print(" " + root.val + " ");
		printPreorder_(root.left);
		printPreorder_(root.right);
	}

	public static void printInorder(TreeNode root) {
		if (root == null)
			return;

		printInorder_(root);
		System.out.println("");
	}

	public static void printInorder_(TreeNode root) {
		if (root == null)
			return;

		printInorder_(root.left);
		System.out.print(" " + root.val + " ");
		printInorder_(root.right);
	}

	public static void printPostorder(TreeNode root) {
		if (root == null)
			return;

		printPostorder_(root);
		System.out.println("");
	}

	public static void printPostorder_(TreeNode root) {
		if (root == null)
			return;

		printPostorder_(root.left);
		printPostorder_(root.right);
		System.out.print(" " + root.val + " ");
	}

	public static String addSpaces(int len, int value, char padding) {
		return addSpaces(len, value + "", padding);
	}

	public static String addSpaces(int len, String value, char padding) {
		StringBuilder result = new StringBuilder();
		result.append(value);
		boolean isLeftTurn = true;
		for (int i = result.length(); i < len; i++) {
			if (isLeftTurn) {
				result.insert(0, padding);
			} else {
				result.append(padding);
			}
			isLeftTurn = !isLeftTurn;
		}
		return result.toString();
	}

	public static void drawTree(TreeNode root) {

		List<List<TreeNode>> lists = getTreeAsLeveledList(root);
		int maxNodeCount = (int) Math.pow(2, lists.size() - 1);

		for (int level = 0; level < lists.size(); level++) {
			int indentation = (maxNodeCount * 3) / (lists.get(level).size() + 1);
			for (TreeNode node : lists.get(level)) {
				String spaces = new String(new char[indentation]).replace("\0", " ");
				if (node.val != -1) {
					System.out.print(spaces + addSpaces(3, node.val, ' '));
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
							if (node.left.val != -1) {
								System.out.print(spaces + addSpaces(3, "/", ' '));
							} else {
								System.out.print(spaces + addSpaces(3, " ", ' '));
							}
						}

						// already space added at left child.
						if (node.left != null) {
							spaces = "";
						}

						if (node.right != null) {
							if (node.right.val != -1) {
								System.out.print(spaces + addSpaces(3, "\\", ' '));
							} else {
								System.out.print(spaces + addSpaces(3, " ", ' '));
							}
						}
					} else {
						// Both null case
						System.out.print(spaces + addSpaces(3, " ", ' '));
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

				if (node.val == -1) {
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

	public static TreeNode buildLevelOrderBstI(int[] levelOrder) {
		TreeNode[] nodes = new TreeNode[levelOrder.length];
		TreeNode root = new TreeNode(levelOrder[0]);
		nodes[0] = root;

		int level = 1;
		int idx = 1;
		boolean isDone = false;

		while (!isDone) {
			for (int i = 0; i < (1 << level); i++) {

				if (idx >= levelOrder.length) {
					isDone = true;
					break;
				}
				if (levelOrder[idx] != -1) {
					System.out.println(
							"idx = " + idx + ", val = " + levelOrder[idx] + ", parent = " + levelOrder[(idx - 1) / 2]);
					TreeNode node = new TreeNode(levelOrder[idx]);
					nodes[idx] = node;
					TreeNode parent = nodes[(idx - 1) / 2];
					if (idx % 2 == 1) {
						parent.left = node;
					} else {
						parent.right = node;
					}
				}
				idx++;
			}
			level++;
		}
		return root;
	}

	public static TreeNode buildLevelOrderBst(int[] levelOrder) {
		Queue<TreeNode> q = new LinkedList<>();
		TreeNode root = new TreeNode(levelOrder[0]);
		q.offer(root);

		int idx = 1;

		while (!q.isEmpty() && idx < levelOrder.length) {
			TreeNode parent = q.poll();

			// left
			if (levelOrder[idx] != -1) {
				System.out.println(
						"idx = " + idx + ", val = " + levelOrder[idx] + ", parent = " + levelOrder[(idx - 1) / 2]);
				TreeNode node = new TreeNode(levelOrder[idx]);
				parent.left = node;
				q.offer(node);

			}
			idx++;

			if (idx >= levelOrder.length)
				break;

			// right
			if (levelOrder[idx] != -1) {
				System.out.println(
						"idx = " + idx + ", val = " + levelOrder[idx] + ", parent = " + levelOrder[(idx - 1) / 2]);
				TreeNode node = new TreeNode(levelOrder[idx]);
				q.offer(node);
				parent.right = node;
			}
			idx++;

		}
		return root;
	}

	public static TreeNode buildLevelOrderBst(String str) {

		String[] strArr = str.replace('[', ' ').replace(']', ' ').split(",");
		System.out.println(Arrays.toString(strArr));
		int[] levelOrder = new int[strArr.length];

		for (int i = 0; i < strArr.length; i++) {
			String s = strArr[i].trim();
			if (s.equals("null"))
				levelOrder[i] = NULL_NODE_INT;
			else
				levelOrder[i] = Integer.parseInt(s);
		}
		System.out.println(Arrays.toString(levelOrder));
		return buildLevelOrderBst(levelOrder);
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
		// TreeNode root = buildBstFromRange(1, 10);
		// int[] levelOrder = { 1, 2, 3, 4, -1, -1, 5, 6 };
		int[] levelOrder = { 1, -1, 3, 2, 4, -1, -1, -1, 5 };

		TreeNode root = buildLevelOrderBst(levelOrder);
		printInorder(root);
		// printPreorder(root);
		// System.out.println("");
		drawTree(root);
		// printPreorder(root);
		// System.out.println("");
		// printInorder(root);
		// System.out.println("");
		// printPostorder(root);
		// System.out.println("");
		String str = "[1,null,3,2,4,null,null,null,5]";
		TreeNode r = buildLevelOrderBst(str);
		drawTree(r);
	}
}
