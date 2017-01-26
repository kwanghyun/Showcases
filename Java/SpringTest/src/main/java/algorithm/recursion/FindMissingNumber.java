package algorithm.recursion;

/*
 * Given a string having a number: "625626628" 
 * Here the substrings are in consecutive order except for 1 substring 
 * which is missing. Find the missing substring. 
 * 
 * Test cases: 
 * "1235678" -> 4 is missing 
 * "9979981000" -> 999 is missing 
 * "624625627" -> 626 is missing
 */

public class FindMissingNumber {

	int result = 0;

	public void findMissingNumber(String str, int len, int oldVal) {

		if (str.length() == 0) {
			return;
		}

		for (int i = len; i <= str.length(); i++) {
			if (i > str.length()) {
				break;
			}

			int curr = Integer.parseInt(str.substring(0, i));
			if (oldVal == -1 || (curr - oldVal) <= 2) {
				if (curr - oldVal == 2) {

					if (result == 0) {
						result = oldVal + 1;
					} else {
						// there is more than one missing number.
						result = 0;
						break;
					}
				}
				findMissingNumber(str.substring(i), i, curr);
			} else {
				break;
			}
		}
	}

	public static void main(String[] args) {
		FindMissingNumber ob = new FindMissingNumber();

		ob.findMissingNumber("625626628", 1, -1);
		System.out.println(ob.result);

		ob.result = 0;
		ob.findMissingNumber("1235678", 1, -1);
		System.out.println(ob.result);

		ob.result = 0;
		ob.findMissingNumber("9798100", 1, -1);
		System.out.println(ob.result);

		ob.result = 0;
		ob.findMissingNumber("624625627", 1, -1);
		System.out.println(ob.result);

	}
}
