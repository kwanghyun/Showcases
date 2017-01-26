package algorithm.recursion;

import java.util.ArrayList;
import java.util.List;

import algorithm.Utils;

/*
 * Recursively print all sentences that can be formed from list of word
 * lists Given a list of word lists How to print all sentences possible
 * taking one word from a list at a time via recursion?
Example:

Input: {{"you", "we"},
        {"have", "are"},
        {"sleep", "eat", "drink"}}

Output:
  you have sleep
  you have eat
  you have drink
  you are sleep
  you are eat
  you are drink
  we have sleep
  we have eat
  we have drink
  we are sleep
  we are eat
  we are drink 
  */
public class PrintAllSentencesFromWordList {

	public void solution(String[][] input) {
		if (input == null || input.length == 0)
			return;

		List<String> list = new ArrayList<>();
		printAll(input, list, 0);
	}

	public void printAll(String[][] input, List<String> list, int row) {
		if (list.size() == 3) {
			System.out.println(list);
			return;
		}

		for (int i = 0; i < input[row].length; i++) {
			list.add(input[row][i]);
			printAll(input, list, row + 1);
			list.remove(list.size() - 1);
		}
	}

	public void printAllCS(String[][] input, List<String> list, int row, int col, int callstack) {
		if (list.size() == 3) {
			System.out.println(list);
			return;
		}

		for (int i = 0; i < input[row].length; i++) {
			list.add(input[row][i]);
			Utils.printCS(callstack, "(+) row = " + row + ", i = " + i + ", list = " + list);
			printAllCS(input, list, row + 1, i, callstack + 1);
			list.remove(list.size() - 1);
			Utils.printCS(callstack, "(-) row = " + row + ", i = " + i + ", list = " + list);
		}
		Utils.printCsEOL(callstack);
	}

	public static void main(String[] args) {
		PrintAllSentencesFromWordList ob = new PrintAllSentencesFromWordList();
		String[][] input = { { "you", "we" }, { "have", "are" }, { "sleep", "eat", "drink" } };
		ob.solution(input);
		ArrayList<String> list = new ArrayList<>();
		ob.printAllCS(input, list, 0, 0, 0);
	}
}
