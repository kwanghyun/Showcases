package algorithm.etc;

import java.util.ArrayList;
import java.util.List;

public class FizzBuzz {
	public List<String> solution(final int n) {
	    
		final List<String> result = new ArrayList<String>(n);
	    
	    for (int i = 1; i <= n; i++) {
	        if(i % 3  == 0 && i % 5 == 0) {
	            result.add("FizzBuzz");
	        } else if (i % 3 == 0) {
	            result.add("Fizz");
	        } else if (i % 5 == 0) {
	            result.add("Buzz");
	        } else {
	            result.add(Integer.toString(i));
	        }
	    }

	    return result;
	}
	
	public static void main(String[] args) {
		FizzBuzz obj = new  FizzBuzz();
		System.out.println(obj.solution(15));
	}
}
