package algorithm.stackNQueue;

import java.util.Arrays;
import java.util.Stack;

/*
 * Write a program to sort a stack in ascending order. You should not make 
 * any assumptions about how the stack is implemented. The following are 
 * the only functions that should be used to write this program: push | pop | peek | isEmpty.
 */

/*
 * Questions
 * 1. Ascending order mean, when pop it return ascending? 
 * 2. What is stack's data type?
 * 3. Can I use Array?
 */

public class StackSort {

	// Most simple one pop every elements and insert into array, and execute
	// Arrays.sort();
	public int[] simpleSolution(Stack<Integer> stack) {
		int[] arr = new int[stack.size()];
		for (int i = 0; !stack.isEmpty(); i++) {
			arr[i] = stack.pop();
		}
		Arrays.sort(arr);
		return arr;
	}

	// TODO Using stack (Book)
	public Stack solution(Stack<Integer> stack) {
		Stack<Integer> returnStack = new Stack<Integer>();
		while(!stack.empty()) {
			int origin = stack.pop();
			if (returnStack.isEmpty()) {
				returnStack.push(origin);
			} else {
				while (returnStack.peek() < origin) {					
					stack.push(returnStack.pop());
				}
				returnStack.push(origin);
			}
		}
		return returnStack;
	}

	public static void main(String args[]) {
		StackSort ss = new StackSort();
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(5);
		stack.push(2);
		stack.push(4);
		stack.push(1);
		stack.push(3);
		stack.push(7);
		stack.push(6);
		stack.push(8);
		// System.out.println(Arrays.toString(ss.simpleSolution(stack)));
		System.out.println(Arrays.toString(ss.solution(stack).toArray()));
	}
}
