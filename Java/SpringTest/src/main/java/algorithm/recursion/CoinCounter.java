package algorithm.recursion;

import java.util.ArrayList;
import java.util.List;

/*
 * Given an infinite number of quarters (25 cents), dimes (10 cents), nickels (5 cents) 
 * and pennies (1 cent), write code to calculate the number of ways of representing n cents.
 */
public class CoinCounter {

	public static List<Integer> countCoin(int amount) {	
		int[] coins = { 25, 10, 5, 1 };
		ArrayList<Integer> returnArr = new ArrayList<Integer>();

		int leftover = amount;
		for (int i = 0; i < coins.length - 1; i++) {
			if ((leftover / coins[i]) > 0) {
				int d = leftover / coins[i];
				returnArr.add(d);
			} else {
				returnArr.add(0);
			}
			leftover = leftover % coins[i];
		}
		returnArr.add(leftover);

		return returnArr;
	}
	
	//TODO do with recursive
	
	public static void main(String args[]){
		List<Integer> list = countCoin(1);
		for(int i : list)
			System.out.println(i);
	}
}
