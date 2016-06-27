package algorithm.dynamic;

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
A, A, A, Ctrl A, Ctrl C, Ctrl V, Ctrl V, Ctrl A, Ctrl C, Ctrl V, Ctrl V*/
public class MaximumNs {
	// assuming max input for 'n' won't be greater than 10.
	// you might want to change it according to your need.
	private static int MAX = 10;

	/*
	 * f(N) = N if N < 7 = max{2*f(N-3), 3*f(N-4),..., (N-2)*f(1)}
	 */
	public static int findMaxAs(int n, int[] dp) {
		// base case
		if (n < 7)
			return n;

		int max = 0, tempMax = 0, multiplier = 2;

		// choose the critical point which produces maximum number of As
		for (int i = n - 3; i >= 0; i--) {
			// make recursive call if required
			if (dp[i] == -1) {
				dp[i] = findMaxAs(i, dp);
			}

			tempMax = multiplier * dp[i];

			if (tempMax > max) {
				max = tempMax;
			}
			multiplier += 1;
		}
		return max;
	}

	/*
	 * Order of the Algorithm
	 * 
	 * Time Complexity is O(n^2) Space Complexity is O(n)
	 */
	public static void main(String[] args) throws java.lang.Exception {
		// assuming input n won't be greater than 10.
		int[] maxAsSolution = new int[MAX];
		int[] maxAsSolutionI = new int[MAX];
		for (int i = 0; i < maxAsSolution.length; i++) {
			// maxAsSolution[i] = -1 indicates that solution for this input =
			// 'i' is not computed yet.
			maxAsSolution[i] = -1;
			maxAsSolutionI[i] = -1;
		}

		// find max number of As with 8 keystrokes allowed.
		System.out.println("Max number of As possible with 8 keystrokes: " + findMaxAs(8, maxAsSolution));
	}
}
