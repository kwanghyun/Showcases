package algorithm.stringArray;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CheckValidParenthesesTest {
	CheckValidParentheses obj = new CheckValidParentheses();
	@Before
	public void setUp() throws Exception {
	}

//	@Test
	public void testNull() {
		String s = null;
		assertEquals(false, obj.isValid(s));
	}

//	@Test
	public void testEmpty() {
		String s = "";
		assertEquals(false, obj.isValid(s));
	}

//	@Test
	public void testSpace() {
		String s = " ((())) ";
		assertEquals(true, obj.isValid(s));
	}

	@Test
	public void testOK1() {
		String s = "((()))";
		assertEquals(true, obj.isValid(s));
	}

	@Test
	public void testOK2() {
		String s = "[](){()}";
		assertEquals(true, obj.isValid(s));
	}

	@Test
	public void testNG1() {
		String s = "(([))";
		assertEquals(false, obj.isValid(s));
	}

	@Test
	public void testNG2() {
		String s = "(()))";
		assertEquals(false, obj.isValid(s));
	}

	@Test
	public void testNG3() {
		String s = "{[}]";
		assertEquals(false, obj.isValid(s));
	}

	public void testMax() {
		
	}
	
	public void testMin() {
		
	}
}
