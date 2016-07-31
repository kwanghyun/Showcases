package algorithm.etc;

/*
 * Implement pow(x, n).
 * 
 * This is a great example to illustrate how to solve a problem during a technical interview. 
 * The first and second solution exceeds time limit; the third and fourth are accepted.
 */
public class Power {

	public int pow(int x, int y) {
		int sum = 1;
		for (int i = 0; i < y; i++) {
			sum *= x;
		}
		return sum;
	}

	public int recurPow(int x, int n) {
		if (n == 0)
			return 1;

		return x * recurPow(x, n - 1);
	}

	/* pow(2, 7) = pow(4, 3) * pow(2,1) = pow(8,1) * pow(4 * 1) * pow(2 * 1) */
	public double power(double x, int n) {
		if (n == 0)
			return 1;
		
		double v = power(x, n / 2);
		
		if (n % 2 == 0) {
			return v * v;
		} else {
			return v * v * x;
		}
	}

	public double powI(double x, int n) {
		if (n < 0) {
			return 1 / power(x, -n);
		} else {
			return power(x, n);
		}
	}

	public static void main(String args[]) {
		Power pw = new Power();
		System.out.println(pw.powI(2, 4));
		System.out.println(pw.powI(2, 8));
		System.out.println(pw.recurPow(2, 4));
		System.out.println(pw.recurPow(2, 8));

	}
}
