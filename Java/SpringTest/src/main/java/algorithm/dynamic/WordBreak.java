package algorithm.dynamic;

import java.util.HashSet;
import java.util.Set;

/*Given a string s and a dictionary of words dict, determine if s can be segmented into
 a space-separated sequence of one or more dictionary words. For example, given s =
 "leetcode", dict = ["leet", "code"]. Return true because "leetcode" can be segmented as
 "leet code".

 This problem can be solve by using a naive approach, which is trivial. A discussion
 can always start from that though.
 */
public class WordBreak {
	public boolean wordBreak(String string, HashSet<String> dict) {
		// return wordBreakHelper(string , dict, 0);
		return wordBreak(string, dict, 0);
	}

	public boolean wordBreak(String str, HashSet<String> dict, int start) {
		if (start == str.length())
			return true;
		String part = "";
		for (int i = 0; i < str.length(); i++) {
			part = str.substring(0, i);
			if (dict.contains(part)) {
				if (wordBreak(part, dict, i))
					return true;
			}
		}
		return false;
	}

	public boolean wordBreakHelper(String string, HashSet<String> dict, int start) {
		if (start == string.length())
			return true;

		for (String item : dict) {
			int len = item.length();
			int end = start + len;

			// end index should be <= string length
			if (end > string.length())
				continue;

			if (string.substring(start, start + len).equals(item))
				if (wordBreakHelper(string, dict, start + len))
					return true;
		}
		return false;
	}


	/*
	 * Dynamic Programming The key to solve this problem by using dynamic
	 * programming approach: • Define an array t[] such that t[i]==true
	 * =>0-(i-1) can be segmented using dictionary • Initial state t[0] == true
	 */

	public boolean wordBreakD(String inputStr, Set<String> dict) {
		boolean[] t = new boolean[inputStr.length() + 1];
		t[0] = true; // set first to be true, why?

		// Because we need initial state
		for (int i = 0; i < inputStr.length(); i++) {
			// should continue from match position
			if (!t[i])
				continue;

			for (String word : dict) {
				int len = word.length();
				int end = i + len;

				if (end > inputStr.length())
					continue;
				if (t[end])
					continue;
				if (inputStr.substring(i, end).equals(word)) {
					t[end] = true;
				}
			}
		}
		return t[inputStr.length()];
	}

	/*
	 * Time: O(string length * dict size) One tricky part of this solution is
	 * the case: INPUT: "programcreek", ["programcree","program","creek"]. We
	 * should get all possible matches, not stop at "programcree".
	 */
	public static void main(String[] args) {
		HashSet<String> dict = new HashSet<String>();
		dict.add("leet");
		dict.add("code");
		WordBreak obj = new WordBreak();
		System.out.println(obj.wordBreak("leetcode", dict));
	}
}
