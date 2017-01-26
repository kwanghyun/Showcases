package algorithm.etc;

/*
 * We are playing the Guess Game. The game is as follows:
 * 
 * I pick a number from 1 to n. You have to guess which number I picked.
 * 
 * Every time you guess wrong, I'll tell you whether the number is higher or
 * lower.
 * 
 * You call a pre-defined API guess(int num) which returns 3 possible
 * results (-1, 1, or 0):
 * 
 * -1 : My number is lower 1 : My number is higher 0 : Congrats! You got it!
 * Example: n = 10, I pick 6.
 * 
 * Return 6.
 */

public class GuessNumberHigherOrLower {

	int target;

	public int guessNumber(int n) {
		long start = 0;
		long end = n;

		while (start <= end) {

			long mid = (start + end) / 2;

			System.out.println("start = " + start + ", end = " + end + ", mid = " + mid);
			int result = guess((int) mid);
			if (result == 0)
				return (int) mid;
			else if (result == -1)
				end = (int) mid - 1;
			else
				start = (int) mid + 1;
		}

		return (int) start;
	}

	public int guess(int n) {
		if (target == n)
			return 0;
		else if (target - n > 0)
			return 1;
		else
			return -1;
	}

	public static void main(String[] args) {
		GuessNumberHigherOrLower ob = new GuessNumberHigherOrLower();
		ob.target = 1702766719;
		System.out.println(ob.guessNumber(2126753390));
	}
}
