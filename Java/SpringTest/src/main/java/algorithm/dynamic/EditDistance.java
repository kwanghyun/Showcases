package algorithm.dynamic;

/*
 * There are three operations permitted on a word: replace, delete, insert.
 * For example, the edit distance between "a" and "b" is 1, the edit
 * distance between "abc" and "def" is 3. 
 * 
 * Key Analysis
 * 
 * Let dp[i][j] stands for the edit distance between two strings with length
 * i and j, i.e., word1[0,...,i-1] and word2[0,...,j-1]. There is a relation
 * between dp[i][j] and dp[i-1][j-1]. Let's say we transform from one string
 * to another. The first string has length i and it's last character is "x";
 * the second string has length j and its last character is "y". The
 * following diagram shows the relation.
 * 
 * if x == y, then dp[i][j] == dp[i-1][j-1]
 * if x != y, and we insert y for word1, then dp[i][j] = dp[i][j-1] + 1
 * if x != y, and we delete x for word1, then dp[i][j] = dp[i-1][j] + 1
 * if x != y, and we replace x with y for word1, then dp[i][j] =
 * dp[i-1][j-1] + 1
 * 
 * When x!=y, dp[i][j] is the min of the three situations.
 * 
 * Initial condition: 
 * dp[i][0] = i, dp[0][j] = j
 */
public class EditDistance {

	public int minDistanceI(String src, String dst) {
		// System.out.println("1 src = " + src + ", dst = " + dst);
		// If first string is empty, the only option is to
		// insert all characters of second string into first
		if (src.length() == 0)
			return dst.length();
	
		// If second string is empty, the only option is to
		// remove all characters of first string
		if (dst.length() == 0)
			return src.length();
	
		if (src.charAt(0) == dst.charAt(0)) {
			return minDistanceI(src.substring(1), dst.substring(1));
		}
	
		// If last characters are not same, consider all three
		// operations on last character of first string, recursively
		// compute minimum cost for all three operations and take
		// minimum of three values.
		int replace = minDistanceI(src.substring(1), dst.substring(1));
		System.out.println("replace = " + replace + ", src = " + src + ", dst = " + dst);
		int delete = minDistanceI(src.substring(1), dst);
		int add = minDistanceI(src, dst.substring(1));
		int tmp = Math.min(replace, delete);
	
		return 1 + Math.min(tmp, add);
	}

	public int minDistanceDP(String src, String dst) {
		int srcLen = src.length();
		int dstLen = dst.length();

		int[][] dp = new int[srcLen + 1][dstLen + 1];

		for (int r = 0; r <= srcLen; r++) {
			dp[r][0] = r;
		}

		for (int c = 0; c <= dstLen; c++) {
			dp[0][c] = c;
		}

		// iterate though, and check last char
		for (int r = 1; r <= srcLen; r++) {
			char c_src = src.charAt(r - 1);
			for (int c = 1; c <= dstLen; c++) {
				char c_dst = dst.charAt(c - 1);

				// if last two chars equal
				if (c_src == c_dst) {
					// update dp value for +1 length
					dp[r][c] = dp[r - 1][c - 1];
				} else {
					int replace = dp[r - 1][c - 1] + 1;
					int insert = dp[r][c - 1] + 1;
					int delete = dp[r - 1][c] + 1;

					int min = replace > insert ? insert : replace;
					min = delete > min ? min : delete;
					dp[r][c] = min;
				}
			}
		}
		return dp[srcLen][dstLen];
	}

	public int minDistance(String word1, String word2) {
		int len1 = word1.length();
		int len2 = word2.length();

		// len1+1, len2+1, because finally return dp[len1][len2]
		int[][] dp = new int[len1 + 1][len2 + 1];

		for (int i = 0; i <= len1; i++) {
			dp[i][0] = i;
		}

		for (int j = 0; j <= len2; j++) {
			dp[0][j] = j;
		}

		// iterate though, and check last char
		for (int i = 0; i < len1; i++) {
			char c1 = word1.charAt(i);
			for (int j = 0; j < len2; j++) {
				char c2 = word2.charAt(j);

				// if last two chars equal
				if (c1 == c2) {
					// update dp value for +1 length
					dp[i + 1][j + 1] = dp[i][j];
				} else {
					int replace = dp[i][j] + 1;
					int insert = dp[i][j + 1] + 1;
					int delete = dp[i + 1][j] + 1;

					int min = replace > insert ? insert : replace;
					min = delete > min ? min : delete;
					dp[i + 1][j + 1] = min;
				}
			}
		}

		return dp[len1][len2];
	}

	public static void main(String[] args) {
		EditDistance ob = new EditDistance();
		String src = "acb";
		String dst = "ade";
		System.out.println("-------------minDistance-------------------");
		System.out.println(ob.minDistance(src, dst));
		System.out.println("-------------minDistanceI-------------------");
		System.out.println(ob.minDistanceDP(src, dst));
		System.out.println("--------------minDistanceR------------------");
		System.out.println(ob.minDistanceI(src, dst));
	}
}
