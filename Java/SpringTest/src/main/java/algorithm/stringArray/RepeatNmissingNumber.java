package algorithm.stringArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Given an unsorted array of size n with numbers ranging from 1 to n. One
 * number from set {1, 2, 3, …, n} is missing and one number occurs twice in
 * array. 
 * 
 * int arr[] = {1, 5, 3, 4, 1, 2};
 * 
 * Where 1 is repeating twice and 6 is missing.
 * 
 * Write code to find the two numbers (one which is missing and one which is
 * repeating).
 */	
public class RepeatNmissingNumber {	
	/*
	 * Method-2:(Using Hash)
	 * 
	 * We can use an extra array of size n which will store the count of
	 * occurrences of i at i’th position in the array. In this case we need to
	 * traverse the array only once (and then we need to traverse the auxiliary
	 * array also)
	 * 
	 * Time taken: O(n) Extra Space Required: O(n)
	 */
	public ArrayList<Integer> repeatedNumber(final List<Integer> a) {
		// ArrayList<Integer> input = new ArrayList<>(a.size());
		int[] input = new int[a.size()];
		Arrays.fill(input, 0);

		int len = a.size();
		for (int i = 0; i < len; i++) {
			int num = a.get(i);
			input[num - 1] = input[num - 1] + num;
		}

		ArrayList<Integer> result = new ArrayList<>();
		int two = 0;
		int missing = 0;
		for (int i = 0; i < len; i++) {
			if (input[i] == 0) {
				missing = i + 1;
			}
			if (input[i] > i + 1) {
				two = i + 1;
			}

		}
		result.add(two);
		result.add(missing);
		return result;
	}
}
