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

	public static void main(String[] args) {
		System.out.println("______________");
		System.out.println(solution(9));
		System.out.println(solution(8));
	}
}
