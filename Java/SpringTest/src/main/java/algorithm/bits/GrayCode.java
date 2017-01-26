package algorithm.bits;

import java.util.ArrayList;
import java.util.List;

/*
 * The gray code is a binary numeral system where two successive values
 * differ in only one bit.
 * 
 * Given a non-negative integer n representing the total number of bits in
 * the code, print the sequence of gray code. A gray code sequence must
 * begin with 0.
 * 
 * For example, given n = 2, return [0,1,3,2]. Its gray code sequence is:
 * 
	Following is 2-bit sequence (n = 2)
  	  00 01 11 10
	Following is 3-bit sequence (n = 3)
	  000 001 011 010 110 111 101 100
	And Following is 4-bit sequence (n = 4)
	  0000 0001 0011 0010 0110 0111 0101 0100 1100 1101 1111 
	  1110 1010 1011 1001 1000
	  
	  
	  n-bit Gray Codes can be generated from list of (n-1)-bit Gray codes using 
	  	following steps.
	1) Let the list of (n-1)-bit Gray codes be L1. Create another list L2 which is 
		reverse of L1.
	2) Modify the list L1 by prefixing a ‘0’ in all codes of L1.
	3) Modify the list L2 by prefixing a ‘1’ in all codes of L2.
	4) Concatenate L1 and L2. The concatenated list is required list of n-bit 
		Gray codes.
	
	For example, following are steps for generating the 3-bit Gray code list from 
		the list of 2-bit Gray code list.
	L1 = {00, 01, 11, 10} (List of 2-bit Gray Codes)
	L2 = {10, 11, 01, 00} (Reverse of L1)
	Prefix all entries of L1 with ‘0’, L1 becomes {000, 001, 011, 010}
	Prefix all entries of L2 with ‘1’, L2 becomes {110, 111, 101, 100}
	Concatenate L1 and L2, we get {000, 001, 011, 010, 110, 111, 101, 100}
 */
public class GrayCode {
	public List<Integer> grayCode(int n) {
		if (n == 0) {
			List<Integer> result = new ArrayList<Integer>();
			result.add(0);
			return result;
		}
	
		List<Integer> result = grayCode(n - 1);
		int numToAdd = 1 << (n - 1);
	
		for (int i = result.size() - 1; i >= 0; i--) {
			result.add(numToAdd + result.get(i));
		}
	
		return result;
	}
	
	// This function generates all n bit Gray codes and prints the
	// generated codes
	public void generateGrayarr(int n) {
		// base case
		if (n <= 0)
			return;
	
		// 'arr' will store all generated codes
		ArrayList<String> list = new ArrayList<>();
	
		// start with one-bit pattern
		list.add("0");
		list.add("1");
	
		// Every iteration of this loop generates 2*i codes from previously
		// generated i codes.
		int i, j;
		for (i = 2; i < (1 << n); i = i << 1) {
			// Enter the prviously generated codes again in arr[] in reverse
			// order. Nor arr[] has double number of codes.
			for (j = i - 1; j >= 0; j--)
				list.add(list.get(j));
	
			// append 0 to the first half
			for (j = 0; j < i; j++)
				list.set(j, "0" + list.get(j));
	
			// append 1 to the second half
			for (j = i; j < 2 * i; j++)
				list.set(j, "1" + list.get(j));
		}
	
		System.out.println(list);
	}

	public static void main(String[] args) {
		GrayCode ob = new GrayCode();
		int testNum = 5;
		System.out.println(ob.grayCode(testNum));
		ob.generateGrayarr(testNum);

	}
}
