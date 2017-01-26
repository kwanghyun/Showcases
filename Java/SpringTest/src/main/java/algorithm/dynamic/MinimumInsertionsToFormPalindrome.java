package algorithm.dynamic;

/*
 * Given a string, find the minimum number of characters to be inserted to
 * convert it to palindrome.
 * 
 * Before we go further, let us understand with few examples: ab: Number of
 * insertions required is 1. bab
 * aa: Number of insertions required is 0. aa
 * abcd: Number of insertions required is 3. dcbabcd
 * abcda: Number of insertions required is 2. adcbcda which is same as
 * number of insertions in the substring bcd(Why?).
 * abcde: Number of insertions required is 4. edcbabcde
 */
public class MinimumInsertionsToFormPalindrome {
	public int findMinInsertions(String str, int start, int end) {
		int result = 0;
		if (start > end)
			return 100;
		if (start == end)
			return 0;

		int diff = str.charAt(start) == str.charAt(end) ? 0 : 1;

		if (str.charAt(start) == str.charAt(end)) {
			result = diff + findMinInsertions(str, start + 1, end - 1);
		} else {
			result = diff
					+ Math.min(findMinInsertions(str, start + 1, end - 1), findMinInsertions(str, start, end - 1));
		}
		return result;
	}

	public static void main(String[] args) {
		MinimumInsertionsToFormPalindrome ob = new MinimumInsertionsToFormPalindrome();
		String testString = "abc";
		System.out.println(ob.findMinInsertions(testString, 0, testString.length() - 1));
	}
}
