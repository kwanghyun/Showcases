package algorithm.stringArray;

import java.util.ArrayList;
import java.util.List;

/*
 * Given an array of words and a length L, format the text such that each
 * line has exactly L characters and is fully (left and right) justified.
 * You should pack your words in a greedy approach; that is, pack as many
 * words as you can in each line. Pad extra spaces ' ' when necessary so
 * that each line has exactlyL characters. Extra spaces between words should
 * be distributed as evenly as possible. If the number of spaces on a line
 * do not divide evenly between words, the empty slots on the left will be
 * assigned more spaces than the slots on the right. For the last line of
 * text, it should be left justified and no extra space is inserted between
 * words. For example,
 * 
 * words: ["This", "is", "an", "example", "of", "text", "justification."]
 * 
 * L: 16.
 * 
 * Return the formatted lines as:
 * 
 * [ "This    is    an", 
 * "example  of text", 
 * "justification.  " ]
 * 
 * Note: Each word is guaranteed not to exceed L in length.
 * 
 * 1. if a line has only one word and the word's length is less than max
 * width, we need to fill the left part with spaces. 2. how to distribute
 * extra spaces for each words when the number of spaces can not be evenly
 * distributed to each word.
 */
public class TextJustification {
	public List<String> fullJustify(String[] words, int maxWidth) {

		int len = 0;
		List<StringBuilder> words4line = new ArrayList<>();
		List<String> resultList = new ArrayList<String>();

		for (int idx = 0; idx < words.length; idx++) {
			String word = words[idx];
			StringBuilder result = new StringBuilder();
			words4line.add(new StringBuilder(word));
			len += word.length();

			// if it's last string or line exceed the limit by adding next
			// string.
			if (idx == words.length - 1 || len + (words4line.size() - 1) + words[idx + 1].length() > maxWidth) {
				int eachSpace = 0;
				int extraSpace = 0;
				if (words4line.size() > 1) {
					eachSpace = (maxWidth - len) / (words4line.size() - 1);
					extraSpace = (maxWidth - len) % (words4line.size() - 1);

					while (eachSpace > 0) {
						for (int i = 0; i < words4line.size() - 1; i++) {
							words4line.get(i).append(" ");
						}
						eachSpace--;
					}

					int i = 0;
					while (extraSpace > 0) {
						words4line.get(i++ % (words4line.size() - 1)).append(" ");
						extraSpace--;
						i++;
					}
				} else {
					extraSpace = maxWidth - len;
					// single string leftover case
					while (extraSpace > 0) {
						words4line.get(0).append(" ");
						extraSpace--;
					}
				}

				for (StringBuilder w : words4line) {
					result.append(w);
				}
				resultList.add(result.toString());
				words4line.clear();
				len = 0;
			}
		}
		return resultList;
	}

	public static void main(String[] args) {
		String[] words = { "This", "is", "an", "example", "of", "text", "justification." };
		TextJustification ob = new TextJustification();
		List<String> list = ob.fullJustify(words, 16);
		System.out.println(list);
		for (String word : list)
			System.out.println(word.length());

		System.out.println("---------------------------");
		List<String> list2 = ob.fullJustifyI(words, 16);
		System.out.println(list2);
		for (String word : list2)
			System.out.println(word.length());
	}

	public List<String> fullJustifyI(String[] words, int maxWidth) {
		List<String> result = new ArrayList<String>();

		if (words == null || words.length == 0) {
			return result;
		}

		int count = 0;
		int last = 0;

		for (int i = 0; i < words.length; i++) {
			count = count + words[i].length();

			if (count + i - last > maxWidth) {
				int wordsLen = count - words[i].length();
				int spaceLen = maxWidth - wordsLen;
				int eachLen = 1;
				int extraLen = 0;

				if (i - last - 1 > 0) {
					eachLen = spaceLen / (i - last - 1);
					extraLen = spaceLen % (i - last - 1);
				}

				StringBuilder sb = new StringBuilder();

				for (int k = last; k < i - 1; k++) {
					sb.append(words[k]);

					int ce = 0;
					while (ce < eachLen) {
						sb.append(" ");
						ce++;
					}

					if (extraLen > 0) {
						sb.append(" ");
						extraLen--;
					}
				}

				sb.append(words[i - 1]);// last words in the line
				// if only one word in this line, need to fill left with space
				while (sb.length() < maxWidth) {
					sb.append(" ");
				}

				result.add(sb.toString());

				last = i;
				count = words[i].length();
			}
		}

		StringBuilder sb = new StringBuilder();

		for (int i = last; i < words.length - 1; i++) {
			count = count + words[i].length();
			sb.append(words[i] + " ");
		}

		sb.append(words[words.length - 1]);
		while (sb.length() < maxWidth) {
			sb.append(" ");
		}
		result.add(sb.toString());

		return result;
	}
}
