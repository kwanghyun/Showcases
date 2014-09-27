package algorithm.stringArray;

//Assume you have a method isSubstring which checks if one word is a substring of another. 
//Given two strings, s1 and s2, write code to check if s2 is a rotation of s1 using only one call 
//to isSubstring (i.e., ¡°waterbottle¡± is a rotation of ¡°erbottlewat¡±).

public class CheckRotated {

	public boolean solution(String str1, String str2) {
		if (str1 == null || str2 == null)
			return false;
		if (str1.length() != str2.length())
			return false;
		boolean found = false;
		int index = 0;
		for (int i = 0; i < str2.length(); i++) {
			if (str1.charAt(0) == str2.charAt(i)) {
				index = i;
				found = true;
			}
		}

		if (found) {
			String before = str2.substring(0, index);
			String after = str2.substring(index, str2.length());
//			System.out.println(after + before  );

			if (str1.equals(after.concat(before)))
				return true;
		}
		return false;
	}

	public static void main(String args[]) {
		CheckRotated cr = new CheckRotated();
		System.out.println(cr.solution("waterbottle", "erbottlewat"));
		System.out.println(cr.solution("waterbottle", "erbottlewae"));
		System.out.println(cr.solution("waterbottle", "erbottlewa"));
		System.out.println(cr.solution("waterbottle", "waterbottle"));
	}
}
