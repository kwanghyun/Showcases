package algorithm.trees;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CommonAncesterTest {
	CommonAncester lca = new CommonAncester();
	
	@Test
	public void test2() {
		assertEquals(8, lca.findLowestCommonAncester(generateTree(), 1, 14).getValue());
	}
	
	@Test
	public void test3() {
		assertEquals(8, lca.findCom(generateTree(), 1, 12).getValue());
	}

	
	@Test
	public void test4() {
		assertEquals(22, lca.findCom(generateTree(), 21, 24).getValue());
	}

	@Test
	public void test5() {
		assertEquals(20, lca.findCom(generateTree(), 1, 24).getValue());
	}
	

//          20
//          / \
//         /   \
//       /       \
//      8       22
//     / \      /  \
//    4  12   21  24
//   /     / \
//  1  10  14
	
	public TreeNode generateTree(){
		TreeNode towenty = new TreeNode(20);
		TreeNode eight = new TreeNode(8);
		TreeNode twoentytwo = new TreeNode(22);
		TreeNode four = new TreeNode(4);
		TreeNode twowelve = new TreeNode(12);
		TreeNode ten = new TreeNode(10);
		TreeNode fourteen = new TreeNode(14);
		TreeNode one = new TreeNode(1);
		TreeNode twowentyone = new TreeNode(21);
		TreeNode twowentyfour = new TreeNode(24);
		towenty.setLeft(eight);
		towenty.setRight(twoentytwo);
		eight.setLeft(four);
		eight.setRight(twowelve);
		twowelve.setLeft(ten);
		twowelve.setRight(fourteen);
		four.setLeft(one);
		twoentytwo.setLeft(twowentyone);
		twoentytwo.setRight(twowentyfour);
		eight.setLeft(ten);
		return towenty;
	}

}
