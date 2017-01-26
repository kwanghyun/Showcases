package algorithm.stringArray;

public class SentenceScreenFitting {

	public int wordsTyping(String[] sentence, int rows, int cols) {
		int r = 0, sIdx = 0, count = 0;

		while (r < rows) {
			int currIdx = r * cols;
			int currMax = currIdx + cols;
			System.out.println("r = " + r);
			while (currIdx < currMax) {

				String curStr = sentence[sIdx % sentence.length];

				if (curStr.length() > cols)
					return 0;

				currIdx += curStr.length();

				System.out.println("sIdx = " + sIdx + ", currIdx = " + currIdx + ", currMax = " + currMax);

				if (currIdx > currMax)
					break;

				sIdx++;
				currIdx++;

				if (sIdx % sentence.length == sentence.length - 1)
					count++;
			}
			r++;
		}

		return count;
	}

	public static void main(String[] args) {
		SentenceScreenFitting ob = new SentenceScreenFitting();
		String[] sentence = { "hellow", "world" };
		int rows = 2, cols = 8;

		// String[] sentence = { "a", "bcd", "e" };
		// int rows = 3, cols = 6;

		// String[] sentence = { "I", "had", "apple", "pie" };
		// int rows = 4, cols = 5;

		System.out.println(ob.wordsTyping(sentence, rows, cols));
	}
}
