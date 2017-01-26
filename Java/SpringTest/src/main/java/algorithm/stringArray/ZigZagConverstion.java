package algorithm.stringArray;

public class ZigZagConverstion {

	public void printString(String s, int numRows) {
		if (s == null || s.length() == 0)
			return;

		int idx = 0;

		int aLineLen = s.length() / ((numRows + numRows / 2) / 2);
		for (int r = 0; r < numRows; r++) {
			if (r % 2 == 0) {
				for (int c = 0; idx < s.length() && c < aLineLen; c++) {
					if (c % 2 == 0) {
						System.out.format("%3c", s.charAt(idx));
						idx++;
					} else {
						System.out.format("%3c", ' ');
					}
				}
			} else {
				for (int c = 0; idx < s.length() && c < aLineLen; c++) {
					System.out.format("%3c", s.charAt(idx));
					idx++;
				}
			}
			System.out.println("");
		}
	}

	public static void main(String[] args) {
		ZigZagConverstion ob = new ZigZagConverstion();
		ob.printString("PAHNAPLSIIGYIR", 3);
	}
}
