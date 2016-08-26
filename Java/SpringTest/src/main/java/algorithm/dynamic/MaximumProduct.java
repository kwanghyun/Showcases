package algorithm.dynamic;

/*
 * Find the contiguous subarray within an array (containing at least one
 * number) which has the largest product.
 * 
 * For example, given the array [2,3,-2,4], the contiguous subarray [2,3]
 * has the largest product = 6.
 */
public class MaximumProduct {

	public int maxProduct(int[] arr) {
		int max = Integer.MIN_VALUE;

		for (int i = 0; i < arr.length; i++) {
			for (int l = 0; l < arr.length; l++) {
				if (i + l < arr.length) {
					int product = calProduct(arr, i, l);
					max = Math.max(product, max);
				}

			}

		}
		return max;
	}

	public int calProduct(int[] A, int i, int j) {
		int result = 1;
		for (int m = i; m <= j; m++) {
			result = result * A[m];
		}
		return result;
	}

	/*
	 * When iterating the array, each element has two possibilities: positive
	 * number or negative number. We need to track a minimum value, so that when
	 * a negative number is given, it can also find the maximum value. We define
	 * two local variables, one tracks the maximum and the other tracks the
	 * minimum.
	 */
	public int maxProductI(int[] arr) {
		if (arr == null || arr.length == 0)
			return 0;

		int tmpMax = arr[0];
		int min = arr[0];
		int max = arr[0];

		for (int i = 1; i < arr.length; i++) {
			tmpMax = Math.max(Math.max(arr[i] * tmpMax, arr[i]), arr[i] * min);
			min = Math.min(Math.min(arr[i] * tmpMax, arr[i]), arr[i] * min);
			max = Math.max(max, tmpMax);
		}
		return max;
	}

	public int maxProduct_(int[] arr) {
		if (arr == null || arr.length == 0)
			return 0;

		int max = 0;

		for (int i = 0; i < arr.length; i++) {
			int tmp_min = Math.min(max * arr[i], arr[i]);
			max = Math.max(tmp_min * arr[i], max * arr[i]);
		}
		return max;
	}

	public static void main(String[] args) {
		MaximumProduct ob = new MaximumProduct();
		int[] arr = { 2, 3, -2, 4 };
		System.out.println(ob.maxProductI(arr));
		System.out.println(ob.maxProduct_(arr));
	}
}
