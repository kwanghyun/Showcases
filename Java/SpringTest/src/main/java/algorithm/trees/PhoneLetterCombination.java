package algorithm.trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Problem :
//Given a digit string, return all possible letter combinations that the number could represent. 
//(Check out your cellphone to see the mappings) 
//Input:Digit string "23", Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
// Analysis : 
//This problem can be solves by a typical DFS algorithm. DFS problems are very 
//similar and can be solved by using a simple recursion. 

public class PhoneLetterCombination {
	public List<String> letterCombinations(String digits) {

		ArrayList<String> result = new ArrayList<>();
		if (digits == null || digits.length() == 0)
			return result;

		ArrayList<Character> temp = new ArrayList<>();
		getString(digits, temp, result, getKeyMap());

		return result;
	}

	public void getString(String digits, ArrayList<Character> temp, ArrayList<String> result,
			HashMap<Integer, String> keyPadMap) {

		if (digits.length() == 0) {
			char[] arr = new char[temp.size()];
			for (int i = 0; i < temp.size(); i++) {
				arr[i] = temp.get(i);
			}
			result.add(String.valueOf(arr));
			return;
		}

		Integer curr = Integer.valueOf(digits.substring(0, 1));
		String letters = keyPadMap.get(curr);

		for (int i = 0; i < letters.length(); i++) {
			temp.add(letters.charAt(i));
			getString(digits.substring(1), temp, result, keyPadMap);
			temp.remove(temp.size() - 1);
		}
	}

	public List<String> letterCombinations2(String digits) {

		ArrayList<String> result = new ArrayList<>();
		if (digits == null || digits.length() == 0)
			return result;

		String temp = "";
		getString2(digits, temp, result, getKeyMap());

		return result;
	}

	public void getString2(String digits, String str, ArrayList<String> result, HashMap<Integer, String> keyPadMap) {

		if (digits.length() == 0) {
			result.add(str);
			return;
		}

		// charAt() returns ASCII code i.e. 50 for "2"
		Integer curr = Integer.valueOf(digits.substring(0, 1));
		String letters = keyPadMap.get(curr);

		for (int i = 0; i < letters.length(); i++) {
			str = str + letters.charAt(i);
			getString2(digits.substring(1), str, result, keyPadMap);
			str = str.substring(0, str.length() - 1);
		}
	}

	private HashMap<Integer, String> getKeyMap() {
		HashMap<Integer, String> keyPadMap = new HashMap<>();
		keyPadMap.put(2, "abc");
		keyPadMap.put(3, "def");
		keyPadMap.put(4, "ghi");
		keyPadMap.put(5, "jkl");
		keyPadMap.put(6, "mno");
		keyPadMap.put(7, "pqrs");
		keyPadMap.put(8, "tuv");
		keyPadMap.put(9, "wxyz");
		keyPadMap.put(0, "");
		return keyPadMap;
	}

	public static void main(String[] args) {
		PhoneLetterCombination obj = new PhoneLetterCombination();
		List<String> result = obj.letterCombinations("234");
		System.out.println("size : " + result.size());
		System.out.println(result);

		List<String> result2 = obj.letterCombinations2("234");
		System.out.println("size : " + result2.size());
		System.out.println(result2);
	}
}
