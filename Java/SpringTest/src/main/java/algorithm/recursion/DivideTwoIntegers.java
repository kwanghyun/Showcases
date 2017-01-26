package algorithm.recursion;

public class DivideTwoIntegers {

	public int divide(int dividend, int divisor) {
		if (dividend < divisor || divisor == 0)
			return 0;

		if (divisor == 1)
			return dividend;

		int result = 0;
		return divide(dividend, divisor, result);
	}

	public int divide(long dividend, long divisor, int result) {
		if (dividend < divisor)
			return 0;

		if (dividend >= (long) divisor * divisor) {
			result += divisor;
			System.out.println("1. dividend: " + dividend + ", divisor * divisor : " + (long) divisor * divisor);
			result = result + divide(dividend - (long) divisor * divisor, (long) divisor * divisor, result);
		} else {
			result += 1;
			System.out.println("2. dividend: " + dividend + ", divisor * divisor : " + divisor);
			result = result + divide(dividend - divisor, divisor, result);
		}
		return result;
	}

	public static void main(String[] args) {
		DivideTwoIntegers ob = new DivideTwoIntegers();
		// System.out.println(ob.divide(10, 3));
		// System.out.println(ob.divide(10, 2));
		// System.out.println(ob.divide(88, 6));
		// long n = (long) 65536 * 65536;
		// System.out.println(n);
		// System.out.println(ob.divide(Integer.MAX_VALUE, 2));
		// 2147483647
		long dividend = Integer.MAX_VALUE;
		long divisor = (long) 65536 * 65536;
		String result = dividend > divisor ? "dividend " + dividend + " is bigger than divisor " + divisor
				: "divisor " + divisor + " is bigger" + "is bigger than dividend " + dividend;
		System.out.println(result);
		System.out.println(Integer.MAX_VALUE / 65536);
	}
}
