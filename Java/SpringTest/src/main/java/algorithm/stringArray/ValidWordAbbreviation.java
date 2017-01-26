package algorithm.stringArray;

/*
 * Given a non-empty string s and an abbreviation abbr, return whether the
 * string matches with the given abbreviation.
 * 
 * A string such as "word" contains only the following valid abbreviations:
 * 
 * ["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d",
 * "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"] Notice that only the above
 * abbreviations are valid abbreviations of the string "word". Any other
 * string is not a valid abbreviation of "word".
 * 
 * Note: Assume s contains only lowercase letters and abbr contains only
 * lowercase letters and digits.
 * 
 * Example 1: 
 * Given s = "internationalization", abbr = "i12iz4n":
 * Return true. 
 * 
 * Example 2: 
 * Given s = "apple", abbr = "a2e":
 * Return false.
 */
public class ValidWordAbbreviation {
	public boolean validWordAbbreviation(String word, String abbr) {
		int d = 0;
		int wIdx = 0;
		for (int i = 0; i < abbr.length(); i++) {
			if (Character.isDigit(abbr.charAt(i))) {
				if (abbr.charAt(i) == '0' && d == 0)
					return false;
				d = d * 10 + (abbr.charAt(i) - '0');

			} else {
				while (d > 0) {
					d--;
					wIdx++;
				}
				if (wIdx >= word.length() || word.charAt(wIdx) != abbr.charAt(i)) {
					return false;
				}
				wIdx++;
				d = 0;
			}
		}

		return (word.length() - wIdx == d) ? true : false;
	}

	public static void main(String[] args) {
		ValidWordAbbreviation ob = new ValidWordAbbreviation();
		// String word = "internationalization";
		// String abbr = "i12iz4n";
		// String word = "a";
		// String abbr = "01";

		String word = "abbreviation";
		String abbr = "a10n";

		System.out.println(ob.validWordAbbreviation(word, abbr));
	}
}
