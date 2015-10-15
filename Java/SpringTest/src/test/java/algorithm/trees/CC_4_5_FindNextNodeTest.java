package algorithm.trees;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CC_4_5_FindNextNodeTest {

	CC_4_5_FindNextNode obj = new CC_4_5_FindNextNode();
	TreeNode testTree;
	
	@Before
	public void setUp() throws Exception {
		testTree = generateTree();
	}

	@Test
	public void test() {
		assertEquals(2, obj.findNext(testTree, 1, false).value);
	}

	@Test
	public void test1() {
		assertEquals(6, obj.findNext(testTree, 3, false).value);
	}
	
	@Test
	public void test2() {
		assertEquals(7, obj.findNext(testTree, 4, false).value);
	}
	
	@Test
	public void test3() {
		assertEquals(null, obj.findNext(testTree, 5, false));
	}
	
	public TreeNode generateTree() {

		// 		  1
		// 	     / \
		//     /    \
		//    /      \
		//   2       3
		//  / \      /
		// 4  5    6
		// /  		/ \
		// 7 	   8   9
		// Preorder: 1 2 4 7 5 3 6 8 9
		// Inorder: 7 4 2 5 1 8 6 9 3
		// Postorder: 7 4 5 2 8 9 6 3 1
		// Level-order: 1 2 3 4 5 6 7 8 9

		TreeNode one = new TreeNode(1);
		TreeNode two = new TreeNode(2);
		TreeNode three = new TreeNode(3);
		TreeNode four = new TreeNode(4);
		TreeNode five = new TreeNode(5);
		TreeNode six = new TreeNode(6);
		TreeNode seven = new TreeNode(7);
		TreeNode eight = new TreeNode(8);
		TreeNode nine = new TreeNode(9);
		one.setLeft(two);
		one.setRight(three);
		two.setLeft(four);
		two.setRight(five);
		three.setLeft(six);
		four.setLeft(seven);
		six.setLeft(eight);
		six.setRight(nine);
		return one;
	}

}
