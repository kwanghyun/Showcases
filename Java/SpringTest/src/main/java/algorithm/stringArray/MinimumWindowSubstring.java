package algorithm.stringArray;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * Given a string S and a string T, find the minimum window in S which will
 * contain all the characters in T in complexity O(n).
 * 
 * For example, S = "ADOBECODEBANC", T = "ABC", Minimum window is "BANC".
 */
public class MinimumWindowSubstring {

	public String minWindowII(String s, String t) {

		HashMap<Character, Integer> target = new HashMap<>();
		int targetCount = t.length();
		for (int i = 0; i < t.length(); i++) {
			char ch = t.charAt(i);
			target.compute(ch, (k, v) -> {
				if (v == null)
					return v = 1;
				return v + 1;
			});
		}

		int start = 0;
		int minLen = s.length();
		String minStr = "";
		int hasToFind = 0;

		Map<Character, Integer> map = new HashMap<>();
		for (int i = 0; i < s.length(); i++) {

			char ch = s.charAt(i);

			if (target.containsKey(ch)) {
				if (!map.containsKey(ch)) {
					map.put(ch, 1);
					hasToFind++;
				} else {
					if (target.get(ch) > map.get(ch))
						hasToFind++;
					map.put(ch, map.get(ch) + 1);
				}
			}

			while (hasToFind == targetCount) {
				char sch = s.charAt(start);
				if (i - start + 1 < minLen) {
					minStr = s.substring(start, i + 1);
					minLen = i - start + 1;
				}

				if (map.containsKey(sch)) {
					int count = map.get(sch);
					if (count == 1) {
						map.remove(sch);
						hasToFind--;
					} else {
						if (target.get(sch) >= count)
							hasToFind--;
						map.put(sch, count - 1);
					}
				}
				start++;
			}
		}

		return minStr;
	}

	public String minWindowI(String s, String t) {

		Set<Character> set = new HashSet<>();
		for (int i = 0; i < t.length(); i++) {
			set.add(t.charAt(i));
		}

		int start = 0;
		int minLen = s.length();
		String minStr = "";

		Map<Character, Integer> map = new HashMap<>();
		for (int i = 0; i < s.length(); i++) {

			char ch = s.charAt(i);

			if (set.contains(ch)) {
				map.compute(ch, (k, v) -> {
					if (v == null)
						return 1;
					return v + 1;
				});
			}

			while (map.size() == set.size()) {
				char sch = s.charAt(start);
				if (i - start + 1 < minLen) {
					minStr = s.substring(start, i + 1);
					minLen = i - start + 1;
				}

				if (map.containsKey(sch)) {
					map.compute(sch, (k, v) -> {
						if (v == 1)
							return null;
						return v - 1;
					});
				}
				start++;
			}
		}
		return minStr;
	}

	public String minWindow(String s, String t) {
		if (t.length() > s.length())
			return "";
		String result = "";

		// character counter for t
		HashMap<Character, Integer> target = new HashMap<>();
		for (int i = 0; i < t.length(); i++) {
			char c = t.charAt(i);
			if (target.containsKey(c)) {
				target.put(c, target.get(c) + 1);
			} else {
				target.put(c, 1);
			}
		}

		// character counter for s
		HashMap<Character, Integer> map = new HashMap<>();
		int left = 0;
		int minLen = s.length() + 1;

		int count = 0; // the total of mapped characters

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (target.containsKey(c)) {
				if (map.containsKey(c)) {
					if (map.get(c) < target.get(c)) {
						count++;
					}
					map.put(c, map.get(c) + 1);
				} else {
					map.put(c, 1);
					count++;
				}
			}

			if (count == t.length()) {
				char sc = s.charAt(left);
				while (!map.containsKey(sc) || map.get(sc) > target.get(sc)) {
					if (map.containsKey(sc) && map.get(sc) > target.get(sc))
						map.put(sc, map.get(sc) - 1);
					left++;
					sc = s.charAt(left);
				}

				if (i - left + 1 < minLen) {
					result = s.substring(left, i + 1);
					minLen = i - left + 1;
				}
			}
		}

		return result;
	}

	public static void main(String[] args) {
		String s = "ADOBECODEBANC";
		// String t = "ABC";
		String t = "ABCC";
		// String t = "BB";
		MinimumWindowSubstring ob = new MinimumWindowSubstring();
		System.out.println(ob.minWindowII(s, t));
		System.out.println(ob.minWindowI(s, t));
	}
}
