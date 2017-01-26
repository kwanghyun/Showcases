package algorithm.stringArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/*
 * Given a string, we can "shift" each of its letter to its successive
 * letter, for example: "abc" -> "bcd". We can keep "shifting" which forms
 * the sequence:
 * 
 * "abc" -> "bcd" -> ... -> "xyz"
 * 
 * Given a list of strings which contains only lowercase alphabets, group
 * all strings that belong to the same shifting sequence.
 * 
 * For example, given: ["abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"],
 * 
	A solution is:
	
	[
	  ["abc","bcd","xyz"],
	  ["az","ba"],
	  ["acef"],
	  ["a","z"]
	]
 */
public class GroupShiftedStrings {
	public List<List<String>> groupStrings(String[] strings) {
		List<List<String>> result = new ArrayList<>();

		if (strings == null || strings.length == 0) {
			return result;
		}

		HashMap<String, Integer> map = new HashMap<>();
		for (int i = 0; i < strings.length; i++) {
			String str = strings[i];
			int prev = str.charAt(0) - 'a';
			StringBuilder pattern = new StringBuilder();
			for (int c = 1; c < str.length(); c++) {
				int curr = str.charAt(c) - 'a';
				int left = 0;
				if (prev > curr) {
					left = (curr + 26) - prev;
				} else {
					left = curr - prev;
				}
				pattern.append(left + "-");
			}
			// Single character case
			if (pattern.length() == 0)
				pattern.append("0");

			String patt = pattern.toString();
			System.out.println("str= " + str + ", patt = " + patt);
			if (!map.containsKey(patt)) {
				map.put(patt, result.size());
				List<String> newList = new ArrayList<>();
				newList.add(str);
				result.add(newList);
			} else {
				int idx = map.get(patt);
				result.get(idx).add(str);
			}
		}
		return result;
	}

	public static void main(String[] args) {

		GroupShiftedStrings ob = new GroupShiftedStrings();
		// String[] strs = { "abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"
		// };
		String[] strs = { "abc", "am" };
		System.out.println(ob.groupStrings(strs));
	}
}
