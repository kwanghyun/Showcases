package algorithm.etc;

/*
 * Implement pow(x, n).
 * 
 * Mistake Note :
 * 1. you can set counter parameter as "counter++" because it first hand over value and increase value.
 *  	hence, it fall into infinite loop.
 */
public class Power {

	public int pow(int x, int y){
		int sum = 1;
		for(int i =0; i < y; i ++){
			sum *= x;
		}
		return sum;
	}
	
	public int recurPow(int x, int n){
		if(n == 0)
			return 1;
		
		return x * recurPow(x, n-1);
	}
	
	public static void main(String args[]){
		Power pw = new Power();
		System.out.println(pw.pow(2,4));
		System.out.println(pw.pow(2,8));
		System.out.println(pw.recurPow(2,4));
		System.out.println(pw.recurPow(2,8));
	}
}
