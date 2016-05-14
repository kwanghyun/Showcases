package algorithm.etc;

import java.util.ArrayList;

/*
 * Count the number of prime numbers less than a non-negative number, n
 * 
 * Java Solution 1
 * 
 * This solution exceeds time limit.
 */
public class CountPrimes {

	public int countPrimes(int n) {
		n = n - 1;

		ArrayList<Integer> primes = new ArrayList<Integer>();

		if (n <= 1)
			return 0;
		if (n == 2)
			return 1;
		if (n == 3)
			return 2;

		primes.add(2);
		primes.add(3);

		for (int i = 4; i <= n; i++) {
			boolean isPrime = true;
			for (int p : primes) {
				int m = i % p;
				//if found self-divided number, it's not prime.
				if (m == 0) {
					isPrime = false;
					break;
				}
			}

			if (isPrime) {
				primes.add(i);
			}
		}

		return primes.size();
	}


	public static void main(String[] args) {
		CountPrimes ob = new CountPrimes();
		System.out.println(ob.countPrimes(5));

	}
}
