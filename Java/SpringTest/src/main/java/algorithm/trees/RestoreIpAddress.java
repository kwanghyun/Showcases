package algorithm.trees;

import java.util.ArrayList;
import java.util.List;

/*
 * Given a string containing only digits, restore it by returning all
 * possible valid IP address combinations.
 * 
 * For example: given "25525511135",return ["255.255.11.135",
 * "255.255.111.35"].
 * 
 * Java Solution
 * 
 * This is a typical search problem and it can be solved by using DFS.
 */
public class RestoreIpAddress {

	public List<String> restoreIpAddresses(String s) {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		ArrayList<String> t = new ArrayList<String>();
		dfs(result, s, 0, t);

		ArrayList<String> finalResult = new ArrayList<String>();

		for (ArrayList<String> r : result) {
			StringBuilder sb = new StringBuilder();
			for (String str : r) {
				sb.append(str + ".");
			}
			sb.setLength(sb.length() - 1);
			finalResult.add(sb.toString());
		}

		return finalResult;
	}

	public List<String> restoreIpAddressesI(String s) {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		ArrayList<String> t = new ArrayList<String>();
		dfsI(result, s, t);

		ArrayList<String> finalResult = new ArrayList<String>();

		for (ArrayList<String> r : result) {
			StringBuilder sb = new StringBuilder();
			for (String str : r) {
				sb.append(str + ".");
			}
			sb.setLength(sb.length() - 1);
			finalResult.add(sb.toString());
		}

		return finalResult;
	}

	public void dfsI(ArrayList<ArrayList<String>> result, String str, ArrayList<String> list) {

		if (list.size() > 4)
			return;

		if (list.size() == 4 && str.length() == 0) {
			result.add(new ArrayList<>(list));
			return;
		}

		for (int i = 1; i <= 3; i++) {
			if (i > str.length())
				return;
			String s = str.substring(0, i);
			if (!isValidIp(s))
				return;
			list.add(s);
			dfsI(result, str.substring(i), list);
			list.remove(list.size() - 1);

		}
	}

	private boolean isValidIp(String ip_str) {
		if (ip_str.startsWith("0"))
			return false;

		int ip = Integer.parseInt(ip_str);
		if (ip > 255)
			return false;

		return true;
	}

	public void dfs(ArrayList<ArrayList<String>> result, String s, int start, ArrayList<String> t) {
		// if already get 4 numbers, but s is not consumed, return
		if (t.size() >= 4 && start != s.length())
			return;

		// make sure t's size + remaining string's length >=4
		if ((t.size() + s.length() - start + 1) < 4)
			return;

		// t's size is 4 and no remaining part that is not consumed.
		if (t.size() == 4 && start == s.length()) {
			ArrayList<String> temp = new ArrayList<String>(t);
			result.add(temp);
			return;
		}

		for (int i = 1; i <= 3; i++) {
			// make sure the index is within the boundary
			if (start + i > s.length())
				break;

			String sub = s.substring(start, start + i);
			// handle case like 001. i.e., if length > 1 and first char is 0,
			// ignore the case.
			if (i > 1 && s.charAt(start) == '0') {
				break;
			}

			// make sure each number <= 255
			if (Integer.valueOf(sub) > 255)
				break;

			t.add(sub);
			dfs(result, s, start + i, t);
			t.remove(t.size() - 1);
		}
	}

	public static void main(String[] args) {
		RestoreIpAddress ob = new RestoreIpAddress();
		System.out.println(ob.restoreIpAddresses("25525511135"));
		System.out.println(ob.restoreIpAddressesI("25525511135"));
	}
}
