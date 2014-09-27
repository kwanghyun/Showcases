package algorithm.stackNQueue;

import java.util.Stack;

//Implement a MyQueue class which implements a queue using two stacks.
public class MyQueueDoubleStacks {

	//Two stack for one queue or stack + stack?
	//TODO implement stack + stack one too.
	
	Stack<Integer> stack = new Stack<Integer>();
	Stack<Integer> reverse = new Stack<Integer>();
	
	public void enqueue(Integer num){
		stack.push(num);
		reverse.add(0, num);
	}
	
	public Integer dequeue(){
		if(stack.empty()) 
			return null;
		stack.remove(0);
		return reverse.pop();
	}

	public void printAll(){
		System.out.println("\n-----------STACK-------------");
		for(int i = 0 ; i < stack.size()   ; i++){
			System.out.print(stack.get(i));	
		}
		System.out.println("\n------------REVERSE------------");
		for(int i = 0 ; i < stack.size()   ; i++){
			System.out.print(reverse.get(i));	
		}
	}
	
	public static void main(String args[]){
		MyQueueDoubleStacks queue = new MyQueueDoubleStacks();
		for(int i = 1; i <6; i++){
			queue.enqueue(i);	
		}
		queue.printAll();

		for(int i = 0; i <6; i++){
			queue.dequeue();	
		}
		queue.printAll();

	}
	
	public void StackTest(){
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(1);
		stack.push(2);
		stack.push(3);
		
		Stack<Integer> stack2 = new Stack<Integer>();

		stack2.push(2);
		stack2.push(1);
		stack2.add(0, stack.peek());
		
		stack2.remove(0);
		
		for(int i = 0 ; i < stack.size() -1  ; i++){
			System.out.print(stack2.get(i));	
		}		
	}
}
