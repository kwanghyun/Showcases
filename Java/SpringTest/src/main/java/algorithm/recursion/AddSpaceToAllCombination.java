package algorithm.recursion;

import java.util.ArrayList;
import java.util.HashSet;

import algorithm.Utils;

/*
 * [1] printComb(abc, a , 1)
 * [1] printComb(abc, ab , 2)
 * [1] printComb(abc, abc , 3) -> abc
 * 		[2] printComb(abc, ab_c, 3) -> ab_c
 * 			[2] printComb(abc, a_b , 2)
 * 				[1] printComb(abc, a_bc , 3) -> a_bc
 * 				[2] printComb(abc, a_b_c , 3) -> a_b_c
 */
public class AddSpaceToAllCombination {

	private void printComb(String str) {

		if (str == null || str.isEmpty())
			return;

		printComb(str, str.substring(0, 1), 1);
	}

	private void printComb(String str, String part, int idx) {

		if (str.length() == idx)
			System.out.println(part);
		else {
			printComb(str, part + str.charAt(idx), idx + 1);
			printComb(str, part + " " + str.charAt(idx), idx + 1);
		}
	}

	private void printCombCS(String str, String part, int idx, int callstack) {

		if (str.length() == idx) {
			Utils.printCS(callstack, "[DONE] part = " + part);
		} else {
			Utils.printCS(callstack, "part = " + part + ", idx = " + idx);
			printCombCS(str, part + str.charAt(idx), idx + 1, callstack + 1);
			Utils.printCS(callstack, "part = " + part + ", idx = " + idx);
			printCombCS(str, part + "_" + str.charAt(idx), idx + 1, callstack + 1);
			Utils.printCS(callstack, "part = " + part + ", idx = " + idx);
		}
		Utils.printCsEOF(callstack);
	}

	private void printCombI(String str, String part, int idx) {

		if (str.length() == idx) {
			System.out.println(part);
			return;
		}
		part = part + str.charAt(idx);
		printCombI(str, part, idx + 1);
		part = part.substring(0, part.length() - 1);
		part = part + "_" + str.charAt(idx);
		printCombI(str, part, idx + 1);
		part = part.substring(0, part.length() - 1);
	}

	public static void main(String[] args) {
		AddSpaceToAllCombination obj = new AddSpaceToAllCombination();

		System.out.println("--------------------printComb------------------");
		obj.printComb("ABC");
		System.out.println("--------------------printCombI------------------");
		obj.printCombI("ABC", "A", 1);
		System.out.println("--------------------printCombCS------------------");
		obj.printCombCS("ABC", "A", 1, 0);

	}
}
