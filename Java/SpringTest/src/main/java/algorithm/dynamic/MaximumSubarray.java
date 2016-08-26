package algorithm.dynamic;

public class MaximumSubarray {

	/*
	 * Find the contiguous subarray within an array (containing at least one
	 * number) which has the largest sum. For example, given the array
	 * [-2,1,-3,4,-1,2,1,-5,4], the contiguous subarray [4,-1,2,1] has the
	 * largest sum = 6.
	 * 
	 * This is a wrong solution, check out the discussion below to see why it is
	 * wrong. I put it here just for fun.
	 */
	public int maxSubArray(int[] arr) {
		int sum = 0;
		int maxSum = Integer.MIN_VALUE;

		for (int i = 0; i < arr.length; i++) {
			sum = sum + arr[i];
			maxSum = Math.max(maxSum, sum);

		}
		return maxSum;
	}

	/*
	 * The changing condition for dynamic programming is "We should ignore the
	 * sum of the previous n-1 elements if nth element is greater than the sum."
	 */
	public int maxSubArrayD(int[] arr) {
		int max = arr[0];
		int[] sum = new int[arr.length];
		sum[0] = arr[0];

		for (int i = 1; i < arr.length; i++) {
			sum[i] = Math.max(arr[i], sum[i - 1] + arr[i]);
			max = Math.max(max, sum[i]);
		}
		return max;
	}

	public int maxSubArrayD2(int[] arr) {
		int max_curr = arr[0];
		int max = Integer.MIN_VALUE;

		for (int i = 1; i < arr.length; i++) {
			max_curr = Math.max(max_curr + arr[i], arr[i]);
			max = Math.max(max, max_curr);
		}
		return max;
	}

	public static void main(String[] args) {
		MaximumSubarray ms = new MaximumSubarray();

		int[] arr = { -2, 1, -3, 4, -1, 2, 1, -5, 4 };
		// int[] arr = { 1, 2, 3, -3, -4, 5, 6 };
		// int[] arr = { -1, -2, -3, 4 };
		System.out.println(ms.maxSubArray(arr));
		System.out.println("-----------------------");
		System.out.println(ms.maxSubArrayD(arr));
		System.out.println("-----------------------");
		System.out.println(ms.maxSubArrayD2(arr));

	}
}
