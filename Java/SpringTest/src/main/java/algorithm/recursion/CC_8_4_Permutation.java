package algorithm.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CC_8_4_Permutation {

	public ArrayList<String> permutation(String str) {
		ArrayList<String> permutations = new ArrayList<String>();
		if (str == null)
			return null;

		if (str.length() == 0) {
			//without this size program doesn't work...
			permutations.add(" ");
			return permutations;
		}

		char first = str.charAt(0);
		String remainder = str.substring(1);

		ArrayList<String> candidates = permutation(remainder);

		for (String candidate : candidates) {
			
			for (int i = 0; i < candidate.length(); i++) {
				String before = candidate.substring(0, i);
				String end = candidate.substring(i, str.length());
				permutations.add(new String(before + first + end));
			}
		}
		return permutations;
	}
	
	public static void PrintPermuation(char[] inputs, int currentFocus) {
		// before start, check if currentFocus come to the last char
		if (currentFocus == inputs.length - 1) {
			System.out.println(new String(inputs));
			return;
		}

		// now first keep the current char order in the array and proceed to
		// next
		PrintPermuation(inputs, currentFocus + 1);

		// new need swap each next char with currentFocus
		for (int i = currentFocus + 1; i < inputs.length; i++) {
			// swap the char pair of position(currentFocus, i)
			char temp = inputs[currentFocus];
			inputs[currentFocus] = inputs[i];
			inputs[i] = temp;

			PrintPermuation(inputs, currentFocus + 1);
		}
	}
	

	public static void main(String[] args) {
		
//		by adding " ",  array 0 max, size 0
		List<String> list1= new ArrayList<String>();
		list1.add("");
		
//		by adding " ", initializing array 10 max, size 1
		List<String> list2= new ArrayList<String>();
		list2.add(" ");

		CC_8_4_Permutation p = new CC_8_4_Permutation();
		ArrayList<String> list = p.permutation("1234");
		
//		ArrayList<String> list = getPerms("1234");
//		PrintPermuation("1234".toCharArray(), 0);
		
		System.out.println("COUNT : " + list.size());
		System.out.println("===================");
		for(String str : list)
			System.out.println("String : [" + str + "] " + "Length : [" + str.length() + "]");
	}
}
