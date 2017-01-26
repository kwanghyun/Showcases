package algorithm.stringArray;

import java.util.*;

/*
 * An abbreviation of a word follows the form <first letter><number><last
 * letter>. Below are some examples of word abbreviations:
 * 
 * a) it --> it (no abbreviation)
 * 
	a) it                      --> it    (no abbreviation)
	
	     1
	b) d|o|g                   --> d1g
	
	              1    1  1
	     1---5----0----5--8
	c) i|nternationalizatio|n  --> i18n
	
	              1
	     1---5----0
	d) l|ocalizatio|n          --> l10n
 * Assume you have a dictionary and given a word, find whether its
 * abbreviation is unique in the dictionary. A word's abbreviation is unique
 * if no other word from the dictionary has the same abbreviation.
 */
public class ValidWordAbbr {
	HashMap<String, Set<String>> map = new HashMap<>();

	public ValidWordAbbr(String[] dictionary) {
		for (String str : dictionary) {
			String key = convertToAbbr(str);
			Set<String> list = map.getOrDefault(key, new HashSet<>());
			list.add(str);
			map.put(key, list);
		}
		System.out.println("map = " + map);
	}

	public String convertToAbbr(String word) {
		String result;
		if (word.length() > 2) {
			result = "" + word.charAt(0) + (word.length() - 2) + word.charAt(word.length() - 1);
		} else {
			result = word;
		}
		return result;
	}

	public boolean isUnique(String word) {
		String key = convertToAbbr(word);
		if (map.containsKey(key)) {
			Set<String> list = map.get(key);
			if (list.size() >= 2)
				return false;
			return list.contains(word);
		}
		return true;
	}

	public static void main(String[] args) {

		// String[] arr = { "hello" };
		String[] arr = { "deer", "door", "cake", "card" };
		ValidWordAbbr ob = new ValidWordAbbr(arr);
		// System.out.println(ob.isUnique("hello"));
		System.out.println(ob.isUnique("dear"));
		System.out.println(ob.isUnique("door"));
		System.out.println(ob.isUnique("cart"));
		System.out.println(ob.isUnique("cake"));
	}
}
