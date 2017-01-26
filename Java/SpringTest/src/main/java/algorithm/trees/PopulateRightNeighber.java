package algorithm.trees;

/*
 * Given a binary tree Populate each next pointer to point to its next right
 * node. If there is no next right node, the next pointer should be set to
 * NULL.
 * 
 * Note:
 * 
 * You may only use constant extra space. You may assume that it is a
 * perfect binary tree (ie, all leaves are at the same level, and every
 * parent has two children).
 * 
	Given the following perfect binary tree,
	         1
	       /  \
	      2    3
	     / \  / \
	    4  5  6  7

	After calling your function, the tree should look like:
	         1 -> NULL
	       /  \
	      2 -> 3 -> NULL
	     / \  / \
	    4->5->6->7 -> NULL
 * 
Why doesn’t method 2 work for trees which are not Complete Binary Trees?
Let us consider following tree as an example. In Method 2, we set the nextRight 
pointer in pre order fashion. When we are at node 4, we set the nextRight of its 
children which are 8 and 9 (the nextRight of 4 is already set as node 5). 
nextRight of 8 will simply be set as 9, but nextRight of 9 will be set as NULL 
which is incorrect. We can’t set the correct nextRight, because when we set 
nextRight of 9, we only have nextRight of node 4 and ancestors of node 4, 
we don’t have nextRight of nodes in right subtree of root.

            1
          /    \
        2        3
       / \      /  \
      4   5    6    7
     / \           / \  
    8   9        10   11
 
 * Follow up
 * What if the given tree could be any binary tree? Would your previous solution still work?
 * 
 * Note:
 * You may only use constant extra space.
 * 
	Given the following binary tree,
	         1
	       /  \
	      2    3
	     / \    \
	    4   5    7

	         1 -> NULL
	       /  \
	      2 -> 3 -> NULL
	     / \    \
	    4-> 5 -> 7 -> NULL
	    
	    
*/

public class PopulateRightNeighber {

	public void connectI(TreeNode root) {

		if (root == null)
			return;

		if (root.left != null)
			root.left.next = root.right;

		if (root.right != null) {
			if (root.next != null)
				root.right.next = root.next.left;
			else
				root.right.next = null;

		}
		connectI(root.left);
		connectI(root.right);
	}

	public void connect(TreeNode root) {

		if (root == null)
			return;

		if (root.left != null) {
			if (root.right != null) {
				root.left.next = root.right;
			} else {
				TreeNode p = root.next;
				while (p != null) {
					if (p.left != null) {
						root.left.next = p.left;
						break;
					} else if (p.right != null) {
						root.left.next = p.right;
						break;
					} else {
						p = p.next;
					}
				}

				if (p == null)
					root.left.next = null;

			}
		}

		if (root.right != null) {
			TreeNode p = root.next;
			while (p != null) {
				if (p.left != null) {
					root.right.next = p.left;
					break;
				} else if (p.right != null) {
					root.right.next = p.right;
					break;
				} else {
					p = p.next;
				}
			}

			if (p == null)
				root.right.next = null;

		}
		connect(root.left);
		connect(root.right);
	}

	private void populateNeighbors(TreeNode root) {
		if (root == null) {
			return;
		}

		// populate the right neighbor for left child
		if (root.left != null) {
			if (root.right != null) {
				root.left.next = root.right;
			}
			// find first non-null node after left child at its level
			else {
				TreeNode p = root.next;
				TreeNode nxt;
				while (p != null) {
					nxt = (p.left != null) ? p.left : p.right;

					// we have found the non-null neighbor for left child
					if (nxt != null) {
						root.left.next = nxt;
						break;
					}

					p = p.next;
				}
			}
		}

		// populate the right neighbor for right child
		if (root.right != null) {
			// find first non-null node after right child at its level
			TreeNode p = root.next;
			TreeNode nxt;

			while (p != null) {
				nxt = (p.left != null) ? p.left : p.right;

				// we have found the non-null neighbor for right child
				if (nxt != null) {
					root.right.next = nxt;
					break;
				}

				p = p.next;
			}
		}

		/*
		 * Populating neighbors in the right sub-tree before that of left
		 * sub-tree allows us to access all nodes at the level of current node
		 * using neighbor-node chain while populating neighbors for current
		 * node's child nodes.
		 */

		// populate neighbors in the right sub-tree first
		populateNeighbors(root.right);

		// and then populate neighbors in the left sub-tree
		populateNeighbors(root.left);
	}

	private void traverseUsingNeighbors(TreeNode root) {
		TreeNode currentNode = root;
		while (currentNode != null) {
			TreeNode temp = currentNode;
			currentNode = null;

			// print all the nodes in the current level
			while (temp != null) {
				// keep checking for the left-most node in the level below the
				// current level
				// that is where traversal for next level is going to start
				if (currentNode == null) {
					currentNode = (temp.left != null) ? temp.left : temp.right;
				}

				System.out.print(temp.val + " ");
				temp = temp.next;
			}
			System.out.print("\n\n");
		}
	}

	public TreeNode createTree() {
		TreeNode root = new TreeNode(0);

		TreeNode n1 = new TreeNode(1);
		TreeNode n2 = new TreeNode(2);
		TreeNode n3 = new TreeNode(3);
		TreeNode n5 = new TreeNode(5);
		TreeNode n6 = new TreeNode(6);
		TreeNode n7 = new TreeNode(7);
		TreeNode n8 = new TreeNode(8);

		root.left = n1;
		root.right = n2;

		n1.left = n3;

		n2.left = n5;
		n2.right = n6;

		n6.left = n7;
		n6.right = n8;

		return root;
	}

	public static void main(String[] args) {
		PopulateRightNeighber tree = new PopulateRightNeighber();

		/*
		 * 0 1 2 3 5 6 7 8
		 */
		TreeNode root = tree.createTree();

		tree.populateNeighbors(root);

		tree.traverseUsingNeighbors(root);
	}
}
