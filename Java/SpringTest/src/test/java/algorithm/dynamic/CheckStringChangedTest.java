package algorithm.dynamic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CheckStringChangedTest {
	CheckStringChanged obj = new CheckStringChanged();
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test1() {
		String org = "abc";
		String dest = "abd";
		assertTrue(obj.isChanged(org, dest));
	}
	
	@Test
	public void test2() {
		String org = "abc";
		String dest = "adc";
		assertTrue(obj.isChanged(org, dest));
	}
	
	@Test
	public void test3() {
		String org = "abc";
		String dest = "ab";
		assertTrue(obj.isChanged(org, dest));
	}

}
