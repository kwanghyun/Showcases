package algorithm.stringArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/*
 * You are given a string, s, and a list of words, words, that are all of
 * the same length. Find all starting indices of substring(s) in s that is a
 * concatenation of each word in words exactly once and without any
 * intervening characters.
 * 
 * For example, given: s: "barfoothefoobarman" words: ["foo", "bar"]
 * 
 * You should return the indices: [0,9]. (order does not matter).
 */

public class SubstringWithConcatenationOfAllWords {
	public List<Integer> findSubstring(String s, String[] words) {
		List<Integer> result = new ArrayList<>();
		HashMap<String, Integer> wordMap = new HashMap<>();
		HashMap<String, Integer> seen = new HashMap<>();

		int wordLen = words[0].length();
		int matchCount = 0;

		// Word Frequency
		for (String word : words) {
			int count = wordMap.getOrDefault(word, 0);
			wordMap.put(word, count + 1);
		}

		int startIdx = 0;
		for (int i = 0; i <= s.length() - wordLen; i = i + wordLen) {
			String curStr = s.substring(i, i + wordLen);

			if (wordMap.containsKey(curStr)) {

				int count = seen.getOrDefault(curStr, 0);
				if (count < wordMap.get(curStr)) {
					matchCount++;
				}
				seen.put(curStr, count + 1);

				System.out.println("matchCount = " + matchCount);
				System.out.println(seen);

				while (matchCount == words.length) {
					String startStr = s.substring(startIdx, startIdx + wordLen);
					result.add(startIdx);
					if (seen.containsKey(startStr)) {
						int cnt = seen.getOrDefault(startStr, 0);
						if (cnt <= wordMap.get(startStr)) {
							matchCount--;
						}

						if (cnt == 1) {
							seen.remove(startStr);
						} else {
							seen.put(startStr, cnt - 1);
						}
					}
					startIdx += wordLen;
				}

			} else {
				seen.clear();
				matchCount = 0;
				startIdx += wordLen;
			}

		}
		return result;
	}

	public static void main(String[] args) {
		SubstringWithConcatenationOfAllWords ob = new SubstringWithConcatenationOfAllWords();
		String s = "barfoofoobarthefoobarman";
		String[] words = { "foo", "bar", "the" };
		System.out.println(ob.findSubstring(s, words));

		System.out.println(
				"[[2147483647,-1,0,2147483647],[2147483647,2147483647,2147483647,-1],[2147483647,-1,2147483647,-1],[0,-1,2147483647,2147483647]]"
						.replace("[", "{").replace("]", "}"));
	}
}
