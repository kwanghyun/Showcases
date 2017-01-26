package algorithm.stringArray;

import java.util.HashMap;
import java.util.Map.Entry;

/*
 * Find longest palindrome formed by removing or shuffling chars from string
 * Given a string, find the longest palindrome that can be constructed by
 * removing or shuffling characters from the string. Return only one
 * palindrome if there are multiple palindrome strings of longest length.
 * 
 * Examples:
 * 
 * Input: abc 
 * Output: a OR b OR c
 * 
 * Input: aabbcc 
 * Output: abccba OR baccab OR cbaabc OR any other palindromic string of length 6.
 * 
 * Input: abbaccd 
 * Output: abcdcba OR ...
 * 
 * Input: aba 
 * Output: aba
 */
public class LongestPalindromeByRemovingOrShuffling {

    public int longestPalindrome(String s) {
		if (s == null || s.length() == 1)
			return s.length();

		HashMap<Character, Integer> map = new HashMap<>();
		for (int i = 0; i < s.length(); i++) {
			map.compute(s.charAt(i), (k, v) -> {
				if (v == null)
					return 1;
				return v + 1;
			});
		}

		System.out.println(map);

		StringBuilder mid = new StringBuilder();
		StringBuilder head = new StringBuilder();
		StringBuilder tail = new StringBuilder();
		for (char ch : map.keySet()) {
			while (map.get(ch) >= 2) {
				map.put(ch, map.get(ch) - 2);
				head.append(ch);
				tail.insert(0, ch);
			}
			if (mid.length() == 0 && map.get(ch) == 1)
				mid.append(ch);
		}

		return head.append(mid.append(tail)).toString().length();        
    }
    
	public String findLongestPalindome(String str) {
		if (str == null || str.length() == 1)
			return str;

		HashMap<Character, Integer> map = new HashMap<>();
		for (int i = 0; i < str.length(); i++) {
			map.compute(str.charAt(i), (k, v) -> {
				if (v == null)
					return 1;
				return v + 1;
			});
		}

		System.out.println(map);

		StringBuilder mid = new StringBuilder();
		StringBuilder head = new StringBuilder();
		StringBuilder tail = new StringBuilder();
		for (char ch : map.keySet()) {
			while (map.get(ch) >= 2) {
				map.put(ch, map.get(ch) - 2);
				head.append(ch);
				tail.insert(0, ch);
			}
			if (mid.length() == 0 && map.get(ch) == 1)
				mid.append(ch);
		}

		return head.append(mid.append(tail)).toString();
	}

	public static void main(String[] args) {
		LongestPalindromeByRemovingOrShuffling ob = new LongestPalindromeByRemovingOrShuffling();
		String testString1 = "abc";
		System.out.println(ob.findLongestPalindome(testString1));
		String testString2 = "aabbcc";
		System.out.println(ob.findLongestPalindome(testString2));
		String testString3 = "abbaccd";
		System.out.println(ob.findLongestPalindome(testString3));
		String testString4 = "aba";
		System.out.println(ob.findLongestPalindome(testString4));
	}
}
