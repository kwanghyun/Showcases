package algorithm.stringArray;

import java.util.*;

/*
 * Given a non-empty string str and an integer k, rearrange the string such
 * that the same characters are at least distance k from each other.
 * 
 * All input strings are given in lowercase letters. If it is not possible
 * to rearrange the string, return an empty string "".
 * 
 * Example 1:
 * str = "aabbcc", k = 3
 * Result: "abcabc"
 * 
 * The same letters are at least distance 3 from each other.
 * 
 * Example 2:
 * str = "aaabc", k = 3
 * Answer: ""
 * 
 * It is not possible to rearrange the string.
 * 
 * Example 3:
 * str = "aaadbbcc", k = 2
 * Answer: "abacabcd"
 * 
 * Another possible answer is: "abcabcda"
 * 
 * The same letters are at least distance 2 from each other.
 */
public class RearrangeStringKDistanceApart {
	public String rearrangeString(String str, int k) {

		Map<Character, Integer> map = new HashMap<>();
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			int count = map.getOrDefault(ch, 0);
			map.put(ch, count + 1);
		}

		if (map.size() < k)
			return "";

		StringBuilder result = new StringBuilder();
//		while (map.size() >= k) {
			for (char ch : map.keySet()) {
				int count = map.get(ch);
				if (count > 1) {
					result.append(ch);
					map.put(ch, count - 1);
				} else {
					result.append(ch);
//					map.remove(ch);
				}

			}
//		}

		return result.toString();
	}

	public static void main(String[] args) {
		RearrangeStringKDistanceApart ob = new RearrangeStringKDistanceApart();
		System.out.println(ob.rearrangeString("aaabc", 3));
	}
}
