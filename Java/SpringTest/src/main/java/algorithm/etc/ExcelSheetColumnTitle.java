package algorithm.etc;

/*
 * Given a positive integer, return its corresponding column title as appear
 * in an Excel sheet. For example:
 * 
 * 1 -> A
 * 2 -> B
 * 3 -> C 
 * ...
 * 26 -> Z
 * 27 -> AA
 * 28 -> AB
 * 
 * This problem is the reverse version of Excel Sheet Column Number.
 * The key is n--. The minimum in 26-bit number is mapped to 1, not 0.
 */
public class ExcelSheetColumnTitle {
	public String convertToTitle(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Input is not valid!");
		}

		StringBuilder sb = new StringBuilder();

		while (n > 0) {
			// If -1 in the below ch level, it got wired value
			n--;
			char ch = (char) (n % 26 + 'A');
			n /= 26;
			sb.insert(0, ch);
		}
		return sb.toString();
	}

	public String convertToTitleI(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Input is not valid!");
		}

		StringBuilder sb = new StringBuilder();

		while (n > 0) {
			n--;
			char ch = (char) (n % 26 + 'A');
			n /= 26;
			sb.append(ch);
			// sb.insert(0, ch);
		}

		// sb.reverse();
		return sb.toString();
	}

	public static void main(String[] args) {
		ExcelSheetColumnTitle ob = new ExcelSheetColumnTitle();
		int test_n = 324;
		// int test_n = 26;
		System.out.println(ob.convertToTitle(test_n));
		System.out.println(ob.convertToTitleI(test_n));
	}
}
