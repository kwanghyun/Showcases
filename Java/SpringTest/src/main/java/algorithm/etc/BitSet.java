package algorithm.etc;

class BitSet {
    int[] bitset;
    
    public BitSet(int size) {
            bitset = new int[size >> 5]; // divide by 32
    }

    boolean get(int pos) {
            int wordNumber = (pos >> 5); // divide by 32
            int bitNumber = (pos & 0x1F); // mod 32
            return (bitset[wordNumber] & (1 << bitNumber)) != 0;
    }
    
    void set(int pos) {
            int wordNumber = (pos >> 5); // divide by 32
            int bitNumber = (pos & 0x1F); // mod 32
            bitset[wordNumber] |= 1 << bitNumber;
    }
    
	public static void main(String args[]) {

		int num2 = 0;
		int num = 105;
		System.out.println(num >> 5);
		System.out.println(num & 0x1F);
		num2 |= 1 << 9;
		System.out.println(num2);
		System.out.println((512 & (1<<9)) != 0 );
		
		System.out.println("-------------------------------");
		int a = 7;
		int b = 5;
		System.out.println(a^b);
		
	}
	
	int factorial( int n ){
	    if (n > 1) {    /* Recursive case */
	        return factorial(n-1) * n;
	    } else {        /* Base case */
	        return 1;
	    }
	}
}
