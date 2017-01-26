package algorithm.stringArray;

import java.util.*;

/*
 * Given a pattern and a string str, find if str follows the same pattern.
 * 
 * Here follow means a full match, such that there is a bijection between a
 * letter in pattern and a non-empty word in str.
 * 
 * Examples: pattern = "abba", str = "dog cat cat dog" should return true.
 * pattern = "abba", str = "dog cat cat fish" should return false.
 * pattern = "aaaa", str = "dog cat cat dog" should return false.
 * pattern = "abba", str = "dog dog dog dog" should return false.
 * 
 * Notes: You may assume pattern contains only lowercase letters, and str
 * contains lowercase letters separated by a sin gle space.
 */
public class WordPattern {
	public boolean wordPattern(String pattern, String str) {
		if (pattern == null || str == null)
			return false;
		if (pattern.length() == 0 && str.length() == 0)
			return true;

		Map<Character, String> chToStrMap = new HashMap<>();
		Map<String, Character> strTochMap = new HashMap<>();
		String[] strArr = str.split(" ");
		if (strArr.length != pattern.length())
			return false;

		for (int i = 0; i < pattern.length(); i++) {
			char ch = pattern.charAt(i);
			String s = strArr[i];
			if (chToStrMap.containsKey(ch)) {
				if (!chToStrMap.get(ch).equals(s))
					return false;
			} else if (strTochMap.containsKey(s)) {
				if (strTochMap.get(s) != ch)
					return false;
			} else {
				chToStrMap.put(ch, s);
				strTochMap.put(s, ch);
			}
		}
		return true;
	}

	public static void main(String[] args) {
		WordPattern ob = new WordPattern();
		System.out.println(ob.wordPattern("abba", "dog cat cat dog"));
		System.out.println(ob.wordPattern("abba", "dog dog dog dog"));
		System.out.println(ob.wordPattern("abba", "dog cat cat fish"));
	}

}
