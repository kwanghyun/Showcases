package algorithm.stringArray;

//Assume you have a method isSubstring which checks if one word is a substring of another. 
//Given two strings, s1 and s2, write code to check if s2 is a rotation of s1 using only one call 
//to isSubstring (i.e., "waterbottle" is a rotation of "erbottlewat").

public class IsRotatedString {

	public boolean solution(String str1, String str2) {
		if (str1 == null || str2 == null)
			return false;
		if (str1.length() != str2.length())
			return false;

		char firstChar = str1.charAt(0);
		int idx = 0;

		while (str1.indexOf(firstChar, idx) > -1) {
			for (int i = 0; i < str2.length(); i++) {
				if (firstChar == str2.charAt(i)) {

					String before = str2.substring(0, i);
					String after = str2.substring(i, str2.length());
					System.out.println(after + before);

					if (str1.equals(after.concat(before)))
						return true;
				}
			}
			idx++;
		}
		return false;
	}

	public boolean solution2(String str1, String str2) {
		if (str1 == null || str2 == null)
			return false;
		if (str1.length() != str2.length())
			return false;

		for (int i = 0; i < str1.length(); i++) {
			if (str1.charAt(0) == str2.charAt(i)) {
				for (int j = 0; j < str1.length(); j++) {
					char str1Ch = str1.charAt(j);
					char str2Ch = str2.charAt((i + j) % str1.length());
					if (str1Ch != str2Ch) {
						break;
					} else if (j == str1.length() - 1) {
						return true;
					}
				}

			}
		}
		return false;
	}

	public static void main(String[] args) {
		IsRotatedString obj = new IsRotatedString();
		obj.solution2("waterbottle", "erbottlewat");
	}

}
