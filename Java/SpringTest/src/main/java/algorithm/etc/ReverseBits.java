package algorithm.etc;

import java.util.StringTokenizer;

/*For example, I have the binary number 1011 which is equal to decimal 11. 
 * I want the reverse bit's location such that it become 1101, 
 * which is decimal 13. Here is code:
 * 
 */
public class ReverseBits {
	public int reverseBits(int n) {
		for (int i = 0; i < 16; i++) {
			n = swapBits(n, i, 32 - i - 1);
		}
		return n;
	}

	public int swapBits(int n, int i, int j) {
		int a = (n >> i) & 1; //Get i th bit
		int b = (n >> j) & 1; //Get j th bit
		if ((a ^ b) != 0) { // if it's not the same bit.
			return n ^= (1 << i) | (1 << j); //toggle the bits
		}
		return n;
	}

	/*
	 * You can use a third shift operator called the "unsigned shift right"
	 * operator: >>> for always shifting in a "0" regardless of the sign.
	 */
	public long reverse(long x) {
	    long r = 0;
	    for (int i = 63; i >= 0; i--) {
	        r |= ((x >>> i) & 0x1L) << (63 - i);
	    }
	    return r;
	}
	
	
	public long reverse2(long x) {
	    long r = 0;
	    for (int i = 63; i >= 0; i--) {
	    	long tmp = (x >>> i) & 0x1L; //Get i th bit
	        r |= tmp << (63 - i); // Set i th bit
	    }
	    return r;
	}
	
	public static void main(String[] args) {
		ReverseBits ob = new ReverseBits();
		System.out.println(ob.reverse(10L));
		System.out.println(ob.reverse2(10L));
	}
}
