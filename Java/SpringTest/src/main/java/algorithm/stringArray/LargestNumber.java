package algorithm.stringArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/*
 * Given a list of non negative integers, arrange them such that they form
 * the largest number.
 * 
 * For example, given [3, 30, 34, 5, 9], the largest formed number is
 * 9534330. (Note: The result may be very large, so you need to return a
 * string instead of an integer.)
 * 
 * Analysis
 * 
 * This problem can be solve by simply sorting strings, not sorting integer.
 * Define a comparator to compare strings by concat() right-to-left or
 * left-to-right.
 */
public class LargestNumber {

	public String largestNumber(int[] nums) {
		String[] strs = new String[nums.length];
		for (int i = 0; i < nums.length; i++) {
			strs[i] = String.valueOf(nums[i]);
		}

		Arrays.sort(strs, new Comparator<String>() {
			public int compare(String s1, String s2) {
				String leftRight = s1 + s2;
				String rightLeft = s2 + s1;
				/*
				 * a negative integer, zero, or a positive integer as the first
				 * argument is less than, equal to, or greater than the second.
				 */
				return -leftRight.compareTo(rightLeft);

			}
		});

		StringBuilder sb = new StringBuilder();
		for (String s : strs) {
			sb.append(s);
		}

		while (sb.charAt(0) == '0' && sb.length() > 1) {
			sb.deleteCharAt(0);
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		LargestNumber ob = new LargestNumber();
		System.out.println("39".compareTo("93"));

		int[] arr = { 3, 30, 34, 5, 9 };
		String[] strs = { "3", "30", "34", "5", "9" };
		System.out.println(ob.largestNumber(arr));
		ArrayList<Integer> list = new ArrayList<>();
		Arrays.sort(arr);
		for(int i : arr ) System.out.println(i);
		System.out.println("----------------");
		for(String s : strs ) System.out.println(s);
	}
}
