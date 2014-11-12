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

public int pow2(int x, int y){
	return powRe( x,  y, 0);
}

public int powRe(int x, int y, int counter){
	if(counter == y)
		return 1;
	
	return x * powRe(x, y, counter+1);
}
	
	public static void main(String args[]){
		Power pw = new Power();
		System.out.println(pw.pow(2,4));
		System.out.println(pw.pow(2,8));
		System.out.println(pw.pow2(2,4));
		System.out.println(pw.pow2(2,8));
	}
}
