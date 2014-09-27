package algorithm.stringArray;

public class ReverseInteger {
	public int reverseInteger(int paramNum) {
		// flag marks if x is negative
		boolean flag = false;
		if (paramNum < 0) {
			paramNum = 0 - paramNum;
			flag = true;
		}

		int resultNum = 0;
		int tempNum = paramNum;

		while (tempNum > 0) {
			int mod = tempNum % 10;
			tempNum = tempNum / 10;
			resultNum = resultNum * 10 + mod;
		}

		if (flag) {
			resultNum = 0 - resultNum;
		}

		return resultNum;
	}
}
