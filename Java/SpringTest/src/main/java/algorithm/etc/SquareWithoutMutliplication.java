package algorithm.etc;

public class SquareWithoutMutliplication {
	public int square(int n){
		if (n == 0) 
			return 0;
		return square(n, n);
	}
	private int square(int n, int idx){
		if(idx == 0)
			return 0;
		return n + square(n, idx -1);
 	}
	
	public static void main(String[] args) {
		SquareWithoutMutliplication obj = new SquareWithoutMutliplication();
		System.out.println(obj.square(2));
		System.out.println(obj.square(4));
		System.out.println(obj.square(5));
	}
}
