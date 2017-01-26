package algorithm.bits;

/*
 * Given a positive integer n and you can do operations as follow:
 * 
 * If n is even, replace n with n/2. If n is odd, you can replace n with
 * either n + 1 or n - 1. What is the minimum number of replacements needed
 * for n to become 1?
 * 
 * Example 1:
 * 
 * Input: 8
 * Output: 3
 * 
 * Explanation: 
 * 8 -> 4 -> 2 -> 1 
 * 
 * Example 2:
 * 
 * Input: 7
 * Output: 4
 * 
 * Explanation: 
 * 7 -> 8 -> 4 -> 2 -> 1 
 * or  
 * 7 -> 6 -> 3 -> 2 -> 1
 */
public class IntegerReplacement {
	int min = Integer.MAX_VALUE;

	public int integerReplacement(int n) {
		integerReplacement(n, 0);
		return min;
	}

	public void integerReplacement(int n, int count) {
		if (n == 1) {
			min = Math.min(min, count);
			return;
		}
		
		if(n == 0 || n == 2){
			min = Math.min(min, count + 1);
			return;
		}
		
		System.out.println(n);

		if (n != 0 && n % 2 == 0) {
			integerReplacement(n / 2, count + 1);
		} else {
			integerReplacement(n - 1, count + 1);
			integerReplacement(n + 1, count + 1);
		}
	}

	public static void main(String[] args) {
		IntegerReplacement ob = new IntegerReplacement();
		System.out.println("RESULT : " + ob.integerReplacement(111111111));
	}

}
