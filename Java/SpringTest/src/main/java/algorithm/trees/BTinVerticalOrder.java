package algorithm.trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Given a binary tree, return the vertical order traversal of its nodes'
 * values. (ie, from top to bottom, column by column).
 * 
 *       1
        /     \
       2       3
      / \      / \
     4   5  6    7
               \     \
                8     9 
           
The output of print this tree vertically will be:
4
2
1 5 6
3 8
7
9 
 */
public class BTinVerticalOrder {
	public List<List<Integer>> verticalOrder(TreeNode root) {
		List<List<Integer>> result = new ArrayList<>();
		if (root == null)
			return result;

		// level and list
		HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();

		LinkedList<TreeNode> queue = new LinkedList<>();
		LinkedList<Integer> level = new LinkedList<>();

		queue.offer(root);
		level.offer(0);

		int minLevel = 0;
		int maxLevel = 0;

		while (!queue.isEmpty()) {
			TreeNode p = queue.poll();
			int lvl = level.poll();

			// track min and max levels
			minLevel = Math.min(minLevel, lvl);
			maxLevel = Math.max(maxLevel, lvl);

			if (map.containsKey(lvl)) {
				map.get(lvl).add(p.val);
			} else {
				ArrayList<Integer> list = new ArrayList<Integer>();
				list.add(p.val);
				map.put(lvl, list);
			}

			if (p.left != null) {
				queue.offer(p.left);
				level.offer(lvl - 1);
			}

			if (p.right != null) {
				queue.offer(p.right);
				level.offer(lvl + 1);
			}
		}

		for (int i = minLevel; i <= maxLevel; i++) {
			if (map.containsKey(i)) {
				result.add(map.get(i));
			}
		}

		return result;
	}
}
