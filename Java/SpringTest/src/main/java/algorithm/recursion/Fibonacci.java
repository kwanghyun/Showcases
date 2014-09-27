package algorithm.recursion;

public class Fibonacci {
	
	int fibonatti(int num){
		if(num == 0) return 0;
		if(num == 1) return 1;
		
		return fibonatti(num-1) + fibonatti(num-2);
	}
	
	int fibIter(int n){
		int returnValue = 0;
		
		while(n > 2){
			returnValue += (n -1) + (n-2);
			n--;
		}
		return returnValue;
	}
	
	public static void main(String args[]){
		Fibonacci fi = new Fibonacci();
		System.out.println(fi.fibonatti(7));
		System.out.println(fi.fibIter(5));
	}

}
