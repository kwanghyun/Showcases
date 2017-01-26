package algorithm.bits;

/*
 * Calculate the sum of two integers a and b, but you are not allowed to use
 * the operator + and -.
 * 
 * 
 * Example: Given a = 1 and b = 2, return 3.
 */

public class SumOfTwoIntegers {
	/*
	 * Given two numbers a and b, a&b returns the number formed by '1' bits on a
	 * and b. When it is left shifted by 1 bit, it is the carry.
	 * 
	 * For example, given a=101 and b=111 (in binary), the a&b=101. a&b << 1 =
	 * 1010.
	 * 
	 * a^b is the number formed by different bits of a and b. a&b=10.
	 */
	public int getSum(int a, int b) {

		while (b != 0) {
			int c = a & b;
			System.out.println("[1] a = " + a + ", b = " + b + ", c = " + c);
			a = a ^ b;
			b = c << 1;
			System.out.println("[2] a = " + a + ", b = " + b + ", c = " + c);
		}

		return a;
	}

	int getSumII(int a, int b) {
		if (b == 0)
			return a;
		int sum = a ^ b;
		int carry = (a & b) << 1;
		System.out.println("[1] sum = " + sum + ", carry = " + carry);
		return getSum(sum, carry);
	}

	public int getSumI(int a, int b) {
		int result = 0;
		int carry = 0;
		for (int i = 0; i < 32; i++) {
			int x = (a >> i) & 1;
			int y = (b >> i) & 1;
			int z = (a >>> i) & 1;
			System.out.println(i + " : " + ", x = " + x + ", y = " + y + ", z = " + z);
			int num = carry + x + y;
			carry = num / 2;
			if (num % 2 == 1) {
				if (i == 31) {
					result = 0 - result;
				} else {
					result += Math.pow(2, i);
				}
			}
		}

		return result;
	}

	public static void main(String[] args) {
		SumOfTwoIntegers ob = new SumOfTwoIntegers();
		System.out.println(ob.getSum(5, 7));

	}
}
