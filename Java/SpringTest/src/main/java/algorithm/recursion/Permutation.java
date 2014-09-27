package algorithm.recursion;

import java.util.ArrayList;
import java.util.Arrays;

public class Permutation {

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

	public static ArrayList<String> getPerms(String s) {
		ArrayList<String> permutations = new ArrayList<String>();
		if (s == null) { // error case
			return null;
		} else if (s.length() == 0) { // base case
			permutations.add("");
			return permutations;
		}
		char first = s.charAt(0); // get the first character
		String remainder = s.substring(1); // remove the first character
		
		ArrayList<String> words = getPerms(remainder);
		for (String word : words) {
			System.out.println("1.###-------#####word : " + word);
			for (int j = 0; j <= word.length(); j++) {
				permutations.add(insertCharAt(word, first, j));
				System.out.println(Arrays.toString(permutations.toArray()));
			}
		}
		return permutations;
	}

	public static String insertCharAt(String word, char c, int i) {

		String start = word.substring(0, i);
		String end = word.substring(i);
//		System.out.println("word : " + word);
		System.out.println("start : "+ start + ", c : " + c +", end : " + end);
		System.out.println("");
		return start + c + end;
	}

	public static void main(String[] args) {
		ArrayList<String> list = getPerms("1234");
//		PrintPermuation("1234".toCharArray(), 0);
		System.out.println("===================");
//		for(String str : list)
//			System.out.println(str);
	}
}
