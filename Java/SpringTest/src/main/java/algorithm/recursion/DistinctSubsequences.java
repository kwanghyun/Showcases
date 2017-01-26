package algorithm.recursion;

public class DistinctSubsequences {

	public int numDistinct(String s, String t) {
		int count = 0;
		numDistinct(s.toCharArray(), 0, t.toCharArray(), 0, count);
		return count;
	}

	public boolean numDistinct(char[] s, int sIdx, char[] t, int tIdx, int count) {
		if (sIdx == s.length && tIdx == t.length)
			return true;

		if (sIdx == s.length || tIdx == t.length)
			return false;

		if (s[sIdx] == t[tIdx]) {
			return numDistinct(s, sIdx + 1, t, tIdx + 1, count);
		} else {
			while (sIdx < s.length) {
				if (numDistinct(s, sIdx + 1, t, tIdx, count + 1)) {
					return true;
				}
				sIdx++;
			}
		}

		return false;
	}

}
