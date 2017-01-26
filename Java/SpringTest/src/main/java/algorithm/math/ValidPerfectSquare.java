package algorithm.math;

/*
 * Given a positive integer num, write a function which returns True if num
 * is a perfect square else False.
 * 
 * Note: Do not use any built-in library function such as sqrt.
 * 
 * Example 1:
 * 
 * Input: 16 
 * Returns: True Example 2:
 * 
 * Input: 14 
 * Returns: False
 */
public class ValidPerfectSquare {
	public boolean isPerfectSquare(int num) {
		long start = 0;
		long end = num;
		while (start <= end) {
			long mid = (start + end) / 2;
			if (mid * mid == num)
				return true;
			else if (mid * mid > num) {
				end = mid - 1;
			} else {
				start = mid + 1;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		ValidPerfectSquare ob = new ValidPerfectSquare();
		System.out.println(ob.isPerfectSquare(2147483647));
	}
}
