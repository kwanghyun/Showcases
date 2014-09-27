package algorithm.stringArray;

public class ReverseWrodsInAString {
	public String reverseWords(String orginStr) {

		if (orginStr == null || orginStr.length() <= 0)
			throw new IllegalArgumentException();

		// Q1. Only space is sole delimiter?
		// What about double space, comma etc.
		String[] strArr = orginStr.split(" ");
		StringBuilder sb = new StringBuilder();
		for (int i = strArr.length -1; i >= 0; i--) {
			if (strArr[i ] != "") {
				sb.append(strArr[i ]).append(" ");
			}
		}
		return sb.toString().length() == 0 ? 
				"" : sb.toString().substring(0, sb.length() - 1);
	}

	public static void main(String args[]) {
		ReverseWrodsInAString rws = new ReverseWrodsInAString();
		System.out.println("Result : " + rws.reverseWords("the sky is blue"));
	}
}
