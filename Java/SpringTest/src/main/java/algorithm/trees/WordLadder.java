package algorithm.trees;

import java.util.HashSet;

//Given two words (start and end), and a dictionary, find the length of shortest transformation sequence from start to end, such that:
//
//Only one letter can be changed at a time
//Each intermediate word must exist in the dictionary
//For example,
//
//Given:
//start = "hit"
//end = "cog"
//dict = ["hot","dot","dog","lot","log"]
//As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
//return its length 5.
//
//Note:
//Return 0 if there is no such transformation sequence.
//All words have the same length.
//All words contain only lowercase alphabetic characters.

public class WordLadder {
	public int ladderLength(String start, String end, HashSet<String> dict) {

		int len = 0;
		HashSet<String> visited = new HashSet<String>();

		for (int i = 0; i < start.length(); i++) {
			char[] startArr = start.toCharArray();

			for (char c = 'a'; c <= 'z'; c++) {
				if (c == start.toCharArray()[i]) {
					// Not execute the bellow code and back to loop when 'c' is in
					// the 'start' String.
					continue; 
				}

				startArr[i] = c;
				String temp = new String(startArr);
				if (dict.contains(temp)) {
					len++;
					start = temp;
					if (temp.equals(end)) {
						return len;
					}
				}
			}
		}
		return len;
	}
	
	public static void main(String[] args) {

//		String start = "hot";
//		String end = "cog";
//		HashSet<String> dict = new HashSet<String>();
//		dict.add("hot");
//		dict.add("dot");
//		dict.add("dog");
//		dict.add("lot");
//		dict.add("log");
//		dict.add("cog");
//		
//		WordLadder wl = new WordLadder();
//		
//		System.out.println(wl.ladderLength(start, end, dict));

		for(int i=0; i < 5; i++){
			if(i ==2){
				System.out.println("Before Continue - " + i);
				continue;
			}
			System.out.println("After Continue - " + i);
		}
	}
}
