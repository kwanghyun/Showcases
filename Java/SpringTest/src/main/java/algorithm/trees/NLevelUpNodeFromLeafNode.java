package algorithm.trees;

import java.util.ArrayList;

import algorithm.utils.TreeUtils;

/*
 * Given a Tree (binary and unbalanced)
 * 
 * Find all the nodes in that tree which are 'n' levels up the leaf node...
 * 
 * Eg:
 * 
                         5 
                         /  \ 
                 2                  8 
                 /  \                  /  \ 
          1           3           6           9 
                      \           \           \ 
                        4             7             10
 * 
 * then output
 * 1 -> 5
 * 4 -> 2
 * 7 -> 8
 * 10 -> 8
 * 
 * hence ans is B,A
 */
public class NLevelUpNodeFromLeafNode {

	public void getNlevelUpNode(TreeNode root, int level, ArrayList<Integer> list) {
		if (root == null)
			return;

		// Leaf Node
		if (root.left == null && root.right == null) {
			if (list.size() >= level) {
				System.out.println(root.val + " -> " + list.get(list.size() - level));
			}
		}

		list.add(root.val);
		getNlevelUpNode(root.left, level, list);
		getNlevelUpNode(root.right, level, list);
		list.remove(list.size() - 1);
	}

	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<>();
		TreeNode root = TreeUtils.buildBstFromRange(1, 10);
		TreeUtils.drawTree(root);
		NLevelUpNodeFromLeafNode ob = new NLevelUpNodeFromLeafNode();
		ob.getNlevelUpNode(root, 2, list);
	}
}
