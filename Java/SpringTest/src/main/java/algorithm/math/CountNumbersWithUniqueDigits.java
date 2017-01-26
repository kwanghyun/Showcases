package algorithm.math;

/*
 * Given a non-negative integer n, count all numbers with unique digits, x,
 * where 0 ≤ x < 10n.
 * 
 * Example: 
 * Given n = 2, return 91. (The answer should be the total numbers
 * in the range of 0 ≤ x < 100, excluding [11,22,33,44,55,66,77,88,99])
 */
public class CountNumbersWithUniqueDigits {

	public int countNumbersWithUniqueDigits(int n) {
		if (n < 2)
			return 0;
		if (n == 2)
			return 91;

		int uniqueCount = (n - 1) * (9 * (int) Math.pow(10, n - 2)) + 9 * 9;
		return (int) Math.pow(10, n) - uniqueCount;
	}

	public static void main(String[] args) {
		CountNumbersWithUniqueDigits ob = new CountNumbersWithUniqueDigits();
		System.out.println(ob.countNumbersWithUniqueDigits(3));
	}
}
