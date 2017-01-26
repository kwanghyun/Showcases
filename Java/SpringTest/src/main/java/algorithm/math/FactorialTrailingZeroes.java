package algorithm.math;

public class FactorialTrailingZeroes {
	public int trailingZeroes(int n) {
		if (n < 0)
			return -1;

		int count = 0;
		for (long i = 5; n / i >= 1; i *= 5) {
			count += n / i;
			System.out.println("i = " + i + ", count = " + count);
		}

		return count;
	}

	public static void main(String[] args) {
		FactorialTrailingZeroes ob = new FactorialTrailingZeroes();
		System.out.println(ob.trailingZeroes(100));
	}
}
