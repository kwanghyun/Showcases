package algorithm.trees;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

import algorithm.trees.TreeNode;

/* 
Given a binary tree, print it in Top View of it. 
       1
    /     \
   2       3
  /  \    / \
 4    5  6   7
Top view of the above binary tree is
4 2 1 3 7

        1
      /   \
    2       3
      \   
        4  
          \
            5
             \
               6
Top view of the above binary tree is
2 1 3 6
*/
public class TopView {
	class QNode {
		int level;
		TreeNode tnode;

		public QNode(int level, TreeNode tnode) {
			this.level = level;
			this.tnode = tnode;
		}
	}

	public static Map<Integer, Integer> ht = new TreeMap<>();

	public void topView(TreeNode root, int level) {
		if (root == null)
			return;
		// create a queue for level order traversal
		Queue<QNode> queue = new LinkedList<>();
		queue.add(new QNode(level, root));

		while (!queue.isEmpty()) {
			QNode q = queue.remove();
			TreeNode node = q.tnode;
			int lvl = q.level;

			// check if this is the first node you are visiting at the level
			if (ht.containsKey(lvl)) {

			} else {// print it, its the first element at his level
				System.out.print(node.val + "   ");
				ht.put(lvl, node.val);
			}

			if (node.left != null) {
				queue.add(new QNode(lvl - 1, node.left));
			}
			if (node.right != null) {
				queue.add(new QNode(lvl + 1, node.right));
			}
		}
	}
}
