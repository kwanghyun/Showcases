package algorithm.stringArray;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import algorithm.stringArray.CheckIsomorphic;

public class CheckIsomorphicTest {
	CheckIsomorphic obj = null;
	@Before
	public void setUp() throws Exception {
		obj = new CheckIsomorphic();
	}

	@Test
	public void test() {
		String s = "foo";
		String t = "bar";
		assertTrue(!obj.isIsomorphic2(s, t));
	}

	@Test
	public void test1() {
		String s = "abca";
		String t = "zbxz";
		assertEquals(true, (obj.isIsomorphic2(s, t)));
	}
	
	@Test
	public void test2() {
		String s = "egg";
		String t = "add";
		assertTrue(obj.isIsomorphic2(s, t));
	}

}
