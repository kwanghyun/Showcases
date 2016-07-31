package algorithm.bits;

/*
 * Problem: Get maximum binary Gap.
 * 
 * For example, 9's binary form is 1001, the gap is 2.
 * 
 * Thoughts
 * 
 * The key to solve this problem is the fact that an integer x & 1 will get
 * the last digit of the integer.
 */
public class MaxBinaryGap {
	public static int solution(int num) {
		int max = 0;
		int count = -1;
		int r = 0;

		while (num > 0) {
			// get right most bit & shift right
			r = num & 1;
			num = num >> 1;

			if (0 == r && count >= 0) {
				count++;

			}

			if (1 == r) {
				max = count > max ? count : max;
				count = 0;
			}
		}

		return max;
	}

	public static int solutionI(int num) {
		int max = 0;
		int count = 0;
		int idx = 0;
		while (num > 0) {
			int t = (num >> idx) & 1;
			if (t == 1) {
				max = Math.max(max, count);
				count = 0;
			} else {
				count++;
			}
		}
		return max;
	}

	public static void main(String[] args) {
		int TEST_INT1 = 99;
		int TEST_INT2 = 9;
		System.out.println("_______solution_______");
		System.out.println(solution(TEST_INT1));
		System.out.println(solution(TEST_INT2));
		System.out.println("_______solutionI_______");
		System.out.println(solution(TEST_INT1));
		System.out.println(solution(TEST_INT2));

	}
}
