package algorithm.stringArray;

import java.util.ArrayList;

/*
 * Two numbers are missing from the first hundred numbers. They are NOT
 * sorted. How to find them? You can't sort.. and can't iterate one by one..
 * has to be less than O(N)? Can't use stack , set or any collection
 * interface or in fact any other data structure or array!
 * 0 1 2 4 5 7 8  9 10 11 12 13 14 15  // missing 3 and 6
	[--------------] [-----------------------]
	7 indices,    7 indices,
	9 values:     7 values
	2 missing     0 missing: do not evaluate!
 */
public class FindMissingNumbers {

	public void findMissingNumbers(int arr[], ArrayList<Integer> list, int start, int end, int m) {
		if (start > end)
			return;

		if (m == 0)
			return;

		int mid = (start + end) / 2;
		
		//no missing numbers 
//		if (arr[mid] - arr[start] == mid - start) {
//			
//		}else{
//			findMissingNumbers(arr, list, mid)
//		}
	}
}
