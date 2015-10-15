package algorithm.stringArray;

/*
 * Reverse digits of an integer. 
 * Example1: x = 123, return 321 Example2: x = -123, return -321
 */
public class ReverseInteger {
	public int reverseInteger(int num) {
		// flag marks if x is negative
		boolean flag = false;
		if (num < 0) {
			num = 0 - num;
			flag = true;
		}

		int result = 0;
		int temp = num;

		while (temp > 0) {
			int mod = temp % 10;
			temp = temp / 10;
			result = result * 10 + mod;
		}

		if (flag) {
			result = 0 - result;
		}

		return result;
	}
}
