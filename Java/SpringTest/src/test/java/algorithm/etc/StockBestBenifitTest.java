package algorithm.etc;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import algorithm.stringArray.StockBestBenifitI;

public class StockBestBenifitTest {

	StockBestBenifitI obj = new StockBestBenifitI();
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testMinRightAfterMax() {
		 int[] arr = { 3, 2, 6, 4, 8, 10, 24, 1, 7 };
		 String result = obj.solution(arr);
		 System.out.println("1:" + result);
		 assertEquals("2, 24", result);
	}
	
	@Test
	public void testLastMax(){
		 int[] arr = { 3, 2, 15};	
		 String result = obj.solution(arr);
		 System.out.println("2:" + result);
		 assertEquals("2, 15", result);
	}
	
	@Test
	public void testMaxBetweenMin(){
		 int[] arr = { 3, 2, 6, 4, 8, 1, 24, 1, 7, 0 };
		 String result = obj.solution(arr);
		 System.out.println("3:" + result);
		 assertEquals("1, 24", result);
	}

	@Test
	public void testLastMaxWithMiddleMin(){
		int[] arr = { 3, 2, 6, 4, 8, 1, 24, 11, 0, 30 };
		String result = obj.solution(arr);
		System.out.println("4:" + result);
		assertEquals("0, 30", result);
	}

//	@Test
	public void testMinRightAfterMax2() {
		 int[] arr = { 3, 2, 6, 4, 8, 10, 24, 1, 7 };
		 String result = obj.solution2(arr);
		 System.out.println("5:" + result);
		 assertEquals("2, 24", result);
	}
	
//	@Test
	public void testLastMax2(){
		 int[] arr = { 3, 2, 15};	
		 String result = obj.solution2(arr);
		 System.out.println("6:" + result);
		 assertEquals("2, 15", result);
	}
	
//	@Test
	public void testMaxBetweenMin2(){
		 int[] arr = { 3, 2, 6, 4, 8, 1, 24, 1, 7, 0 };
		 String result = obj.solution2(arr);
		 System.out.println("7:" + result);
		 assertEquals("1, 24", result);
	}

//	@Test
	public void testLastMaxWithMiddleMin2(){
		int[] arr = { 3, 2, 6, 4, 8, 1, 24, 11, 0, 30 };
		String result = obj.solution2(arr);
		System.out.println("8:" + result);
		assertEquals("0, 30", result);
	}

	@Test
	public void testMinRightAfterMax3() {
		 int[] arr = { 3, 2, 6, 4, 8, 10, 24, 1, 7 };
		 int result = obj.maxProfit(arr);
		 System.out.println("5:" + result);
		 assertEquals(22, result);
	}
	
	@Test
	public void testLastMax3(){
		 int[] arr = { 3, 2, 15};	
		 int result = obj.maxProfit(arr);
		 System.out.println("6:" + result);
		 assertEquals(13, result);
	}
	
	@Test
	public void testMaxBetweenMin3(){
		 int[] arr = { 3, 2, 6, 4, 8, 1, 24, 1, 7, 0 };
		 int result = obj.maxProfit(arr);
		 System.out.println("7:" + result);
		 assertEquals(23, result);
	}

	@Test
	public void testLastMaxWithMiddleMin3(){
		int[] arr = { 3, 2, 6, 4, 8, 1, 24, 11, 0, 30 };
		int result = obj.maxProfit(arr);
		System.out.println("8:" + result);
		assertEquals(30, result);
	}
	
	@Test
	public void testMaxAfterMinsMin3(){
		int[] arr = { 3, 5, 6, 4, 8, 24, 2, 1, 0 };
		int result = obj.maxProfit(arr);
		System.out.println("9:" + result);
		assertEquals(21, result);
	}

}
