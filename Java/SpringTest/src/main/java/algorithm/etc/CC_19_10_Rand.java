package algorithm.etc;

import java.util.Random;

/*
 * Write a method to generate a random number between 1 and 7, 
 * given a method that generates a random number between 1 and 5 
 * (i.e., implement rand7() using rand5()).
 */
public class CC_19_10_Rand {

	public int getRandomValue(){
		Random generator = new Random();
		return 1 + (int) (generator.nextInt(5) * 7 / 5);
	}
	
	public static void main(String args[]){
		CC_19_10_Rand rd = new CC_19_10_Rand();
		for(int i= 0; i < 100; i++){
			System.out.println(rd.getRandomValue());
		}
	}
}
