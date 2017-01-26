package algorithm.stringArray;

import java.util.Arrays;

/*
 * Given two integers representing the numerator and denominator of a
 * fraction, return the fraction in string format.
 * 
 * If the fractional part is repeating, enclose the repeating part in
 * parentheses.
 * 
 * For example,
 * 
 * Given numerator = 1, denominator = 2, return "0.5".
 * Given numerator = 2, denominator = 1, return "2".
 * Given numerator = 2, denominator = 3, return "0.(6)".
 */
public class FractiontoRecurringDecimal {

	public String fractionToDecimal(int numerator, int denominator) {
		StringBuilder result = new StringBuilder();
		if (denominator == 0) {
			return "0";
		}
		double div = (double) numerator / denominator;
		String divStr = Double.toString(div);

		String[] divArr = divStr.split("\\.");
		System.out.println(div);
		System.out.println("divStr = " + divStr + " , divArr = " + Arrays.toString(divArr) + ", divArr[1].length() = "
				+ divArr[1].length());

		if (numerator % denominator == 0)
			return divArr[0];

		result.append(divArr[0] + ".");
		boolean duplicated = false;

		for (int i = 1; i < divArr[1].length(); i++) {
			if (divArr[1].charAt(i - 1) == divArr[1].charAt(i)) {
				duplicated = true;
			} else if (duplicated) {
				result.append("(" + divArr[1].charAt(i - 1) + ")");
				duplicated = false;
			} else {
				result.append(divArr[1].charAt(i - 1));
			}
		}

		if (duplicated) {
			result.append("(" + divArr[1].charAt(divArr[1].length() - 1) + ")");
		} else {
			result.append(divArr[1].charAt(divArr[1].length() - 1));
		}
		return result.toString();
	}

	public static void main(String[] args) {
		FractiontoRecurringDecimal ob = new FractiontoRecurringDecimal();
		System.out.println(ob.fractionToDecimal(1, 2));
		System.out.println(ob.fractionToDecimal(2, 1));
		System.out.println(ob.fractionToDecimal(2, 3));
		System.out.println(ob.fractionToDecimal(1, 6));
		System.out.println(ob.fractionToDecimal(1, 8));
		System.out.println(ob.fractionToDecimal(1, 90));
	}
}
