package algorithm.math;

import algorithm.Utils;

/*
 * Implement pow(x, n).
 * 
 * This is a great example to illustrate how to solve a problem during a technical interview. 
 * The first and second solution exceeds time limit; the third and fourth are accepted.
 */
public class Power {
	public double myPow(double x, int n) {
		if (n == 0)
			return 1;

		if (n < 0) {
			return 1 / myPowHelper(x, -n);
		}
		return myPowHelper(x, n);
	}

	public double myPowHelper(double x, int n) {
		if (n == 0)
			return 1;

		double v = myPowHelper(x, n / 2);

		if (n % 2 == 0) {
			return v * v;
		} else {
			return v * v * x;
		}
	}

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

	public double powerCS(double x, int n, int callstack) {
		if (n == 0) {
			Utils.printCS(callstack, " (EOR) return 1");
			return 1;
		}
		Utils.printCS(callstack, " (+) x = " + x + ", n = " + n);
		double v = powerCS(x, n / 2, callstack + 1);
		Utils.printCS(callstack, " (-) x = " + x + ", n = " + n + ", v = " + v);

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
		System.out.println(pw.powerCS(2, 9, 0));

	}
}
