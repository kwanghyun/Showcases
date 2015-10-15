package algorithm.recursion;

import java.util.LinkedList;

public class RecursionList {

//	public ArrayList<Character> recursiveTest(String str) {
//		ArrayList<Character> result = new ArrayList<Character>();
//		return recursiveTest(str, result, 0);
//	}

	public LinkedList<Character> recursiveTest(String str, LinkedList<Character> list, int idx) {
		if (idx == str.length())
			return list;

//		LinkedList<Character> list = new LinkedList<Character>();
		list.add(str.charAt(idx));
		recursiveTest(str, list, idx + 1);
		list.remove();
		return list;
	}

	public static void main(String[] args) {
		RecursionList obj = new RecursionList();
		System.out.println(obj.recursiveTest("ABCD", new LinkedList<Character>(), 0));
		
	}
}
