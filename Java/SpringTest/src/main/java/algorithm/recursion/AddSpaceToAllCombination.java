package algorithm.recursion;

import java.util.ArrayList;
import java.util.HashSet;

/*
 * [1] printComb(abc, a , 1)
 * [1] printComb(abc, ab , 2)
 * [1] printComb(abc, abc , 3) -> abc
 * 		[2] printComb(abc, ab_c, 3) -> ab_c
 * 			[2] printComb(abc, a_b , 2)
 * 				[1] printComb(abc, a_bc , 3) -> a_bc
 * 				[2] printComb(abc, a_b_c , 3) -> a_b_c
 */
public class AddSpaceToAllCombination {

	private void printComb(String str) {

		if (str == null || str.isEmpty())
			return;

		printComb(str, str.substring(0, 1), 1);
	}

	private void printComb(String str, String part, int idx) {

		if (str.length() == idx)
			System.out.println(part);
		else{
			printComb(str, part + str.charAt(idx), idx + 1);
			printComb(str, part + " " + str.charAt(idx), idx + 1);
		}
	}
	
	public static void main(String[] args) {
		AddSpaceToAllCombination obj = new AddSpaceToAllCombination();

		System.out.println("----------------------------------");
		obj.printComb("ABCD");

	}
}
