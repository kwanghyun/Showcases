package algorithm.stringArray;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FindMissingNumberTest {

	FindMissingNumber obj = new FindMissingNumber();

	@Test
	public void test1() {
		int[] arr = { 0, 1, 2, 3, 4, 5, 7 };
		assertEquals(6, obj.findMissingNumber(arr));
	}

	@Test
	public void test2() {
		int[] arr = { 0, 1, 2, 3, 4, 5, 6, 7, 9 };
		assertEquals(8, obj.findMissingNumber(arr));
	}

	@Test
	public void test3() {
		int[] arr = { 0, 2 };
		assertEquals(1, obj.findMissingNumber(arr));
	}

}
