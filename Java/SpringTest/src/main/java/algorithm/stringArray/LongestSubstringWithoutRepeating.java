package algorithm.stringArray;

import java.util.HashMap;
import java.util.Set;

import scala.collection.mutable.HashSet;

/*
 * Given a string, find the length of the longest substring without
 * repeating characters. For example, the longest substring without
 * repeating letters for "abcabcbb" is "abc", which the length is 3. For
 * "bbbbb" the longest substring is "b", with the length of 1.
 * 
 * Java Solution 2
 * 
 * The basic idea is using a hash table to track existing characters and
 * their position. When a repeated character occurs, check from the
 * previously repeated character. However, the time complexity is higher -
 * O(n).
 */
public class LongestSubstringWithoutRepeating {

	public static int lengthOfLongestSubstring(String s) {
		if (s == null)
			return 0;
		char[] arr = s.toCharArray();
		int max = 0;

		HashMap<Character, Integer> map = new HashMap<>();

		for (int i = 0; i < arr.length; i++) {
			// System.out.println("[0] i = " + i);
			if (!map.containsKey(arr[i])) {
				map.put(arr[i], i);
			} else {
				max = Math.max(max, map.size());
				i = map.get(arr[i]);
				System.out.println("[1] i = " + i + ", ch = " + arr[i]);
				map.clear();
			}
		}

		return Math.max(max, map.size());
	}

	/* Java Solution 3 */
	public int lengthOfLongestSubstringI(String s) {
		if (s == null) {
			return 0;
		}

		int max = 0;

		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		int start = 0;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (map.containsKey(c)) {
				max = Math.max(max, map.size());
				while (map.containsKey(c)) {
					map.remove(s.charAt(start));
					start++;
				}

				map.put(c, i);
			} else {
				map.put(c, i);
			}
		}

		max = Math.max(max, map.size());

		return max;
	}

	public static void main(String[] args) {
		System.out.println(lengthOfLongestSubstring("abcabcbb"));
	}
}
