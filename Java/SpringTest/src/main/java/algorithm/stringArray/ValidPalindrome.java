package algorithm.stringArray;

/*Given a string, determine if it is a palindrome, considering only alphanumeric characters
 and ignoring cases.
 For example, "Red rum, sir, is murder" is a palindrome, while "Programcreek is
 awesome" is not.
 Note: Have you consider that the string might be empty? This is a good question to
 ask during an interview.
 For the purpose of this problem, we define empty string as valid palindrome.*/
public class ValidPalindrome {

	public static boolean isValidPalindrome(String s) {
		if (s == null || s.length() == 0)
			return false;
		
		s = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
		System.out.println(s);
		
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != s.charAt(s.length() - 1 - i)) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		String str = "A man, a plan, a canal: Panama";
		System.out.println(isValidPalindrome(str));
	}
}
