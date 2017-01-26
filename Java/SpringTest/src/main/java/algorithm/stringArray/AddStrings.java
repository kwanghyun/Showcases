package algorithm.stringArray;

public class AddStrings {

	public String addStrings(String num1, String num2) {
		StringBuilder sb = new StringBuilder();
		int n1Idx = num1.length();
		int n2Idx = num2.length();
		int carry = 0;

		for (int i = Math.max(num1.length(), num2.length()) - 1; i >= 0; i--) {
			int num = 0;

			if (n1Idx > 0) {
				n1Idx--;
				num += num1.charAt(n1Idx) - '0';
				System.out.println("1. num = " + num);
			}

			if (n2Idx > 0) {
				n2Idx--;
				num += num2.charAt(n2Idx) - '0';
				System.out.println("2. num = " + num);
			}

			num += carry;
			carry = num / 10;

			System.out.println("3. num = " + num + ", carry = " + carry);

			sb.insert(0, num % 10);
		}

		if (carry > 0) {
			sb.insert(0, carry);
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		AddStrings ob = new AddStrings();
		String num1 = "12345";
		String num2 = "789";
		System.out.println(ob.addStrings(num1, num2));
	}
}
