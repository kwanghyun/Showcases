package algorithm.stringArray;

import java.util.HashMap;
import java.util.Map;

/*	
Find four elements a, b, c and d in an array such that a+b = c+d
Given an array of distinct integers, find if there are two pairs (a, b) 
and (c, d) such that a+b = c+d, and a, b, c and d are distinct elements. 
If there are multiple answers, then print any of them.

Example:

Input:   {3, 4, 7, 1, 2, 9, 8}
Output:  (3, 8) and (4, 7)
Explanation: 3+8 = 4+7

Input:   {3, 4, 7, 1, 12, 9};
Output:  (4, 12) and (7, 9)
Explanation: 4+12 = 7+9

Input:  {65, 30, 7, 90, 1, 9, 8};
Output:  No pairs found
Expected Time Complexity: O(n2)
*/
public class FindFourElementThatEqualSumPair {

	public void printSolution(int[] arr) {

		if (arr == null || arr.length == 0)
			return;

		Map<Integer, String> map = new HashMap<>();
		for (int i = 0; i < arr.length; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				int sum = arr[i] + arr[j];
				if (!map.containsKey(sum)) {
					map.put(sum, arr[i] + ", " + arr[j]);
				} else {
					String result = map.get(sum) + ", " + arr[i] + ", " + arr[j];
					System.out.println(result);
				}
			}
		}
	}

	public static void main(String[] args) {
		FindFourElementThatEqualSumPair ob = new FindFourElementThatEqualSumPair();
		int[] arr = { 3, 4, 7, 1, 2, 9, 8 };
		ob.printSolution(arr);
	}
}
