package algorithm.math;

/*
 * Given an array of integers, every element appears twice except for one. 
 * Find that single one.
 */
public class SingleNumber {

	public int singleNumber(int[] arr) {
		int x = 0;

		for (int num : arr) {
			x = x ^ num;
		}

		return x;
	}

	public static void main(String args[]) {
		// int num = 0;
		// int num2 = num ^ 8;
		// System.out.println(num ^ 8);
		// System.out.println(num2 ^ 8);

		SingleNumber sn = new SingleNumber();
		int[] arr = {1,2,3,4,5,1,2,4,5};
		System.out.println(sn.singleNumber(arr));
		
	}
}
