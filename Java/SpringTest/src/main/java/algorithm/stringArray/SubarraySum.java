package algorithm.stringArray;

/*
 * Given an unsorted array of nonnegative integers, find a continous
 * subarray which adds to a given number.
 * 
 * Examples:
 * Input: arr[] = {1, 4, 20, 3, 10, 5}, sum = 33
 * Ouptut: Sum found between indexes 2 and 4
 * 
 * Input: arr[] = {1, 4, 0, 0, 3, 10, 5}, sum = 7
 * Ouptut: Sum found between indexes 1 and 4
 * 
 * Input: arr[] = {1, 4}, sum = 0
 * Output: No subarray found
 */
public class SubarraySum {

	int subArraySumII(int arr[], int n, int target) {
		int start = 0;
		int sum = 0;
		// Pick a starting point
		for (int i = 0; i < n; i++) {
	
			if (i <= arr[i])
				sum += arr[i];
	
			// If curr_sum exceeds the sum, then remove the starting elements
			while (sum > target) {
				sum -= arr[start];
				start++;
			}
	
			if (sum == target) {
				System.out.println("Sum found between indexes " + start + " and " + i);
				return sum;
			}
		}
	
		System.out.println("No subarray found");
		return 0;
	}

	/*
	 * Returns true if the there is a subarray of arr[] with sum equal to 'sum'
	 * otherwise returns false. Also, prints the result
	 */
	int subArraySumI(int arr[], int n, int sum) {
		int curr_sum = arr[0], start = 0, i;

		// Pick a starting point
		for (i = 1; i <= n; i++) {
			// If curr_sum exceeds the sum, then remove the starting elements
			while (curr_sum > sum && start < i - 1) {
				curr_sum = curr_sum - arr[start];
				start++;
			}

			// If curr_sum becomes equal to sum, then return true
			if (curr_sum == sum) {
				int p = i - 1;
				System.out.println("Sum found between indexes " + start + " and " + p);
				return 1;
			}

			// Add this element to curr_sum
			if (i < n)
				curr_sum = curr_sum + arr[i];
		}

		System.out.println("No subarray found");
		return 0;
	}

	/*
	 * Returns true if the there is a subarray of arr[] with sum equal to 'sum'
	 * otherwise returns false. Also, prints the result
	 */
	int subArraySum(int arr[], int n, int sum) {
		int curr_sum, i, j;

		// Pick a starting point
		for (i = 0; i < n; i++) {
			curr_sum = arr[i];

			// try all subarrays starting with 'i'
			for (j = i + 1; j <= n; j++) {
				if (curr_sum == sum) {
					int p = j - 1;
					System.out.println("Sum found between indexes " + i + " and " + p);
					return 1;
				}
				if (curr_sum > sum || j == n)
					break;
				curr_sum = curr_sum + arr[j];
			}
		}

		System.out.println("No subarray found");
		return 0;
	}

	public static void main(String[] args) {
		SubarraySum ob = new SubarraySum();
		int[] arr = { 5, 2, 7, 9, 3 };
		int target = 18;
		System.out.println("-------------subArraySumI------------");
		ob.subArraySumI(arr, arr.length, target);
		System.out.println("-------------subArraySumII------------");
		ob.subArraySumII(arr, arr.length, target);
	}
}
