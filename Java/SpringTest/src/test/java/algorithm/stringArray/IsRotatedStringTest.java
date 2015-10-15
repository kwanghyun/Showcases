package algorithm.stringArray;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import algorithm.stringArray.IsRotatedString;

public class IsRotatedStringTest {
	IsRotatedString cr; 
	@Before
	public void setUp() throws Exception {
		cr = new IsRotatedString();
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

	@Test
	public void testNormal2_1() {
		assertEquals(true, cr.solution2("waterbottle", "erbottlewat"));
	}
	
	@Test
	public void testNormal2_2() {
		assertEquals(false, cr.solution2("waterbottle", "erbottlewate"));		
	}

	@Test
	public void testNormal2_3() {
		assertEquals(false, cr.solution2("waterbottle", ""));		
	}

	@Test
	public void testNormal2_4() {
		assertEquals(true, cr.solution2("akka", "aakk"));		
	}

	@Test
	public void testNormal2_5() {
		assertEquals(true, cr.solution2("waterbottle", "lewaterbott"));		
	}

	@Test
	public void testNormal2_6() {
		assertEquals(true, cr.solution2("waterbottle", "waterbottle"));		
	}


}
