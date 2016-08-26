package algorithm.stringArray;

import java.util.HashMap;

/*
 * Given a string, find the longest substring that contains only two unique
 * characters. For example, given "abcbbbbcccbdddadacb", the longest
 * substring that contains 2 unique character is "bcbbbbcccb".
 */
public class LongestSubStringWithKuniqueChar {

	// 1. Longest Substring Which Contains 2 Unique Characters
	public int lengthOfLongestSubstringTwoDistinct(String s) {
		int max = 0;
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		int start = 0;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (map.containsKey(c)) {
				map.put(c, map.get(c) + 1);
			} else {
				map.put(c, 1);
			}

			if (map.size() > 2) {
				max = Math.max(max, i - start);

				while (map.size() > 2) {
					char t = s.charAt(start);
					int count = map.get(t);
					if (count > 1) {
						map.put(t, count - 1);
					} else {
						map.remove(t);
					}
					start++;
				}
			}
		}

		max = Math.max(max, s.length() - start);

		return max;
	}

	public int lengthOfLongestSubstringTwoDistinctI(String s) {
		int max = 0;
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		int start = 0;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (map.containsKey(c)) {
				map.put(c, map.get(c) + 1);
			} else {
				map.put(c, 1);
			}

			while (map.size() > 2) {
				char t = s.charAt(start);
				int count = map.get(t);
				if (count > 1) {
					map.put(t, count - 1);
				} else {
					map.remove(t);
				}
				start++;
			}

			max = Math.max(max, i - start + 1);
		}

		return max;
	}

	// 2. Solution for K Unique Characters
	public int lengthOfLongestSubstringKDistinct(String s, int k) {
		if (k == 0 || s == null || s.length() == 0)
			return 0;

		if (s.length() < k)
			return s.length();

		HashMap<Character, Integer> map = new HashMap<Character, Integer>();

		int maxLen = k;
		int left = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (map.containsKey(c)) {
				map.put(c, map.get(c) + 1);
			} else {
				map.put(c, 1);
			}

			if (map.size() > k) {
				maxLen = Math.max(maxLen, i - left);

				while (map.size() > k) {

					char fc = s.charAt(left);
					if (map.get(fc) == 1) {
						map.remove(fc);
					} else {
						map.put(fc, map.get(fc) - 1);
					}

					left++;
				}
			}

		}

		maxLen = Math.max(maxLen, s.length() - left);

		return maxLen;
	}

	public static void main(String[] args) {
//		String target = "abcbbbbcccbdddadacb";
//		String target = "abcbb";
		String target = "abcbbbbcccbdddadcccbdddadacbcccbdddada";
		LongestSubStringWithKuniqueChar ob = new LongestSubStringWithKuniqueChar();
		System.out.println(ob.lengthOfLongestSubstringTwoDistinct(target));
		System.out.println(ob.lengthOfLongestSubstringTwoDistinctI(target));
	}
}
