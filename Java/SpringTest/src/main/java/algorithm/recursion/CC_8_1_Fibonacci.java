package algorithm.recursion;

public class CC_8_1_Fibonacci {
	
	int fibonatti(int num){
		if(num == 0) return 0;
		if(num == 1) return 1;
		
		return fibonatti(num-1) + fibonatti(num-2);
	}
	
	public int ifib(int n) {
		
        if ((n == 1) || (n == 2)) {
            return 1;
        } else {
            int prev = 1, current = 1, next = 0;
            for (int  i = 3; i <= n; i++) {
                next = prev + current;
                prev = current;
                current = next;
            }
            return next;
        }
	}
	
	public int fib(int num){
		if(num==1) return 1;
		if(num ==0) return 0;
		
		return fib(num - 1) + fib(num-2);
	}
	
	public int fib2(int num){
		if(num == 0)
			return 0;
		
		int returnValue = 0; 
		while(num > 1 ){
			returnValue += (num-1) + (num-2);
			num--;
		}
		
		return returnValue + 1;
	}
	
	
	public static void main(String args[]){
		CC_8_1_Fibonacci fi = new CC_8_1_Fibonacci();
		int num = 10;
//		System.out.println(fi.fibonatti(num));
//		System.out.println(fi.ifib(num));
//		System.out.println(fi.fib(num));
//		System.out.println(fi.fib2(num));

	}

}
