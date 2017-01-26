package algorithm.trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*Given a binary tree, imagine yourself standing on the right side of it, return the
values of the nodes you can see ordered from top to bottom. For example, given the
following binary tree,
	1 <---
   / \
  2  3 <---
   \
    5 <---
You can see [1, 3, 5].

This problem can be solve by using a queue. On each level of the tree, we add the
right-most element to the results.
*/
public class TreeRightSideView {

	public List<Integer> rightSideView(TreeNode root) {

		ArrayList<Integer> result = new ArrayList<Integer>();
		LinkedList<TreeNode> queue = new LinkedList<TreeNode>();

		if (root == null)
			return result;

		queue.offer(root);

		while (queue.size() > 0) {

			// get size here
			int size = queue.size();

			for (int i = 0; i < size; i++) {
				TreeNode node = queue.poll();

				// the first element in the queue (right-most of the tree)
				if (i == 0) {
					result.add(node.val);
				}

				// add right first
				if (node.right != null) {
					queue.offer(node.right);
				}

				// add left
				if (node.left != null) {
					queue.offer(node.left);
				}
			}
		}
		return result;
	}
}
