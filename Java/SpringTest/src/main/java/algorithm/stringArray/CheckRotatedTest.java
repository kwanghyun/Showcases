package algorithm.stringArray;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CheckRotatedTest {
	CheckRotated cr; 
	@Before
	public void setUp() throws Exception {
		cr = new CheckRotated();
	}

	@Test
	public void testNormal() {
		assertEquals(true, cr.solution("waterbottle", "erbottlewat"));
	}
	
	@Test
	public void testNormal2() {
		assertEquals(false, cr.solution("waterbottle", "erbottlewate"));		
	}

	@Test
	public void testNormal3() {
		assertEquals(false, cr.solution("waterbottle", ""));		
	}

	@Test
	public void testNormal4() {
		assertEquals(true, cr.solution("akka", "aakk"));		
	}



}
