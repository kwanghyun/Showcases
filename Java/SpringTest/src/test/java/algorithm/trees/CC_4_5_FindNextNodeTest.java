package algorithm.trees;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import algorithm.utils.TreeUtils;

// 		  	 1
// 	     	/ \
//     	   /    \
//        /      \
//       2       3
//      / \      /
//     4  5    6
//    /  		/ \
//   7 	   8   9
// Preorder: 1 2 4 7 5 3 6 8 9
// Inorder: 7 4 2 5 1 8 6 9 3
// Postorder: 7 4 5 2 8 9 6 3 1
// Level-order: 1 2 3 4 5 6 7 8 9
public class CC_4_5_FindNextNodeTest {

	CC_4_5_FindNextNode obj = new CC_4_5_FindNextNode();
	TreeNode testTree;

	@Before
	public void setUp() throws Exception {
		testTree = generateTree();
	}

	@Test
	public void test() {
		assertEquals(2, obj.findNext(testTree, 1, false).val);
	}

	@Test
	public void test1() {
		assertEquals(6, obj.findNext(testTree, 3, false).val);
	}

	@Test
	public void test2() {
		assertEquals(7, obj.findNext(testTree, 4, false).val);
	}

	@Test
	public void test3() {
		assertEquals(null, obj.findNext(testTree, 5, false));
	}

	public TreeNode generateTree() {

		int[] preorder = { 1, 2, 4, 7, 5, 3, 6, 8, 9 };
		int[] inorder = { 7, 4, 2, 5, 1, 8, 6, 9, 3 };
		TreeNode root = TreeUtils.buildInPreorderTree(inorder, preorder);
		return root;
	}

}
