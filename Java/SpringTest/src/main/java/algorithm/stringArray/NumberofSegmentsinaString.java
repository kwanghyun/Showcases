package algorithm.stringArray;

/*
 * Count the number of segments in a string, where a segment is defined to
 * be a contiguous sequence of non-space characters.
 * 
 * Please note that the string does not contain any non-printable
 * characters.
 * 
 * Example:
 * 
 * Input: "Hello, my name is John" Output: 5
 */

public class NumberofSegmentsinaString {
	public int countSegments(String s) {
		String input = s.trim();

		if (input.length() == 0)
			return 0;

		int count = 0;
		char prev = ' ';
		for (int i = 0; i < input.length(); i++) {
			char ch = input.charAt(i);
			if (ch == ' ' && prev != ' ')
				count++;
			prev = ch;
		}

		return count + 1;
	}

	public static void main(String[] args) {
		NumberofSegmentsinaString ob = new NumberofSegmentsinaString();
		System.out.println(ob.countSegments("Hello, my name is John"));
	}

}
