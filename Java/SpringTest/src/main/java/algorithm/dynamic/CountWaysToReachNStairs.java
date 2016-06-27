package algorithm.dynamic;

import java.util.HashMap;
import java.util.Map;

/*
 * There are n stairs, a person standing at the bottom wants to reach the
 * top. The person can climb either 1 stair or 2 stairs at a time. Count the
 * number of ways, the person can reach the top stairs
 * 
 * Examples :
 * 
 * Input: n = 1
 * Output: 1
 * There is only one way to climb 1 stair
 * 
 * Input: n = 2
 * Output: 2
 * There are two ways: (1, 1) and (2)
 * 
 * Input: n = 4
 * Output: 5
 * (1, 1, 1, 1), (1, 1, 2), (2, 1, 1), (1, 2, 1), (2, 2)
 */

/*
 * We can easily find recursive nature in above problem. The person can
 * reach n’th stair from either (n-1)’th stair or from (n-2)’th stair. Let
 * the total number of ways to reach n’t stair be ‘ways(n)’. The value of
 * ‘ways(n)’ can be written as following.
 * 
 * ways(n) = ways(n-1) + ways(n-2)
 * 
 * The above expression is actually the expression for Fibonacci numbers,
 * but there is one thing to notice, the value of ways(n) is equal to
 * fibonacci(n+1).
 * 
 * ways(1) = fib(2) = 1 
 * ways(2) = fib(3) = 2
 * ways(3) = fib(4) = 3
 */
public class CountWaysToReachNStairs {
	static Map<Integer, Integer> map = new HashMap<>();

	public static int countWays(int n) {
		if (n == 1 || n == 2)
			return n;
		return countWays(n - 1) + countWays(n - 2);
	}

	public static int countWaysI(int n) {
		if (n == 1 || n == 2)
			return n;

		if (map.get(n) != null) {
			return map.get(n);
		}
		int count = countWaysI(n - 1) + countWaysI(n - 2);
		map.put(n, count);

		return count;
	}

	public static void main(String[] args) {
		System.out.println(countWays(15));
		System.out.println(countWaysI(15));
	}
}
