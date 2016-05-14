package algorithm.bits;

/*
 * You are given two numbers A and B. Write a program to count number of
 * bits needed to be flipped to convert A to B.
 * 
 * Output:
 * Print the number of bits needed to be flipped.
 * 
 * Explanation:
 * 
 * A = 1001001 
 * B = 0010101 
 * No of bits need to flipped = set bit count i.e. 4
 */
public class BitDfference {
	public int count(int num1, int num2) {
		int count = 0;
		for (int i = 0; i < 32; i++) {
			int a = (num1 >> i) & 1;
			int b = (num2 >> i) & 1;
			if ((a ^ b) == 1)
				count++;
		}
		return count;
	}

	public static void main(String[] args) {
		BitDfference ob = new BitDfference();
		System.out.println(ob.count(10, 20));
	}
}
