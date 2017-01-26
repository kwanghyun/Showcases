package algorithm.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/*
 * Given a non-negative number represented as an array of digits,
 * 
 * add 1 to the number ( increment the number represented by the digits ).
 * 
 * The digits are stored such that the most significant digit is at the head
 * of the list.
 * 
 * Example:
 * 
 * If the vector has [1, 2, 3]
 * 
 * the returned vector should be [1, 2, 4]
 * 
 * as 123 + 1 = 124.
 * 
 * NOTE: Certain things are intentionally left unclear in this question
 * which you should practice asking the interviewer
 * 
 * Q : Can the input have 0’s before the most significant digit. Or in other
 * words, is 0 1 2 3 a valid input? 
 * A : For the purpose of this question, YES 
 * 
 * Q : Can the output have 0’s before the most significant digit? Or in
 * other words, is 0 1 2 4 a valid output? 
 * A : For the purpose of this question, NO. Even if the input has zeroes before the most significant
 * digit.
 */
public class AddOne {
	public static ArrayList<Integer> plusOne(ArrayList<Integer> a) {
	    
	    if(a == null || a.size() ==0) return a;
	    ArrayList<Integer> result = new ArrayList();
	    
	    int add = 1;
	    int num = 0;
	    int headingZeroCount = 0;

	    for(int i = a.size() - 1; i >=0 ; i--){
	        num = a.get(i) + add;
	        if(num == 10){
	            num = 0;
	            add = 1;
	        }else{
	            add = 0;
	        }
	        a.set(i, num);
	    }
	    
	    if( add == 1){
	        result.add(1);
	    }else{
	        for(int i=0; i< a.size(); i++){
    	        if(a.get(i) == 0)
    	            headingZeroCount ++;
    	        else
    	            break;
    	    } 
    	}
	    
	    for(int i =headingZeroCount; i < a.size(); i ++){
	            result.add(a.get(i));
	    }
	    
	    return result;
	}
	
	public static void main(String[] args) {
		
		ArrayList<Integer> list = (ArrayList<Integer>) Arrays.asList(0, 3, 7, 6, 4, 0, 5, 5, 5);
		System.out.println(plusOne(list));
	}
}
