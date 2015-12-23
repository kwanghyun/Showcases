package algorithm.dynamic;

import java.util.HashMap;
import java.util.Map;

/*F(n) = F(n-1) + F(n-2) and F(1) = F(2) = 1
This means that the sequence of the first 10 fibonacci numbers would go:
1, 1, 2, 3, 5, 8, 13, 21, 34, 55
You might also find it defined as:
F(0) = F(1) = 1
And so the sequence would be:
0,1, 1, 2, 3, 5, 8, 13, 21, 34, 55*/
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
			return curr;
		}
	}

	Map<Integer, Integer> past_fib = new HashMap<Integer, Integer>();

	int dynamicFibonacci(int num) {
		// Return nth fibonacci number memorizing past solutions"

		int total = 0;

		if (past_fib.containsKey(num))
			return past_fib.get(num);

		if (num <= 2) {
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
		System.out.println("recursive answer => " + fi.fibonacci(num));
		long now = System.currentTimeMillis();
		System.out.println("recursive performance :: " + (now - start));
		
		start = System.currentTimeMillis();
		System.out.println("for loop answer => " + fi.ifib(num));
		now = System.currentTimeMillis();
		System.out.println("for loop performance :: " + (now - start));

		start = System.currentTimeMillis();
		System.out.println("dynamic answer => " + fi.dynamicFibonacci(num));
		now = System.currentTimeMillis();
		System.out.println("dynamic performance:: " + (now - start));

	}

}
