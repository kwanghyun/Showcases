package algorithm.stringArray;

import java.util.ArrayList;

public class ReverseStringR {

	public String reverseStrI(String str) {
		if (str == null || str.length() == 0)
			return "";

		StringBuilder sb = new StringBuilder();
		sb.append(reverseStrI(str.substring(1)) + str.charAt(0));

		return sb.toString();
	}

	public String reverseStr(String str, int idx) {
		if (idx == str.length())
			return "";

		StringBuilder sb = new StringBuilder();
		sb.append(reverseStr(str, idx + 1) + str.charAt(idx));

		return sb.toString();
	}

	public void printAll(String s1, String s2, int idx1, int idx2, int idx3, ArrayList<Character> list) {

		if (idx3 == s1.length() + s2.length()) {
			System.out.println(list);
			return;
		}
		if (idx1 < s1.length()) {
			list.add(s1.charAt(idx1));
			printAll(s1, s2, idx1 + 1, idx2, idx3 + 1, list);
			list.remove(list.size() - 1);
		}
		if (idx2 < s2.length()) {
			list.add(s2.charAt(idx2));
			printAll(s1, s2, idx1, idx2 + 1, idx3 + 1, list);
			list.remove(list.size() - 1);
		}
	}

	public static void main(String[] args) {
		ReverseStringR ob = new ReverseStringR();
		System.out.println("------- reverseStr() -------");
		System.out.println(ob.reverseStr("ABC", 0));
		System.out.println("------- reverseStrI() -------");
		System.out.println(ob.reverseStrI("ABC"));
		ArrayList<Character> list = new ArrayList<>();
		System.out.println("------- printAll() -------");
		ob.printAll("abc", "de", 0, 0, 0, list);

	}
}
