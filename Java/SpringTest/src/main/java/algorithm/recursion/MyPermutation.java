package algorithm.recursion;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * Let¡¯s assume a given string S represented by the letters A1, A2, A3, ... , An
 * To permute set S, we can select the first character, A1, permute the remainder 
 * of the string to get a new list. Then, with that new list, we can ¡°push¡± A1 into 
 * each possible position.
 */

/*
 * 1. Let first = ¡°a¡± and let remainder = ¡°bc¡±
 * 2. Let list = permute(bc) = {¡°bc¡±, ¡°cd¡±}
 * 3. Push ¡°a¡± into each location of ¡°bc¡± (--> ¡°abc¡±, ¡°bac¡±, ¡°bca¡±) and ¡°cb¡± (--> ¡°acb¡±, ¡°cab¡±, ¡°cba¡±)
 * 4. Return our new list
 */
public class MyPermutation {

	public static ArrayList<String> getPerm(String str){
		ArrayList<String> permList = new ArrayList<String>();
		
		if(str == null) return null;
		if(str.length() == 0 ){
			permList.add(" ");
			return permList;
		}
		
		char first = str.charAt(0);
		String remainder = str.substring(1);

		ArrayList<String> candidateList = getPerm(remainder);
		
		for(String candidate : candidateList){
			for(int i = 0; i<candidate.length(); i++){
				String start = candidate.substring(0, i);
				String end = candidate.substring(i);
				String s = new String(start + first + end);
//				System.out.println(s);
				permList.add(s);
			}			
		}
		return permList;
	}

}
