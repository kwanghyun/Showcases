package algorithm.bits;

/*Reverse bits of a given 32 bits unsigned integer.
 For example, given input 43261596 
 (represented in binary as 00000010100101000001111010011100),
 return 964176192 
 (represented in binary as 00111001011110000010100101000000).
 Follow up: If this function is called many times, how would you optimize it?
 Related problem: Reverse Integer*/

public class ReverseBits {
	public static int reverseBits(int n) {
		for (int i = 0; i < 16; i++) {
			n = swapBits(n, i, 32 - i - 1);
		}
		return n;
	}

	public static int swapBits(int n, int i, int j) {
		int a = (n >> i) & 1; // right to left
		int b = (n >> j) & 1; // left to right
		if ((a ^ b) != 0) { // if it's not the same bit.
			return n ^= (1 << i) | (1 << j); //toggle the bits
		}
		return n;
	}

	public static long reverse(long x) {
	    long r = 0;
	    for (int i = 63; i >= 0; i--) {
	        r |= ((x >>> i) & 0x1L) << (63 - i);
	    }
	    return r;
	}
	
	public static void main(String[] args) {
		System.out.println(reverse(5));
		
		System.out.println(8 >> 2);
		System.out.println((8 >> 2) & 1);
	}
}
