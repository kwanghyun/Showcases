package algorithm.stringArray;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * Given a list of words and two words word1 and word2, return the shortest
 * distance between these two words in the list.
 * 
 * For example, 
 * Assume that words = ["practice", "makes", "perfect", "coding", "makes"].
 * 
 * Given word1 = “coding”, word2 = “practice”, return 3. 
 * Given word1 = "makes", word2 = "coding", return 1.
 */
public class ShortestDistance {
	public int getShortestDistanceI(String[] words, String s1, String s2) {
		int idx1 = -1;
		int idx2 = -1;
		int result = words.length;
		for (int i = 0; i < words.length; i++) {
			if (words[i].equals(s1)) {
				idx1 = i;
			} else if (words[i].equals(s2)) {
				idx2 = i;
			}
			if (idx1 != -1 && idx2 != -1) {
				result = Math.min(result, Math.abs(idx2 - idx1));
			}
		}
		return result;
	}

	/*
	 * This is a follow up of Shortest Word Distance. The only difference is now
	 * you are given the list of words and your method will be called repeatedly
	 * many times with different parameters. How would you optimize it?
	 */
	public int getShortestDistanceII(String[] words, String word1, String word2) {

		// In Constructor
		HashMap<String, ArrayList<Integer>> map = new HashMap<String, ArrayList<Integer>>();
		for (int i = 0; i < words.length; i++) {
			if (map.containsKey(words[i])) {
				map.get(words[i]).add(i);
			} else {
				ArrayList<Integer> list = new ArrayList<Integer>();
				list.add(i);
				map.put(words[i], list);
			}
		}
		// In Constructor

		ArrayList<Integer> l1 = map.get(word1);
		ArrayList<Integer> l2 = map.get(word2);

		int result = Integer.MAX_VALUE;
		int i = 0;
		int j = 0;
		while (i < l1.size() && j < l2.size()) {
			result = Math.min(result, Math.abs(l1.get(i) - l2.get(j)));
			if (l1.get(i) < l2.get(j)) {
				i++;
			} else {
				j++;
			}
		}

		return result;
	}

	public static void main(String[] args) {
		ShortestDistance ob = new ShortestDistance();
		String[] words = { "practice", "makes", "perfect", "coding", "makes" };

		System.out.println(ob.getShortestDistanceI(words, "coding", "practice"));
		System.out.println(ob.getShortestDistanceI(words, "makes", "coding"));
	}
}
