package algorithm.stringArray;

public class CountAllPossibleDistinctBinaryStringWithNoConsecutive1 {
	public static int countBinary(int N) {
		if (N < 1)
			return 0;
		int C0 = 1;
		int C1 = 1;

		for (int i = 1; i < N; i++) {
			int temp = C1;
			C1 = C0;
			C0 = C0 + temp;
		}

		return C0 + C1;
	}

	public static void main(String args[]) {
		System.out.print(countBinary(4));
	}
}
