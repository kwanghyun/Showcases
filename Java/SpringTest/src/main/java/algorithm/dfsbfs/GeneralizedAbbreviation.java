package algorithm.dfsbfs;

import java.util.ArrayList;
import java.util.List;

/*
 * Write a function to generate the generalized abbreviations of a word.
 * 
 * Example: Given word = "word", return the following list (order does not
 * matter):
 * 
 * ["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d",
 * "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"]
 */
public class GeneralizedAbbreviation {
	public List<String> generateAbbreviations(String word) {
		List<String> result = new ArrayList<>();
		if (word == null || word.length() == 0)
			return result;

		int len = word.length();

		for (int i = 0; i < 1 << len; i++) {
			int count = 0;
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < len; j++) {
				if ((i >> j & 1) == 1) {
					count++;
				} else {
					if (count > 0) {
						sb.append(count);
						count = 0;
					}
					sb.append(word.charAt(j));
				}
			}
			if (count > 0)
				sb.append(count);

			result.add(sb.toString());

		}

		return result;
	}

	public static void main(String[] args) {
		GeneralizedAbbreviation ob = new GeneralizedAbbreviation();
		System.out.println(ob.generateAbbreviations("word"));

	}
}
