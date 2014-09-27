package algorithm.stackNQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/*
 * Imagine a (literal) stack of plates. If the stack gets too high, it might topple. Therefore, 
 * in real life, we would likely start a new stack when the previous stack exceeds some threshold. 
 * Implement a data structure SetOfStacks that mimics this. SetOfStacks should be composed 
 * of several stacks, and should create a new stack once the previous one exceeds capacity. 
 *  SetOfStacks.push() and SetOfStacks.pop() should behave identically to a single stack 
 *  (that is, pop() should return the same values as it would if there were just a single stack).
 *  
 *  FOLLOW UP
 *  Implement a function popAt(int index) which performs a pop operation on a specific sub-stack.
 *  
 *  Mistake Node :
 *  1. Initial push, pop handling
 *  2. When pop,  
 */

//TODO FOLLOW UP popAt() 

public class MultiStackManager {
	private class Dish {
		public int num;
		public Dish(int i) {
			num = i;
		}
	}

	List<Stack<Dish>> stacks = new ArrayList<Stack<Dish>>();
	private static final int THREASHOLD = 5;

	public void push(Dish d) {
		if(stacks.isEmpty()){
			Stack<Dish> stack = new Stack<Dish>();
			stack.add(d);
			stacks.add(stack);
			return;
		}
		
		Stack<Dish> stack = stacks.get(stacks.size() - 1);
		if (stack.size() == THREASHOLD) {
			stack = new Stack<Dish>();
			stack.add(d);
			stacks.add(stack);
		} else {
			stack.push(d);
		}
	}

	public Dish pop() {
		if(stacks.isEmpty()){
			return null;
		}
		
		Stack<Dish> stack = stacks.get(stacks.size() - 1);
		if (stack.empty()) {
			if(stacks.size() < 2)
				return null;
			stack = stacks.get(stacks.size() - 2);
		}
		return stack.pop();
	}
	
	public static void main(String args[]){
		MultiStackManager msm = new MultiStackManager();
		for(int i = 0; i <48; i++){
			Dish dish = msm.new Dish(i);
			msm.push(dish);
		}
		
		for (Stack s :msm.stacks){
			for(int i = 0; i<s.size(); i ++){
				Dish d = (Dish) s.get(i);
				System.out.print(d.num);
			}
			System.out.println("\n__________");
		}
		
		for(int i = 0; i <8; i++){
			System.out.println(msm.pop().num);
		}
		
		for (Stack s :msm.stacks){
			for(int i = 0; i<s.size(); i ++){
				Dish d = (Dish) s.get(i);
				System.out.print(d.num);
			}
			System.out.println("\n__________");
		}
		
	}
}
