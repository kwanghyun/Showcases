package algorithm.math;

/*Given an integer, write a function to determine if it is a power of two.*/
public class PowerOfTwo {
	public boolean isPowerOfTwo(int n) {
		if (n <= 0)
			return false;

		if (n == 1)
			return true;
		long i = 2;
		System.out.println(i);
		
		while (n > i) {
			if (n > i * i)
				i = i * i;
			else
				i = i * 2;
			System.out.println(i);
			if (n % i != 0)
				return false;
		}
		return true;
	}

	public static void main(String[] args) {
		PowerOfTwo ob = new PowerOfTwo();
		System.out.println(ob.isPowerOfTwo(131072));
		System.out.println(ob.isPowerOfTwo(-16));
	}
}
