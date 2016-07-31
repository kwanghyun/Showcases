package algorithm.dynamic;

/*
 * Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and
 * s2.
 * 
 * Example,
 * 
 * s1 = "aabcc",
 * 
 * s2 = "dbbca",
 * 
 * When s3 = "aadbbcbcac", return true.
 * 
 * When s3 = "aadbbbaccc", return false
 * 
 * Return 0 / 1 ( 0 for false, 1 for true ) for this problem
 */
public class InterleavingStrings {

	public boolean isInterleavedRecursive(char str1[], char str2[], char str3[], int pos1, int pos2, int pos3) {
		if (pos1 == str1.length && pos2 == str2.length && pos3 == str3.length) {
			return true;
		}

		if (pos3 == str3.length) {
			return false;
		}
		boolean a = false;
		boolean b = false;
		if (pos1 < str1.length && str1[pos1] == str3[pos3])
			a = isInterleavedRecursive(str1, str2, str3, pos1 + 1, pos2, pos3 + 1);

		if (pos2 < str2.length && str2[pos2] == str3[pos3])
			b = isInterleavedRecursive(str1, str2, str3, pos1, pos2 + 1, pos3 + 1);
		return a || b;
	}

	public boolean isInterleavedRecursiveI(char str1[], char str2[], char str3[], int pos1, int pos2, int pos3) {
		if (pos1 == str1.length && pos2 == str2.length && pos3 == str3.length) {
			return true;
		}

		if (pos3 == str3.length) {
			return false;
		}

		return (pos1 < str1.length && str1[pos1] == str3[pos3]
				&& isInterleavedRecursive(str1, str2, str3, pos1 + 1, pos2, pos3 + 1))
				|| (pos2 < str2.length && str2[pos2] == str3[pos3]
						&& isInterleavedRecursive(str1, str2, str3, pos1, pos2 + 1, pos3 + 1));
	}

	public boolean isInterleaved(char str1[], char str2[], char str3[]) {
		boolean dp[][] = new boolean[str1.length + 1][str2.length + 1];

		if (str1.length + str2.length != str3.length) {
			return false;
		}

		for (int r = 0; r < dp.length; r++) {
			for (int c = 0; c < dp[r].length; c++) {
				int len = r + c - 1;
				if (r == 0 && c == 0) {
					dp[r][c] = true;
				} else if (r == 0) {
					if (str3[len] == str2[c - 1]) {
						dp[r][c] = dp[r][c - 1];
					}
				} else if (c == 0) {
					if (str1[r - 1] == str3[len]) {
						dp[r][c] = dp[r - 1][c];
					}
				} else {
					dp[r][c] = (str1[r - 1] == str3[len] ? dp[r - 1][c] : false)
							|| (str2[c - 1] == str3[len] ? dp[r][c - 1] : false);
				}
			}
		}
		return dp[str1.length][str2.length];
	}

	public static void test1(InterleavingStrings sti) {
		String str1 = "XXYM";
		String str2 = "XXZT";
		String str3 = "XXXZXYTM";

		System.out.println(
				sti.isInterleavedRecursive(str1.toCharArray(), str2.toCharArray(), str3.toCharArray(), 0, 0, 0));
	}

	public static void test2(InterleavingStrings sti) {
		String str1 = "aabcc";
		String str2 = "dbbca";
		String str3 = "aadbbcbcac";

		System.out.println(
				sti.isInterleavedRecursive(str1.toCharArray(), str2.toCharArray(), str3.toCharArray(), 0, 0, 0));
	}

	public static void test3(InterleavingStrings sti) {
		String str1 = "aabcc";
		String str2 = "dbbca";
		String str3 = "aadbbbaccc";

		System.out.println(
				sti.isInterleavedRecursive(str1.toCharArray(), str2.toCharArray(), str3.toCharArray(), 0, 0, 0));
	}

	public static void main(String args[]) {
		InterleavingStrings sti = new InterleavingStrings();
		test1(sti);
		System.out.println("------------------------");
		test2(sti);
		System.out.println("------------------------");
		test3(sti);
	}
}
