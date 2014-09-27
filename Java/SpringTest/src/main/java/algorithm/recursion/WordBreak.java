package algorithm.recursion;

import java.util.Set;

public class WordBreak {
	public boolean wordBreak(String string , Set<String> dict) {
		return wordBreakHelper(string , dict, 0);
	}

	public boolean wordBreakHelper(String string , Set<String> dict, int start) {
		if (start == string .length())
			return true;

		for (String item : dict) {
			int len = item.length();
			int end = start + len;

			// end index should be <= string length
			if (end > string .length())
				continue;

			if (string.substring(start, start + len).equals(item))
				if (wordBreakHelper(string , dict, start + len))
					return true;
		}
		return false;
	}
}
