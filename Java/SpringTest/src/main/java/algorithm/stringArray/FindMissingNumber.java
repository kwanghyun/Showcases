package algorithm.stringArray;

/*An array n - 1 unique numbers in the range from 0 to n - 1. 
 * There is only one number in the range from 0 to n - 1 missing. 
 * Please write a function to find the missing number. */
public class FindMissingNumber {
	public int findMissingNumber(int[] arr) {
		int sum1 = 0, sum2 = 0;
		sum2 = (arr.length  * (arr.length + 1)) / 2;
		for (int item : arr) {
			sum1 = sum1 + item;
		}

		return sum2 - sum1;
	}
}
