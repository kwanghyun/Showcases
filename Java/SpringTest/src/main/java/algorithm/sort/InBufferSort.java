package algorithm.sort;

import java.util.Arrays;

/*
 * You are given two sorted arrays, A and B, and A has a large enough buffer 
 * at the end to hold B. Write a method to merge B into A in sorted order.
 */

public class InBufferSort {

	public int[] sort(int[] arrA, int[] arrB) {
		int indexA = 0;
		int indexB = 0;
		while (arrA[indexA] != 0 && indexB < arrB.length ) {
			if (arrA[indexA] < arrB[indexB]) {
				indexA++;
			} else if (arrA[indexA] > arrB[indexB]) {
				System.arraycopy(arrA, indexA, arrA, indexA + 1, arrA.length - indexA - 1);
				arrA[indexA] = arrB[indexB];
				indexA ++;
				indexB ++;
			}
		}
		if( indexB < arrB.length ){
			System.arraycopy(arrB, indexB, arrA, indexA, arrB.length - indexB);
		}
		return arrA;
	}
	
	//TODO Let's use empty buffer.
	
	public static void main(String args[]){
		int[] arrA = new int[10];
		int[] arrB = new int[5];
		
		arrA[0]  = 11;
		arrA[1]  = 12;
		arrA[2]  = 13;
		arrA[3]  = 14;
		arrA[4]  = 15;
		
		arrB[0]  = 2;
		arrB[1]  = 4;
		arrB[2]  = 6;
		arrB[3]  = 8;
		arrB[4]  = 10;
		
		InBufferSort ibs = new InBufferSort();
		ibs.sort(arrA, arrB);
		System.out.println(Arrays.toString(arrA));

//		arrA[0]  = 1;
//		arrA[1]  = 3;
//		arrA[2]  = 5;
//		arrA[3]  = 7;
//		arrA[4]  = 9;
//		
//		arrB[0]  = 2;
//		arrB[1]  = 4;
//		arrB[2]  = 6;
//		arrB[3]  = 8;
//		arrB[4]  = 10;

	}
}
