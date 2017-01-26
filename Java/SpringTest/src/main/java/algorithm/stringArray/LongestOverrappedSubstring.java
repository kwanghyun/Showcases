package algorithm.stringArray;

import java.util.HashSet;

/*
 * Find out the longest repeated common sub-string(overlapped) in a string.
 * For example:- mystr = banana # The "ana" is the common overlapped
 * sub-string is been used 2 times.
 */
public class LongestOverrappedSubstring {

	public String getMaxString(String str) {

		if (str == null || str.length() == 0)
			return null;

		int strLen = str.length();
		int maxLen = 0;
		String maxString = "";
		HashSet<String> set = new HashSet<>();

		for (int len = 1; len <= strLen; len++) {
			for (int i = 0; i < strLen - len + 1; i++) {
				int j = i + len;
				String tmp = str.substring(i, j);
				if (!set.contains(tmp)) {
					set.add(tmp);
				} else {
					if (maxLen < j - i) {
						maxLen = j - i;
						maxString = tmp;
					}
				}
			}
		}

		return maxString;
	}

	public static void main(String[] args) {
		LongestOverrappedSubstring ob = new LongestOverrappedSubstring();
		System.out.println(ob.getMaxString("banana"));
	}
}
