package algorithm.etc;

/*
 * 19.1 Write a function to swap a number in place without temporary variables.
 */
public class CC_19_1_SwapWithoutTemp{
		int bit_fld = 0; //int = 4 bytes = 32 bits 
	public void test(int n){
		//Set a bit
		bit_fld |= (1 << n);
		//Get a bit
		bit_fld &= (1 << n);
		//Clear a bit
		bit_fld &= ~(1 << n);
		//Toggle a bit
		bit_fld ^= (1 << n);
		
	}
	public void swap(int x, int y){
		x = y - x;
		y = y -x;
		x = x + y;
		System.out.println("x : " + x + ", y : " + y);
	}

	public void swapWithBit(int x, int y){
		x = y ^ x;
		y = y ^ x;
		x = x ^ y;
		System.out.println("x : " + x + ", y : " + y);
	}

	public static void main(String args[]){
		CC_19_1_SwapWithoutTemp swt = new CC_19_1_SwapWithoutTemp();
		swt.swap(7, 5);
		swt.swapWithBit(7, 5);
	}
}
