package algorithm.trees;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/*Given two words (start and end), and a dictionary, find the length of shortest transformation sequence from start to end, such that:

 Only one letter can be changed at a time
 Each intermediate word must exist in the dictionary
 For example,

 Given:
 start = "hit"
 end = "cog"
 dict = ["hot","dot","dog","lot","log"]
 As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
 return its length 5.

 Note:
 Return 0 if there is no such transformation sequence.
 All words have the same length.
 All words contain only lowercase alphabetic characters.
 */
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
//				System.out.println("word : " + temp);
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
	
	/***
	 * So we quickly realize that this looks like a tree searching problem for which breath
	 * 	first guarantees the optimal solution.
	 * 	Assuming we have some words in the dictionary, and the start is "hit" as shown in
	 * 	the diagram below.
	 * 	                             Start : hit
	 * 	                          /        |       \
	 * 	                        /          |          \
	*                   hot        hot       hot
	*                   /\
	*                ...  ...
	* 
	* We can use two queues to traverse the tree, one stores the nodes, the other stores 
	* the step numbers.
	* 
	* What can be learned from this problem?
	* • Use breath-first or depth-first search to solve problems
	* • Use two queues, one for words and another for counting
 	***/
	
public int ladderLength2(String start, String end, HashSet<String> dict) {
	if (dict.size() == 0)
		return 0;
	
	dict.add(end);
	Queue<String> wordQueue = new LinkedList<>();
	Queue<Integer> distanceQueue = new LinkedList<>();
	
	wordQueue.add(start);
	distanceQueue.add(1);
	
	// track the shortest path
	int ladderLen = Integer.MAX_VALUE;
	
	//This while loop keep hold all matched element and try to 
	while (!wordQueue.isEmpty()) {
		String currWord = wordQueue.remove();
		Integer currDistance = distanceQueue.remove();
	
		if (currWord.equals(end)) {
			ladderLen = Math.min(ladderLen, currDistance);
		}
		
		for (int i = 0; i < currWord.length(); i++) {
			char[] currCharArr = currWord.toCharArray();
		
			for (char c = 'a'; c <= 'z'; c++) {
				currCharArr[i] = c;
				String newWord = new String(currCharArr);
//				System.out.println("word : " + newWord);
				if (dict.contains(newWord)) {
					wordQueue.add(newWord);
					distanceQueue.add(currDistance + 1);
					dict.remove(newWord);
				}
			}
		}
	}
	
	if (ladderLen < Integer.MAX_VALUE)
		return ladderLen;
	else
		return 0;
}		
	
	public static void main(String[] args) {

		String start = "hit";
		String end = "cog";
		HashSet<String> dict = new HashSet<String>();
		dict.add("hot");
		dict.add("dot");
		dict.add("dog");
		dict.add("lot");
		dict.add("log");
		dict.add("cog");
//		
		WordLadder wl = new WordLadder();
//		
		System.out.println(wl.ladderLength2(start, end, dict));

//		for(int i=0; i < 5; i++){
//			if(i ==2){
//				System.out.println("Before Continue - " + i);
//				continue;
//			}
//			System.out.println("After Continue - " + i);
//		}
		
		LinkedList<Integer> q = new LinkedList<Integer>();
		q.add(1);
		q.add(2);
		q.add(3);
//		System.out.println(q.remove());
		System.out.println(q.pop());
	}
}
