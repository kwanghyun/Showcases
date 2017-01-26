package algorithm.etc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import akka.util.Collections;

/*
 * Given a sequence of words, print all anagrams together
 * 
 * Given an array of words, print all anagrams together. For example, if the
 * given array is {“cat”, “dog”, “tac”, “god”, “act”}, then output may be
 * “cat tac act dog god”.
 */
public class GroupAnagrams {

	public List<List<String>> groupAnagrams(String[] strs) {
		List<List<String>> result = new ArrayList<>();
		if (strs == null || strs.length == 0)
			return null;

		char[][] sorted = new char[strs.length][];

		for (int i = 0; i < strs.length; i++) {
			char[] temp = strs[i].toCharArray();
			Arrays.sort(temp);
			sorted[i] = temp;
		}

		HashMap<String, Integer> map = new HashMap<>();
		for (int i = 0; i < sorted.length; i++) {
			String key = new String(sorted[i]);
			if (!map.containsKey(key)) {
				map.put(key, result.size());
				List<String> list = new ArrayList<>();
				list.add(strs[i]);
				result.add(list);
			} else {
				int idx = map.get(key);
				result.get(idx).add(strs[i]);
			}

		}

		return result;
	}

	public static void main(String[] args) {
		GroupAnagrams ob = new GroupAnagrams();
		String arr[] = { "cat", "dog", "tac", "god", "act" };
		System.out.println(ob.groupAnagrams(arr));
	}
}
