package algorithm.dynamic;

import java.util.HashMap;
import java.util.Map;

/*	Let us assume that we have a special keyboard with only the following four keys:  
Key 1:  Prints 'A' on screen  
Key 2: (Ctrl-A): Selects screen  
Key 3: (Ctrl-C): Copy selection to buffer  
Key 4: (Ctrl-V): Prints buffer on screen appending it after what has already been printed.  
If you can only press this keyboard for N times, write a program which computes maximum 
numbers of As possible. That is input for the program is N and output of the program is 
number indicating maximum As possible.

Example -   
Input:  N = 3  
Output: 3  
Maximum number of As possible with 3 keystrokes is 3 which are obtained by pressing following key sequence - 
A, A, A   

Input:  N = 7  
Output: 9  
Maximum number of As possible with 7 keystrokes is 9 which are obtained by pressing following key sequence -  
A, A, A, Ctrl A, Ctrl C, Ctrl V, Ctrl V  
  
Input:  N = 11  
Output: 27  
Maximum number of As possible with 11 keystrokes is 27 which are obtained by pressing following key sequence -  
A, A, A, Ctrl A, Ctrl C, Ctrl V, Ctrl V, Ctrl A, Ctrl C, Ctrl V, Ctrl V

f(N) = N if N < 7 = max{2*f(N-3), 3*f(N-4),..., (N-2)*f(1)}
*/
public class MaximumAs {
	public int findMaxAsI(int n, Map<Integer, Integer> map) {
		if (n < 4)
			return n;

		if (map.containsKey(n))
			return map.get(n);

		int max = 0;

		for (int i = 4; i <= n; i++) {
			max = Math.max(max, findMaxAsI(n - i + 1, map) * (i - 2));
		}
		// Compare with only use A key
		max = Math.max(n, max);

		map.put(n, max);
		return max;
	}

	public static void main(String[] args) throws java.lang.Exception {

		MaximumAs ob = new MaximumAs();
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		int keys = 10;
		System.out.println("Max number of As possible with " + keys + " keystrokes: " + ob.findMaxAsI(keys, map));

	}
}
