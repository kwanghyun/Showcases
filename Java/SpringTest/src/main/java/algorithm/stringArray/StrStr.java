package algorithm.stringArray;

public class StrStr {
	
	public int strStr(String haystack, String needle) {
		
		if (haystack == null || needle == null)
			return 0;
		
		if (needle.length() == 0)
			return 0;
		
		for (int i = 0; i < haystack.length(); i++) {
			if (i + needle.length() > haystack.length())
				return -1;
			
			for (int j = 0; j < needle.length(); j++) {
				if (needle.charAt(j) == haystack.charAt(j + i)) {
					if (j == needle.length() - 1)
						return i;
				} else {
					break;
				}
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
		StrStr obj = new StrStr();
		System.out.println(obj.strStr("I'm the algorithm King", "gori"));
	}
}
