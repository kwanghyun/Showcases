package algorithm.dynamic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import algorithm.Utils;

/*
 * Given a string s and a dictionary of words dict, determine if s can be
 * segmented into a space-separated sequence of one or more dictionary
 * words. For example, given s = "leetcode", dict = ["leet", "code"]. Return
 * true because "leetcode" can be segmented as "leet code".
 * 
 * This problem can be solve by using a naive approach, which is trivial. A
 * discussion can always start from that though.
 */
public class WordBreak {

	// Time is O(n^2) and exceeds the time limit.
	public boolean wordBreak(String string, HashSet<String> dict) {
		return dfs(string, dict);
	}

	public boolean wordBreakDP(String string, HashSet<String> dict) {
		return wordBreakDp(string, dict);
	}

	public boolean dfs(String str, HashSet<String> dict) {

		if (str.length() == 0)
			return true;

		for (int i = 1; i <= str.length(); i++) {
			String part = str.substring(0, i);
			if (dict.contains(part)) {
				if (dfs(str.substring(i), dict))
					return true;
			}
		}
		return false;
	}

	public boolean wordBreakI(String string, HashSet<String> dict) {
		return dfsI(string, dict, 0);
	}

	public boolean dfsI(String str, HashSet<String> dict, int idx) {
		if (idx == str.length())
			return true;

		for (int i = idx + 1; i <= str.length(); i++) {
			String part = str.substring(idx, i);
			if (dict.contains(part)) {
				if (dfsI(str, dict, i))
					return true;
			}
		}
		return false;
	}

	public boolean wordBreakDp(String s, Set<String> wordDict) {
		int[] pos = new int[s.length() + 1];

		Arrays.fill(pos, -1);

		pos[0] = 0;

		for (int i = 0; i < s.length(); i++) {
			if (pos[i] != -1) {
				for (int j = i + 1; j <= s.length(); j++) {
					String sub = s.substring(i, j);
					System.out.println(sub);
					if (wordDict.contains(sub)) {
						pos[j] = i;
					}
				}
				Utils.printArray(pos);
			}
		}

		return pos[s.length()] != -1;
	}

	public String breakWordDPI(String word, Set<String> dict) {
		int dp[][] = new int[word.length()][word.length()];

		for (int i = 0; i < dp.length; i++) {
			for (int j = 0; j < dp[i].length; j++) {
				dp[i][j] = -1; // -1 indicates string between i to j cannot be
								// split
			}
		}

		// fill up the matrix in bottom up manner
		for (int len = 1; len <= word.length(); len++) {
			for (int r = 0; r < word.length() - len + 1; r++) {
				int c = r + len - 1;
				String str = word.substring(r, c + 1);

				if (dict.contains(str)) {
					dp[r][c] = r;
					continue;
				}

				for (int k = r + 1; k <= c; k++) {
					if (dp[r][k - 1] != -1 && dp[k][c] != -1) {
						dp[r][c] = k;
						break;
					}
				}
			}
		}
		if (dp[0][word.length() - 1] == -1) {
			return null;
		}

		// create space separate word from string is possible
		StringBuffer buffer = new StringBuffer();
		int i = 0;
		int j = word.length() - 1;
		while (i < j) {
			int k = dp[i][j];
			if (i == k) {
				buffer.append(word.substring(i, j + 1));
				break;
			}
			buffer.append(word.substring(i, k) + " ");
			i = k;
		}

		return buffer.toString();
	}

	/*
	 * Time: O(string length * dict size) One tricky part of this solution is
	 * the case: INPUT: "programcreek", ["programcree","program","creek"]. We
	 * should get all possible matches, not stop at "programcree".
	 */
	public static void main(String[] args) {
		HashSet<String> dict = new HashSet<String>();
		dict.add("leet");
		dict.add("code");
		// dict.add("le");
		// dict.add("co");
		// dict.add("de");
		dict.add("none");
		WordBreak obj = new WordBreak();
		System.out.println("------------------wordBreak-----------------");
		System.out.println(obj.wordBreak("leetcode", dict));
		System.out.println("------------------wordBreakDP-----------------");
		System.out.println(obj.wordBreakDP("leetcode", dict));
		System.out.println("------------------wordBreakI----------------");
		System.out.println(obj.wordBreakI("leetcode", dict));
		System.out.println("------------------breakWordDP----------------");
		System.out.println(obj.breakWordDPI("leetcode", dict));
	}
}
