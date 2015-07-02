/**
 * 
 */
package algorithm.etc;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author kwjang
 *
 */
public class Closest3SumTest {
	
	Closest3Sum cs = new Closest3Sum();
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
	}
	
	//findCloestSum
	@Test
	public void test1() {
		int[] arr = {-1, 2, 1, 3, -4, 4, 5};
		System.out.println(cs.findCloestSum(arr, 15));
		assertEquals(12,cs.findCloestSum(arr, 15));
	}

	@Test
	public void test1_1() {
		int[] arr = {-4, -3, -2, -1, 0, 1, 2, 3, 4};
		System.out.println(cs.doBetter(arr, 15));
		assertEquals(9,cs.doBetter(arr, 15));
	}

	//doBetter
	@Test
	public void test2() {
		int[] arr = {-1, 2, 1, 3, -4, 4, 5};
		System.out.println(cs.findCloestSum(arr, 15));
		assertEquals(12,cs.findCloestSum(arr, 15));
	}
	
	@Test
	public void test2_1() {
		int[] arr = {-4, -3, -2, -1, 0, 1, 2, 3, 4};
		System.out.println(cs.doBetter(arr, 15));
		assertEquals(9,cs.doBetter(arr, 15));
	}
	

}
