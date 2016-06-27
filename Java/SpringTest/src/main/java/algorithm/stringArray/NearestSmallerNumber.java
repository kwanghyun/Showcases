package algorithm.stringArray;

import java.util.Stack;

/*
 * Given an array of integers, find the nearest smaller number for every
 * element such that the smaller element is on left side.
 * 
 * Examples:
 * Input: arr[] = {1, 6, 4, 10, 2, 5}
 * Output: {_, 1, 1, 4, 1, 2}
 * 
 * First element ('1') has no element on left side. For 6,
 * there is only one smaller element on left side '1'.
 * For 10, there are three smaller elements on left side (1,
 * 6 and 4), nearest among the three elements is 4.
 * 
 * Input: arr[] = {1, 3, 0, 2, 5}
 * Output: {_, 1, _, 0, 2}
 * 
 * Expected time complexity is O(n).
 */
public class NearestSmallerNumber {
	// Prints smaller elements on left side of every element
	public void printPrevSmaller(int arr[]) {
		// Create an empty stack
		Stack<Integer> stack = new Stack<>();

		// Traverse all array elements
		for (int i = 0; i < arr.length; i++) {
			// Keep removing top element from S while the top
			// element is greater than or equal to arr[i]
			while (!stack.isEmpty() && stack.peek() >= arr[i])
				stack.pop();

			// If all elements in S were greater than arr[i]
			if (stack.empty())
				System.out.print("_, ");
			else // Else print the nearest smaller element
				System.out.print(stack.peek() + ", ");

			// Push this element
			stack.push(arr[i]);
		}
	}

	/* Driver program to test insertion sort */
	public static void main(String[] args) {
		int arr[] = { 1, 3, 0, 2, 5 };
		NearestSmallerNumber ob = new NearestSmallerNumber();
		ob.printPrevSmaller(arr);
	}
}
