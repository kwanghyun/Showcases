package algorithm.recursion;

public class Factorical {

	int n = 10;

	int factorial(int n) {
		if (n == 1) {
			return 1;
		} else {
			return n * factorial(n - 1);
		}
	}

	int factorial_interation(int n) {
		int product = 1;
		for (int i = 2; i < n; i++) {
			product = product * i;
		}
		return product;
	}

	int fib(int n) {
		if (n == 0) {
			return 0;
		} else if (n == 1) {
			return 1;
		} else {
			return fib(n - 1) + fib(n - 2);
		}
	}

	int fib_iteration(int n) {
		if (n <= 2) {
			return n;
		}

		int first = 1, second = 2;
		int third = 0;

		for (int i = 3; i <= n; i++) {
			third = first + second;
			first = second;
			second = third;
		}

		return third;
	}

}
