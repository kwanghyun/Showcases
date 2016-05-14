package algorithm.trees;

/*
 * Given a binary tree with each node having a reference for its 'neighbor'
 * node along with left and right child nodes. A 'neighbor' node for node
 * 'n' is defined as the node located on the immediate right hand side of
 * node 'n'. A node and its neighbor node would be on the same level. If
 * there is no node located on the immediate right hand side of node 'n',
 * then neighbor of node 'n' is null.
 * 
 * As illustrated below, original tree on left hand side would be modified
 * to the tree on the right hand side after populating neighbors for all
 * nodes.
 */
public class PopulateRightNeighber {
	private void populateNeighbors(TreeNode root) {
		if (root == null) {
			return;
		}

		// populate the right neighbor for left child
		if (root.left != null) {
			if (root.right != null) {
				root.left.neighbor = root.right;
			}
			// find first non-null node after left child at its level
			else {
				TreeNode parentNeighbor = root.neighbor;
				TreeNode neighborNode;
				while (parentNeighbor != null) {
					neighborNode = (parentNeighbor.left != null) ? parentNeighbor.left : parentNeighbor.right;

					// we have found the non-null neighbor for left child
					if (neighborNode != null) {
						root.left.neighbor = neighborNode;
						break;
					}

					parentNeighbor = parentNeighbor.neighbor;
				}
			}
		}

		// populate the right neighbor for right child
		if (root.right != null) {
			// find first non-null node after right child at its level
			TreeNode parentNeighbor = root.neighbor;
			TreeNode neighborNode;

			while (parentNeighbor != null) {
				neighborNode = (parentNeighbor.left != null) ? parentNeighbor.left : parentNeighbor.right;

				// we have found the non-null neighbor for right child
				if (neighborNode != null) {
					root.right.neighbor = neighborNode;
					break;
				}

				parentNeighbor = parentNeighbor.neighbor;
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
	
    private void traverseUsingNeighbors(TreeNode root)
    {
        TreeNode currentNode = root;
        while (currentNode != null)
        {
            TreeNode temp = currentNode;
            currentNode = null;
             
            // print all the nodes in the current level
            while(temp != null)
            {
                // keep checking for the left-most node in the level below the current level
                // that is where traversal for next level is going to start
                if (currentNode == null)
                {
                    currentNode = (temp.left != null) ? temp.left : temp.right;
                }
                 
                System.out.print(temp.value + " ");
                temp = temp.neighbor;
            }
            System.out.print("\n\n");
        }
    }
 
     
    public TreeNode createTree()
    {
        TreeNode root = new TreeNode(0);
         
        TreeNode n1 = new TreeNode(1);
        TreeNode n2 = new TreeNode(2);
        TreeNode n3 = new TreeNode(3);
        TreeNode n5 = new TreeNode(5);
        TreeNode n6 = new TreeNode(6);
        TreeNode n7 = new TreeNode(7);
        TreeNode n8 = new TreeNode(8);
          
        root.left  = n1;
        root.right = n2;
          
        n1.left = n3;
          
        n2.left = n5;
        n2.right = n6;
          
        n6.left = n7;
        n6.right = n8;
          
        return root;
    }
    
    public static void main(String[] args)
    {
    	PopulateRightNeighber tree = new PopulateRightNeighber();
 
        /*
                                0
                          1             2
                      3              5     6
                                          7  8
        */
        TreeNode root = tree.createTree();
 
        tree.populateNeighbors(root);
 
        tree.traverseUsingNeighbors(root);
    }
}
