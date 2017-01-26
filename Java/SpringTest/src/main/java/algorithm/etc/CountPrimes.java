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

	//Let's optimiaze isPrime as DP
	public int countPrimes(int n) {
		n--;

		if (n <= 1)
			return 0;

		if (n <= 3)
			return n - 1;

		int[] dp = new int[n + 1];
		dp[0] = 0;
		dp[1] = 0;
		dp[2] = 1;
		dp[3] = 2;
		for (int i = 4; i <= n; i++) {
			int add = isPrime(i) ? 1 : 0;
			dp[i] = dp[i - 1] + add;
		}

		return dp[n];
	}

	boolean isPrime(int n) {
		for (int i = 2; i <= Math.sqrt(n); i++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}

	public int countPrimesI(int n) {
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
				// if found self-divided number, it's not prime.
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
		int test_num = 5;
		System.out.println(ob.countPrimes(test_num));
		System.out.println(ob.countPrimesI(test_num));
	}
}
