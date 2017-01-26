package algorithm.dynamic;

/*
 * Ugly numbers are numbers whose only prime factors are 2, 3 or 5. 
 * The sequence 1, 2, 3, 4, 5, 6, 8, 9, 10, 12, 15, … 
 * shows the first 11 ugly numbers. By convention, 1 is included. 
 * Write a program to find and print the 150’th ugly number.
 */
public class NextUglyNumber {

	public int getMaxDiviable(int x, int y) {
		while (x % y == 0)
			x = x / y;
		return x;
	}

	private boolean isUnglyNumber(int n) {
		int remains = getMaxDiviable(n, 2);
		remains = getMaxDiviable(remains, 3);
		remains = getMaxDiviable(remains, 5);
		return remains == 1 ? true : false;
	}

	public int getNextUglyNumber(int n) {
		if (n == 1)
			return 1;

		int count = 1;
		int num = 1;
		while (count < n) {
			num++;
			if (isUnglyNumber(num)) {
				count++;
			}
		}

		return num;
	}

	public static void main(String[] args) {
		NextUglyNumber ob = new NextUglyNumber();
		System.out.println(ob.getNextUglyNumber(150));
	}
}
