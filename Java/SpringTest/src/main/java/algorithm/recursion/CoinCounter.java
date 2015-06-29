package algorithm.recursion;

import java.util.ArrayList;
import java.util.List;

/*
 * Given an infinite number of quarters (25 cents), dimes (10 cents), nickels (5 cents) 
 * and pennies (1 cent), write code to calculate the number of ways of representing n cents.
 */


public class CoinCounter {
	
	List<String> possibleList = new ArrayList<String>();
	
	public void countCoins(int amount, int c25, int c10, int c5, int c1){
	
		int total = 25 * c25 + 10 * c10 + 5 * c5 + c1;
	
		if(amount < total ) 
			return;		
		
		if(amount == total){
			if(!possibleList.contains(c25 + ", " + c10 + ", " + c5 + ", " + c1)){
				possibleList.add(c25 + ", " + c10 + ", " + c5 + ", " + c1);
			}
			return;
		}else{
			countCoins(amount, c25+1, c10, c5, c1);
			countCoins(amount, c25, c10+1, c5, c1);
			countCoins(amount, c25, c10, c5+1, c1);
			countCoins(amount, c25, c10, c5, c1+1);
		}
	}
		
	/*
	 * The difference with findAllpossible path is, it meets condition when sum
	 * is the same as amount, fintPath example's condition is each int values
	 * are meet certain count.
	 */
	public static void main(String args[]){
		CoinCounter counter = new CoinCounter();
		counter.countCoins(26, 0, 0, 0, 0);
		int idx = 1;
		for(String entry : counter.possibleList){
			System.out.println(idx + " :: " + entry);
			idx ++;
		}
			
	}
}
