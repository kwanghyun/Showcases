package algorithm.dynamic;

import java.util.HashMap;
import java.util.Map;

public class Fibonacci {
	
	int fibonacci(int num) {
		if (num == 0)
			return 0;
		if (num == 1)
			return 1;

		return fibonacci(num - 1) + fibonacci(num - 2);
	}

	public int ifib(int n) {

		if ((n == 1) || (n == 2)) {
			return 1;
		} else {
			int prev = 1, curr = 1, next = 0;
			for (int i = 3; i <= n; i++) {
				next = prev + curr;
				prev = curr;
				curr = next;
			}
			return next;
		}
	}

	Map<Integer, Integer> past_fib = new HashMap<Integer, Integer>();

	int dynamicFibonacci(int num) {
		// Return nth fibonacci number memorizing past solutions"
		int total = 0;

		if (past_fib.containsKey(num))
			return past_fib.get(num);

		if (num == 0 || num == 1) {
			past_fib.put(num, 1);
			return 1;
		}

		total = dynamicFibonacci(num - 1) + dynamicFibonacci(num - 2);
		past_fib.put(num, total);
		return total;
	}
	
	public static void main(String args[]){
		Fibonacci fi = new Fibonacci();
		int num = 40;
		long start = System.currentTimeMillis();
		System.out.println(fi.fibonacci(num));
		long now = System.currentTimeMillis();
		System.out.println("recursive :: " + (now - start));
		
		start = System.currentTimeMillis();
		System.out.println(fi.dynamicFibonacci(num));
		now = System.currentTimeMillis();
		System.out.println("dynamic :: " + (now - start));

		start = System.currentTimeMillis();
		System.out.println(fi.ifib(num));
		now = System.currentTimeMillis();
		System.out.println("while loop :: " + (now - start));


	}

}