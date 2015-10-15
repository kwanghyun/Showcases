package algorithm.stringArray;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class MergeSortedArrayTest {
	MergeSortedArray obj = new MergeSortedArray();

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void test() {
		int[] arr1 = new int[10];
		int[] temp = { 1, 3, 5, 7, 9 };
		System.arraycopy(temp, 0, arr1, 0, 5);
		System.out.println(Arrays.toString(arr1));

		int[] arr2 = { 2, 4, 6, 8, 10 };
		int[] expected = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		assertArrayEquals(expected, obj.merge(arr1, 5, arr2, 5));
	}

	@Test
	public void test2() {
		int[] arr1 = new int[10];
		int[] temp = { 2, 4, 6, 8, 10 };
		System.arraycopy(temp, 0, arr1, 0, 5);
		System.out.println(Arrays.toString(arr1));

		int[] arr2 = { 1, 3, 5, 7, 9 };
		int[] expected = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		assertArrayEquals(expected, obj.merge(arr1, 5, arr2, 5));
	}

	@Test
	public void test3() {
		int[] arr1 = new int[10];
		int[] temp = { 6, 7, 8, 9, 10 };
		System.arraycopy(temp, 0, arr1, 0, 5);
		System.out.println(Arrays.toString(arr1));

		int[] arr2 = { 1, 2, 3, 4, 5 };
		int[] expected = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		assertArrayEquals(expected, obj.merge(arr1, 5, arr2, 5));
	}

	@Test
	public void test4() {
		int[] arr1 = new int[10];
		int[] temp = { 1, 2, 3, 4, 5 };
		System.arraycopy(temp, 0, arr1, 0, 5);
		System.out.println(Arrays.toString(arr1));

		int[] arr2 = { 6, 7, 8, 9, 10 };
		int[] expected = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		assertArrayEquals(expected, obj.merge(arr1, 5, arr2, 5));
	}

}
