package algorithm.stringArray;

import java.util.HashMap;
import java.util.Map;

/*
Given an integer array and a positive integer k, count all distinct pairs 
with difference equal to k.

Examples:

Input: arr[] = {1, 5, 3, 4, 2}, k = 3
Output: 2
There are 2 pairs with difference 3, the pairs are {1, 4} and {5, 2} 

Input: arr[] = {8, 12, 16, 4, 0, 20}, k = 4
Output: 5
There are 5 pairs with difference 4, the pairs are {0, 4}, {4, 8}, 
{8, 12}, {12, 16} and {16, 20} 
*/
public class DiffK {

	public int getCount(int[] arr, int k) {
		Map<Integer, Integer> map = new HashMap<>();
		int count = 0;
		for (int i = 0; i < arr.length; i++) {

			map.put(k - arr[i], i);
			map.put(k + arr[i], i);

			if (map.containsKey(arr[i])) {
				count++;
				System.out.println((k - arr[i]) + " : " + (k + arr[i]) + ": " + i);
			}
		}

		return count;
	}

	public static void main(String[] args) {
		DiffK ob = new DiffK();
		// int arr[] = { 1, 5, 3, 4, 2 };
		// System.out.println(ob.getCount(arr, 3));
		int arr[] = { 8, 12, 16, 4, 0, 20 };
		System.out.println(ob.getCount(arr, 4));
	}
}
