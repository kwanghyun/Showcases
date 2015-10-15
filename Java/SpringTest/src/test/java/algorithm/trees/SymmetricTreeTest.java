package algorithm.trees;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SymmetricTreeTest {
	SymmetricTree obj = new SymmetricTree();
	
	@Test
	public void test() {
		assertEquals(true, obj.isSymmetric(generateTrueTree()));
	}

	@Test
	public void test2() {
		assertEquals(false, obj.isSymmetric(generateFalseTree()));
	}

	TreeNode generateTrueTree(){
		TreeNode one = new TreeNode(1);
		TreeNode two1 = new TreeNode(2);
		TreeNode two2 = new TreeNode(2);
		TreeNode three1 = new TreeNode(3);
		TreeNode three2 = new TreeNode(3);
		TreeNode four1 = new TreeNode(4);
		TreeNode four2 = new TreeNode(4);
		one.setLeft(two1);
		one.setRight(two2);
		two1.setLeft(three1);
		two1.setRight(four1);
		two2.setLeft(four2);
		two2.setRight(three2);
		return one;
	}
	
	/*             1
	 *             / \
	 *           2   2
	 *            
	 * */
	TreeNode generateFalseTree(){
		TreeNode one = new TreeNode(1);
		TreeNode two1 = new TreeNode(2);
		TreeNode two2 = new TreeNode(2);
		TreeNode three1 = new TreeNode(3);
		TreeNode three2 = new TreeNode(3);
		one.setLeft(two1);
		one.setRight(two2);
		two1.setRight(three1);
		two2.setRight(three2);
		return one;
	}

}
