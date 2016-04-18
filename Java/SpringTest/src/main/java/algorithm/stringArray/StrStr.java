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

	public int strStrKMP(String _text, String _pattern) {
		char[] text = _text.toCharArray();
		char[] pattern = _pattern.toCharArray();

		int textLen = text.length;
		int patternLen = pattern.length;

		if (patternLen == 0)
			return 0;

		int[] kmpTable = computeKMP(_pattern);
		int textIdx = 0;
		int pattIdx = 0;

		while (textIdx < textLen) {
			if (text[textIdx] == pattern[pattIdx]) {
				if (pattIdx == patternLen - 1) {
					return textIdx - patternLen + 1;
				}
				textIdx++;
				pattIdx++;
			} else if (pattIdx > 0) {
				//Reuse suffix
				pattIdx = kmpTable[pattIdx - 1];
			} else {
				textIdx++;
			}
		}
		return -1;
	}

	private int[] computeKMP(String pattern) {
		int[] kmpTable = new int[pattern.length()];
		kmpTable[0] = 0;
		for (int i = 1; i < pattern.length(); i++) {
			int index = kmpTable[i - 1];
			while (index > 0 && pattern.charAt(index) != pattern.charAt(i)) {
				index = kmpTable[index - 1];
			}
			if (pattern.charAt(index) == pattern.charAt(i)) {
				kmpTable[i] = kmpTable[i - 1] + 1;
			} else {
				kmpTable[i] = 0;
			}
		}
		return kmpTable;
	}

	public static void main(String[] args) {
		StrStr obj = new StrStr();
		System.out.println(obj.strStr("I'm the algorithm King", "gori"));
		
		System.out.println("-----------------------------------------");
		System.out.println(obj.strStrKMP("I'm the algorithm King", "gori"));
	}
}
