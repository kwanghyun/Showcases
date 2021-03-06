package algorithm.stringArray;

public class FindCharInStringRecursive {

	public int countA(String input) {

		// exit condition – recursive calls must have an exit condition
		if (input == null || input.length() == 0) {
			return 0;
		}

		int count = 0;

		// check first character of the input
		if (input.substring(0, 1).equals("A")) {
			count = 1;
		}

		// recursive call to evaluate rest of the input
		// (i.e. 2nd character onwards)
		return count + countA(input.substring(1));
	}

	public static void main(String[] args) {
		FindCharInStringRecursive fsr = new FindCharInStringRecursive();
		System.out.println(fsr.countA("AAA rating"));
	}
}
