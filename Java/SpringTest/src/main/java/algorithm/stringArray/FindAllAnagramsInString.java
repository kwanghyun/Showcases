package algorithm.stringArray;

import java.util.*;

/*
 * Given a string s and a non-empty string p, find all the start indices of
 * p's anagrams in s.
 * 
 * Strings consists of lowercase English letters only and the length of both
 * strings s and p will not be larger than 20,100.
 * 
 * The order of output does not matter.
 * 
 *  Example 1:
 * 
 * Input: s: "cbaebabacd" p: "abc"
 * 
 * Output: [0, 6]
 * 
 * Explanation: The substring with start index = 0 is "cba", which is an
 * anagram of "abc". The substring with start index = 6 is "bac", which is
 * an anagram of "abc".
 * 
 * 
 * Example 2:
 * 
 * Input: s: "abab" p: "ab"
 * 
 * Output: [0, 1, 2]
 * 
 * Explanation: The substring with start index = 0 is "ab", which is an
 * anagram of "ab". The substring with start index = 1 is "ba", which is an
 * anagram of "ab". The substring with start index = 2 is "ab", which is an
 * anagram of "ab".
 */

public class FindAllAnagramsInString {
	public List<Integer> findAnagrams(String s, String p) {
		List<Integer> result = new ArrayList<>();

		if (p.length() > s.length())
			return result;

		Map<Character, Integer> target = new HashMap<>();
		for (int i = 0; i < p.length(); i++) {
			char ch = p.charAt(i);
			int count = target.getOrDefault(ch, 0);
			target.put(ch, count + 1);
		}

		Map<Character, Integer> map = new HashMap<>();
		for (int i = 0; i < s.length(); i++) {

			char ch = s.charAt(i);
			int count = map.getOrDefault(ch, 0);
			map.put(ch, count + 1);
			System.out.println("map = " + map);
			int start = i - p.length();

			if (i > p.length() - 1) {
				System.out.println("i = " + i + ", s.charAt(start) = " + start);
				char sch = s.charAt(i - p.length());
				int cnt = map.get(sch);
				if (cnt == 1)
					map.remove(sch);
				else
					map.put(sch, cnt - 1);
			}

			if (map.size() == target.size()) {
				boolean isIdentical = true;
				for (char c : map.keySet()) {
					if (!target.containsKey(c) && map.get(c) != target.get(c)) {
						isIdentical = false;
					}
				}
				if (isIdentical)
					result.add(i - p.length() + 1);
			}
		}
		return result;
	}

	public static void main(String[] args) {
		FindAllAnagramsInString ob = new FindAllAnagramsInString();

		String s = "aaaaaaaaaaaaaaaaaabbbbabbbbbab";
		String p = "ab";

		System.out.println(ob.findAnagrams(s, p));
	}
}
