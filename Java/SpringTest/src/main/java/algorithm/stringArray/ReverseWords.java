package algorithm.stringArray;

/*Given an input string, reverse the string word by word.
 For example, given s = "the sky is blue", return "blue is sky the".

 Note: String-
 Builder should be used to avoid creating too many Strings. If the string is very long,
 using String is not scalable since String is immutable and too many objects will be
 created and garbage collected.

 */
public class ReverseWords {
	public static String reverseWords(String input) {
		if (input == null || input.length() == 0) {
			return "";
		}
		
		// split to words by space
		String[] arr = input.split(" ");
		StringBuilder sb = new StringBuilder();
		
		for (int i = arr.length - 1; i >= 0; --i) {
			if (!arr[i].equals("")) {
				sb.append(arr[i]).append(" ");
			}
		}
		return sb.length() == 0 ? "" : sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(reverseWords("the sky is blue"));
	}
}
