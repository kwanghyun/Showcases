package algorithm.recursion;

import java.util.ArrayList;
import java.util.HashSet;

public class AddSpaceToAllCombination {

	public HashSet<String> permute(String str) {
		HashSet<String> resultList = new HashSet<String>();
		if (str == null)
			return null;
		if (str.length() == 0) {
			resultList.add(" ");
			return resultList;
		}

		String space = " ";
		char frist = str.charAt(0);
		String leftover = str.substring(1);
		HashSet<String> cadindates = permute(leftover);

		for (String elem : cadindates) {
			for (int i = 0; i < elem.length(); i++) {

				String head = elem.substring(0, i);
				String tail = elem.substring(i, elem.length());

				resultList.add(new String(frist + head + space + tail));
			}
		}
		return resultList;
	}

	private void printComb(String str) {

		if (str == null || str.isEmpty())
			return;

		printComb(str, str.substring(0, 1), 1);
	}

	/*
	 * [1] printComb(abc, a , 1)
	 * [1] printComb(abc, ab , 2)
	 * [1] printComb(abc, abc , 3) -> abc
	 * 		[2] printComb(abc, ab_c, 3) -> ab_c
	 * 			[2] printComb(abc, a_b , 2)
	 * 				[1] printComb(abc, a_bc , 3) -> a_bc
	 * 				[2] printComb(abc, a_b_c , 3) -> a_b_c
	 */
	private void printComb(String str, String part, int idx) {

		if (str.length() == idx)
			System.out.println(part);

		else if (idx != 0) {
			printComb(str, part + str.charAt(idx), idx + 1);
			printComb(str, part + " " + str.charAt(idx), idx + 1);
		}
	}
	
	public void print(String str, int index, String type) {
		
		if (index == str.length())
			System.out.println("---------------------- type -> " + type + ", index -> " + index);
		else {
			System.out.println("type -> " + type + ", index -> " + index);
			print(str, index + 1, "A");
			print(str, index + 1, "B");
		}
	}

	public static void main(String[] args) {
		AddSpaceToAllCombination obj = new AddSpaceToAllCombination();

		// obj.test("ABC", "ABC".length());

		HashSet<String> result = obj.permute("ABC");
		//
		for (String elem : result) {
			System.out.println("## => " + elem);
		}

		System.out.println("----------------------------------");
		obj.printComb("ABCD");
		System.out.println("----------------------------------");
		obj.print("ABC", 0, "Main");

	}
}
