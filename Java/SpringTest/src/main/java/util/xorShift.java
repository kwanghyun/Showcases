package util;

public class xorShift {

	/**
	 * Medium-quality Random Number Generator Suitable for Testing.
	 * 
	 * Many benchmarks are, unbeknownst to their developers or users, simply
	 * tests of how great a concurrency bottleneck the RNG is. Rather than using
	 * a general-purpose RNG, it is better to use simple pseudorandom functions.
	 * 
	 * You don¡¯t need high-quality randomness; all you need is enough randomness
	 * to ensure the numbers change from run to run.
	 * 
	 * @param args
	 */

	static int xorShift(int y) {
		y ^= (y << 6);
		y ^= (y >>> 21);
		y ^= (y << 7);
		return y;
	}

	public static void main(String[] args) {
		for(int i=0; i<10000; i++)
		{
			System.out.println(xorShift(i));
		}
	}

}
